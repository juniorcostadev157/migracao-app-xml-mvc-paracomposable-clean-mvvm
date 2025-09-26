package com.junior.migracaosevenmvvmcompose.presentation.screens.equipmentos


import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.junior.migracaosevenmvvmcompose.core.utils.EquipamentoUtils
import com.junior.migracaosevenmvvmcompose.data.usecase.equipamentos.ListEquipamentosUseCase
import com.junior.migracaosevenmvvmcompose.domain.usecase.users.GetUserLoginFromPreferencesUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class EquipamentosViewModel @Inject constructor(
    private val useCase: ListEquipamentosUseCase,
    private val getUserLoginFromPreferencesUseCase: GetUserLoginFromPreferencesUseCase
) : ViewModel() {


    private val _uiState = mutableStateOf(EquipamentosUiState())
    val uiState: State<EquipamentosUiState> = _uiState

    init {
        viewModelScope.launch {
            getUserLoginFromPreferencesUseCase().collect {login->
                if (!login.isNullOrBlank()){
                    listEquipamentos(login)
                }

            }
        }
    }

    fun listEquipamentos(login: String) {
        viewModelScope.launch {

                try {
                  useCase(login).collect { list->
                      val updateList = list.map { equip->
                          equip.apply {
                              critico = EquipamentoUtils.isCritico(equip)
                          }
                      }
                      _uiState.value = _uiState.value.copy(
                          isLoading = false,
                          allData = updateList
                      )

                      applyFilters()

                  }

                } catch (e: Exception) {
                    _uiState.value = _uiState.value.copy(
                        isLoading = false,
                        error = e.message
                    )
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

    private fun applyFilters() {
        val current  = _uiState.value
        var filtered = current.allData

        if (current.searchText.isNotBlank()){
            filtered = filtered.filter {
                it.tipo.contains(current.searchText,  ignoreCase = true) ||
                it.numeroSerie.contains(current.searchText, ignoreCase = true)||
                it.estado.contains(current.searchText, ignoreCase = true)
            }
        }

        filtered = if (current.selectedFilter == "Todos"){
            filtered
        }else{
            filtered.filter { it.estado == current.selectedFilter }
        }

        _uiState.value = current.copy(data = filtered)


    }
}