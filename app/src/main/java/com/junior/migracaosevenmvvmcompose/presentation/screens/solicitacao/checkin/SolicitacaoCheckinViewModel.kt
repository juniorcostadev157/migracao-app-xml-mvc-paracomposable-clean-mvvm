package com.junior.migracaosevenmvvmcompose.presentation.screens.solicitacao.checkin

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.junior.migracaosevenmvvmcompose.domain.model.SolicitacaoCheckIn
import com.junior.migracaosevenmvvmcompose.domain.usecase.solicitacoes.ListSolicitacaoCheckInUseCase
import com.junior.migracaosevenmvvmcompose.domain.usecase.solicitacoes.UpdateStatusSolicitacaoCheckinUseCase
import com.junior.migracaosevenmvvmcompose.domain.usecase.users.GetUserLoginFromPreferencesUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SolicitacaoCheckinViewModel @Inject constructor(
    private val listSolicitacaoCheckinUseCase: ListSolicitacaoCheckInUseCase,
    private val getUserLoginFromPreferencesUseCase: GetUserLoginFromPreferencesUseCase,
    private val updateSolicitacaoCheckinUseCase: UpdateStatusSolicitacaoCheckinUseCase
) : ViewModel() {

    private  val _uiState = MutableStateFlow(SolicitacaoCheckInUiState())
    val uiState : StateFlow<SolicitacaoCheckInUiState> = _uiState.asStateFlow()

    init {
        viewModelScope.launch {
            getUserLoginFromPreferencesUseCase().collect {login->

                if (!login.isNullOrBlank()){
                    listSolicitacoesCheckin(login)

                }
            }
        }
    }
    fun listSolicitacoesCheckin(login: String){
        viewModelScope.launch {
            _uiState.value = SolicitacaoCheckInUiState(isLoading = true)
            try {
                listSolicitacaoCheckinUseCase(login).collect { list ->
                    _uiState.value = SolicitacaoCheckInUiState(isLoading = false, data = list)
                }
            }catch (e: Exception){
                _uiState.value = SolicitacaoCheckInUiState(isLoading = false, error = e.message)
            }
        }
        }


    fun updateStatusSolicitacao(solicitacao: SolicitacaoCheckIn, status: String){
        viewModelScope.launch {
            updateSolicitacaoCheckinUseCase(solicitacao.id, status)
            val updatedList = _uiState.value.data.filter { it.id != solicitacao.id }
            _uiState.value = _uiState.value.copy(data = updatedList)
        }

    }

}