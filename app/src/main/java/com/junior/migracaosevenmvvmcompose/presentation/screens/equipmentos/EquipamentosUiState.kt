package com.junior.migracaosevenmvvmcompose.presentation.screens.equipmentos

import com.junior.migracaosevenmvvmcompose.domain.model.Equipamento
import com.junior.migracaosevenmvvmcompose.presentation.components.BaseUiState

data class EquipamentosUiState(
    override val isLoading: Boolean = true,
    override val data: List<Equipamento> = emptyList(),
    override val error: String? = null,
    val allData: List<Equipamento> = emptyList(),
    val selectedFilter: String = "Todos",
    val searchText: String = ""

): BaseUiState<Equipamento>(isLoading, data, error)
