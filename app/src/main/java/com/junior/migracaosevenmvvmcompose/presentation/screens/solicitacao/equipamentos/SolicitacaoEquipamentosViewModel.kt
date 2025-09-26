    package com.junior.migracaosevenmvvmcompose.presentation.screens.solicitacao.equipamentos

    import androidx.lifecycle.ViewModel
    import androidx.lifecycle.viewModelScope
    import com.junior.migracaosevenmvvmcompose.domain.usecase.solicitacoes.ListSolicitacaoEquipamentosUseCase
    import com.junior.migracaosevenmvvmcompose.data.usecase.solicitacoes.UpdateStatusSolicitacaoEquipamentosUseCase
    import com.junior.migracaosevenmvvmcompose.domain.model.SolicitacaoEquipamentos
    import com.junior.migracaosevenmvvmcompose.domain.usecase.users.GetUserLoginFromPreferencesUseCase
    import dagger.hilt.android.lifecycle.HiltViewModel
    import kotlinx.coroutines.flow.MutableStateFlow
    import kotlinx.coroutines.flow.StateFlow
    import kotlinx.coroutines.flow.asStateFlow
    import kotlinx.coroutines.flow.collectLatest
    import kotlinx.coroutines.flow.update
    import kotlinx.coroutines.launch
    import javax.inject.Inject

    @HiltViewModel
    class SolicitacaoEquipamentosViewModel @Inject constructor(
        private val listSolicitacaoEquipamentosUseCase: ListSolicitacaoEquipamentosUseCase,
        private val getUserLoginFromPreferencesUseCase: GetUserLoginFromPreferencesUseCase,
        private val updateStatusSolicitacaoEquipamentosUseCase: UpdateStatusSolicitacaoEquipamentosUseCase
    ) : ViewModel() {

        private val _uiState = MutableStateFlow(SolicitacaoEquipamentoUiState())
        val uiState: StateFlow<SolicitacaoEquipamentoUiState> = _uiState.asStateFlow()

        init {
            observeSolicitacoes()
        }

        private fun observeSolicitacoes() {
            viewModelScope.launch {
                getUserLoginFromPreferencesUseCase().collectLatest { login ->
                    if (!login.isNullOrBlank()) {
                        loadSolicitacoes(login)
                    }
                }
            }
        }

        private fun loadSolicitacoes(login: String) {
            viewModelScope.launch {
                _uiState.update { it.copy(isLoading = true, error = null) }
                try {
                    listSolicitacaoEquipamentosUseCase(login).collectLatest { list ->
                        _uiState.update {
                            it.copy(
                                isLoading = false,
                                data = list,
                                error = null
                            )
                        }
                    }
                } catch (e: Exception) {
                    _uiState.update {
                        it.copy(
                            isLoading = false,
                            error = e.message ?: "Erro desconhecido"
                        )
                    }
                }
            }
        }

        fun updateStatusSolicitacao(solicitacao: SolicitacaoEquipamentos, status: String) {
            viewModelScope.launch {
                try {
                    updateStatusSolicitacaoEquipamentosUseCase(solicitacao.id, status)
                    _uiState.update { state ->
                        state.copy(data = state.data.filter { it.id != solicitacao.id })
                    }
                } catch (e: Exception) {
                    _uiState.update {
                        it.copy(error = "Falha ao actualizer status ${e.message}")
                    }
                }
            }
        }
    }
