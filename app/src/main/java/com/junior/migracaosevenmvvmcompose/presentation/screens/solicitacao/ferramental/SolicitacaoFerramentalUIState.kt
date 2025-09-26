package com.junior.migracaosevenmvvmcompose.presentation.screens.solicitacao.ferramental

import com.junior.migracaosevenmvvmcompose.data.model.SolicitacaoFerramentalResponse
import com.junior.migracaosevenmvvmcompose.presentation.components.BaseUiState

data class SolicitacaoFerramentalUIState(
    override val isLoading: Boolean = false,
    override val data: List<SolicitacaoFerramentalResponse> = emptyList(),
    override val error: String? = null
): BaseUiState<SolicitacaoFerramentalResponse>(isLoading, data, error)
