package com.junior.migracaosevenmvvmcompose.presentation.screens.movimentacao.instalacao

import com.junior.migracaosevenmvvmcompose.data.model.EquipamentoResponse
import com.junior.migracaosevenmvvmcompose.domain.model.Equipamento
import com.junior.migracaosevenmvvmcompose.presentation.components.BaseUiState

data class InstalacaoUiState (
    override val isLoading: Boolean = true,
    override val data: List<Equipamento> = emptyList(),
    override val error: String? = null

): BaseUiState<Equipamento>(isLoading, data, error)