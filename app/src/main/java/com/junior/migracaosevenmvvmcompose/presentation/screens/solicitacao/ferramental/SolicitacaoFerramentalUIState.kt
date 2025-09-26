package com.junior.migracaosevenmvvmcompose.presentation.screens.solicitacao.ferramental

import com.junior.migracaosevenmvvmcompose.domain.model.SolicitacaoFerramental
import com.junior.migracaosevenmvvmcompose.presentation.components.BaseUiState

data class SolicitacaoFerramentalUIState(
    override val isLoading: Boolean = false,
    override val data: List<SolicitacaoFerramental> = emptyList(),
    override val error: String? = null
): BaseUiState<SolicitacaoFerramental>(isLoading, data, error)
