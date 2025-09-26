package com.junior.migracaosevenmvvmcompose.data.usecase.equipamentos

import com.junior.migracaosevenmvvmcompose.domain.model.Equipamento
import com.junior.migracaosevenmvvmcompose.domain.repository.EquipamentosRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class ListEquipamentosUseCase @Inject  constructor(
    private val repository: EquipamentosRepository

) {

    operator fun invoke(login: String): Flow<List<Equipamento>>{
        return repository.listEquipamentos(login)
    }
}