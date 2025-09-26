package com.junior.migracaosevenmvvmcompose.presentation.components

open class BaseUiState<T>(
    open val isLoading: Boolean = true,
    open val data: List<T> = emptyList(),
    open val error: String? = null
)