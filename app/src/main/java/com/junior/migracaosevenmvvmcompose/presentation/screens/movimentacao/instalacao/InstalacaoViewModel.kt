package com.junior.migracaosevenmvvmcompose.presentation.screens.movimentacao.instalacao


import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.junior.migracaosevenmvvmcompose.domain.usecase.equipamentos.ListEquipamentoInicializadoUseCase
import com.junior.migracaosevenmvvmcompose.domain.usecase.users.GetUserLoginFromPreferencesUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class InstalacaoViewModel @Inject constructor(
    private val useCaseInstalacao: ListEquipamentoInicializadoUseCase,
    private val  getUserLoginFromPreferencesUseCase: GetUserLoginFromPreferencesUseCase
): ViewModel() {

    private val _uiState = MutableStateFlow(InstalacaoUiState())
    val uiState: StateFlow<InstalacaoUiState> = _uiState

    private val _selected = MutableStateFlow<Map<Int, Pair<String, String>>>(emptyMap())
    val selected: StateFlow<Map<Int, Pair<String, String>>> = _selected

    init {
        viewModelScope.launch {
            getUserLoginFromPreferencesUseCase().collect { login->
                if (!login.isNullOrBlank()){
                    loadingEquipamento(login)
                }
            }
        }

    }

    private fun loadingEquipamento(login: String){
        viewModelScope.launch {
            try{
                _uiState.value = _uiState.value.copy(isLoading = true)
                useCaseInstalacao(login).collect { list->
                    _uiState.value = InstalacaoUiState(
                        isLoading = false,
                        data = list
                    )
                }

            }catch (e: Exception){
                _uiState.value = InstalacaoUiState(
                    isLoading = false,
                    error = e.message ?: "Erro desconhecido"
                )
            }
        }
    }

    fun updateSelection(index: Int, tipo: String, serial: String){
        val newSelected = _selected.value.toMutableMap()
        newSelected[index]= tipo to serial
        _selected.value = newSelected

    }

    fun getSerialsAvailable(tipo: String, indexCurrent: Int): List<String>{
        val used = _selected.value
            .filterKeys { it != indexCurrent }
            .map { it.value.second }
            .filter { it.isNotEmpty() }

        return _uiState.value.data
            .filter { it.tipo == tipo && !used.contains(it.numeroSerie) }
            .map { it.numeroSerie!! }

    }


}