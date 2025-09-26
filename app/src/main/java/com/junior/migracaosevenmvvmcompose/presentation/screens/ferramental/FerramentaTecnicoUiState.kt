package com.junior.migracaosevenmvvmcompose.presentation.screens.ferramental


import com.junior.migracaosevenmvvmcompose.domain.model.FerramentalTecnico
import com.junior.migracaosevenmvvmcompose.presentation.components.BaseUiState

data class FerramentaTecnicoUiState(

    override val isLoading: Boolean = false,
    override val error: String? = null,
    override val data: List<FerramentalTecnico> = emptyList(),
    val allData: List<FerramentalTecnico> = emptyList(),
    val selectedFilter: String = "Todos",
    val searchText: String = ""

): BaseUiState<FerramentalTecnico>(isLoading, data, error)
