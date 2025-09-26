package com.junior.migracaosevenmvvmcompose.presentation.screens.solicitacao.checkin


import com.junior.migracaosevenmvvmcompose.domain.model.SolicitacaoCheckIn
import com.junior.migracaosevenmvvmcompose.presentation.components.BaseUiState

data class SolicitacaoCheckInUiState(
    override val isLoading: Boolean = false,
    override val data: List<SolicitacaoCheckIn> = emptyList(),
    override val error: String? = null
): BaseUiState<SolicitacaoCheckIn>(isLoading, data, error)
