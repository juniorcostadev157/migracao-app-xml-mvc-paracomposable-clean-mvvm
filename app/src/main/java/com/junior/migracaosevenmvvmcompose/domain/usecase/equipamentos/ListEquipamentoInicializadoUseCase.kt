package com.junior.migracaosevenmvvmcompose.domain.usecase.equipamentos


import com.junior.migracaosevenmvvmcompose.domain.model.Equipamento
import com.junior.migracaosevenmvvmcompose.domain.repository.EquipamentosRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class ListEquipamentoInicializadoUseCase @Inject constructor(
    private val repository: EquipamentosRepository
) {
    operator fun invoke(login: String): Flow<List<Equipamento>>{
        return repository.listEquipamentos(login)
            .map { list->
                list.filter { it.estado == "INICIALIZADO" }
            }
    }
}