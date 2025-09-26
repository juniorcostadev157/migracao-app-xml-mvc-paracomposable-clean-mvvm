package com.junior.migracaosevenmvvmcompose.presentation.screens.solicitacao.equipamentos


import com.junior.migracaosevenmvvmcompose.domain.model.SolicitacaoEquipamentos
import com.junior.migracaosevenmvvmcompose.presentation.components.BaseUiState

data class SolicitacaoEquipamentoUiState(
    override val isLoading: Boolean = false,
    override val data: List<SolicitacaoEquipamentos> = emptyList(),
    override val error: String? = null
): BaseUiState<SolicitacaoEquipamentos>(isLoading, data, error)
