package com.junior.migracaosevenmvvmcompose.presentation.screens.solicitacao.ferramental


import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.junior.migracaosevenmvvmcompose.domain.model.SolicitacaoFerramental
import com.junior.migracaosevenmvvmcompose.domain.usecase.solicitacoes.ListSolicitacaoFerramentalUseCase
import com.junior.migracaosevenmvvmcompose.domain.usecase.solicitacoes.UpdateStatusSolicitacaoFerramentalUseCase
import com.junior.migracaosevenmvvmcompose.domain.usecase.users.GetUserLoginFromPreferencesUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SolicitacaoFerramentalViewModel @Inject constructor(
    private val listSolicitacaoFerramentalUseCase: ListSolicitacaoFerramentalUseCase,
    private val getUserLoginFromPreferencesUseCase: GetUserLoginFromPreferencesUseCase,
    private val updateStatusSolicitacaoFerramentalUseCase: UpdateStatusSolicitacaoFerramentalUseCase
) : ViewModel(){

    private val _uiState = MutableStateFlow(SolicitacaoFerramentalUIState())
    val uiState: StateFlow<SolicitacaoFerramentalUIState> = _uiState.asStateFlow()


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

    fun updateSolicitacaoFerramental(solicitacao: SolicitacaoFerramental, status: String){
        viewModelScope.launch {
            updateStatusSolicitacaoFerramentalUseCase(solicitacao.id, status)
            val updatedList = _uiState.value.data.filter { it.id != solicitacao.id }
            _uiState.value = _uiState.value.copy(data = updatedList)

        }
    }


}