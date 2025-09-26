package com.junior.migracaosevenmvvmcompose.presentation.screens.ferramental

import com.junior.migracaosevenmvvmcompose.data.model.FerramentalTecnicoModel
import com.junior.migracaosevenmvvmcompose.presentation.components.BaseUiState

data class FerramentaTecnicoUiState(

    override val isLoading: Boolean = false,
    override val error: String? = null,
    override val data: List<FerramentalTecnicoModel> = emptyList(),
    val allData: List<FerramentalTecnicoModel> = emptyList(),
    val selectedFilter: String = "Todos",
    val searchText: String = ""

): BaseUiState<FerramentalTecnicoModel>(isLoading, data, error)
