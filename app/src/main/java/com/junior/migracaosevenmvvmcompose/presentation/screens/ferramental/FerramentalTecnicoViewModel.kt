package com.junior.migracaosevenmvvmcompose.presentation.screens.ferramental


import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.junior.migracaosevenmvvmcompose.domain.usecase.ferramental.ListFerramentalUseCase
import com.junior.migracaosevenmvvmcompose.domain.usecase.users.GetUserLoginFromPreferencesUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class FerramentalTecnicoViewModel @Inject constructor(
    private val listFerramentalUseCase: ListFerramentalUseCase,
    private val getUserLoginFromPreferencesUseCase: GetUserLoginFromPreferencesUseCase
): ViewModel () {

    private val _uiState = MutableStateFlow(FerramentaTecnicoUiState())
    val uiState: StateFlow<FerramentaTecnicoUiState> = _uiState.asStateFlow()

    init {
        viewModelScope.launch {
            getUserLoginFromPreferencesUseCase().collect { login->
                if (!login.isNullOrBlank()){
                    listFerramental(login)
                }

            }
        }
    }

    fun listFerramental(login: String){
        viewModelScope.launch {
            try {

                listFerramentalUseCase(login).collect {list->

                    _uiState.value = _uiState.value.copy(isLoading = false, allData =  list)

                    applyFilters()
                }
            }catch (e: Exception){
                    _uiState.value = _uiState.value.copy(isLoading = false, error = e.message)

            }
        }
    }


    fun onSearchChange(query: String){
        _uiState.value = _uiState.value.copy(searchText = query)
        applyFilters()
    }

    fun onFilterChange(filter: String){
        _uiState.value = _uiState.value.copy(
            selectedFilter = filter
        )
        applyFilters()
    }

    private fun applyFilters(){
        val current  = _uiState.value
        var filtered = current.allData

        if (current.searchText.isNotBlank()){
            filtered = filtered.filter {
                it.nomeFerramenta.contains(current.searchText , ignoreCase = true) ||
                it.statusValidade.contains(current.searchText, ignoreCase = true) ||
                it.statusCheckIn.contains(current.searchText, ignoreCase = true)

            }
        }

        filtered = if (current.selectedFilter == "Todos"){
            filtered
        }else{
            filtered.filter { it.statusValidade == current.selectedFilter }

        }
            _uiState.value = current.copy(data = filtered)
    }
}