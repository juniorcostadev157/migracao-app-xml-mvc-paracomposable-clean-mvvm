package com.junior.migracaosevenmvvmcompose.presentation.screens.solicitacao.ferramental

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.junior.migracaosevenmvvmcompose.data.model.SolicitacaoFerramentalResponse
import com.junior.migracaosevenmvvmcompose.data.usecase.solicitacoes.ListSolicitacaoFerramentalUseCase
import com.junior.migracaosevenmvvmcompose.data.usecase.solicitacoes.UpdateStatusSolicitacaoFerramentalUseCase
import com.junior.migracaosevenmvvmcompose.domain.usecase.users.GetUserLoginFromPreferencesUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SolicitacaoFerramentalViewModel @Inject constructor(
    private val listSolicitacaoFerramentalUseCase: ListSolicitacaoFerramentalUseCase,
    private val getUserLoginFromPreferencesUseCase: GetUserLoginFromPreferencesUseCase,
    private val updateStatusSolicitacaoFerramentalUseCase: UpdateStatusSolicitacaoFerramentalUseCase
) : ViewModel(){

    private val _uiState = mutableStateOf(SolicitacaoFerramentalUIState())
    val uiState: State<SolicitacaoFerramentalUIState> = _uiState


    init {
        viewModelScope.launch {
            getUserLoginFromPreferencesUseCase().collect { login->
                if (!login.isNullOrBlank()){
                    listSolicitacaoFerramental(login)
                }
            }
        }
    }

    fun listSolicitacaoFerramental(login: String) {
        viewModelScope.launch {
           _uiState.value = SolicitacaoFerramentalUIState(isLoading = true)
            try {
                listSolicitacaoFerramentalUseCase(login).collect { list->
                    _uiState.value = SolicitacaoFerramentalUIState(isLoading = false, data = list)
                }
            }catch (e: Exception){
                _uiState.value = SolicitacaoFerramentalUIState(isLoading = false, error = e.message)
            }
        }
    }

    fun updateSolicitacaoFerramental(solicitacao: SolicitacaoFerramentalResponse, status: String){
        viewModelScope.launch {
            updateStatusSolicitacaoFerramentalUseCase(solicitacao.id, status)
            val updatedList = _uiState.value.data.filter { it.id != solicitacao.id }
            _uiState.value = _uiState.value.copy(data = updatedList)

        }
    }


}