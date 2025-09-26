package com.junior.migracaosevenmvvmcompose.presentation.screens.solicitacao.checkin

import com.junior.migracaosevenmvvmcompose.data.model.SolicitacaoCheckInResponse
import com.junior.migracaosevenmvvmcompose.presentation.components.BaseUiState

data class SolicitacaoCheckInUiState(
    override val isLoading: Boolean = false,
    override val data: List<SolicitacaoCheckInResponse> = emptyList(),
    override val error: String? = null
): BaseUiState<SolicitacaoCheckInResponse>(isLoading, data, error)
