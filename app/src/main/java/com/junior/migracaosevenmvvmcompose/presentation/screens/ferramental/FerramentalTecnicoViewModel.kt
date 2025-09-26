package com.junior.migracaosevenmvvmcompose.presentation.screens.ferramental

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.junior.migracaosevenmvvmcompose.data.usecase.ferramental.ListFerramentalUseCase
import com.junior.migracaosevenmvvmcompose.domain.usecase.users.GetUserLoginFromPreferencesUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class FerramentalTecnicoViewModel @Inject constructor(
    private val listFerramentalUseCase: ListFerramentalUseCase,
    private val getUserLoginFromPreferencesUseCase: GetUserLoginFromPreferencesUseCase
): ViewModel () {

    private val _uiState = mutableStateOf(FerramentaTecnicoUiState())
    val uiState: State<FerramentaTecnicoUiState> = _uiState

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
                it.Nome_ferramenta!!.contains(current.searchText , ignoreCase = true) ||
                it.Status_Validade!!.contains(current.searchText, ignoreCase = true) ||
                it.Status_Check_in!!.contains(current.searchText, ignoreCase = true)

            }
        }

        filtered = if (current.selectedFilter == "Todos"){
            filtered
        }else{
            filtered.filter { it.Status_Validade == current.selectedFilter }

        }
            _uiState.value = current.copy(data = filtered)
    }
}