package com.junior.migracaosevenmvvmcompose.data.repository.remote

import com.junior.migracaosevenmvvmcompose.data.datasource.remote.FirebaseEquipamentosDataSource
import com.junior.migracaosevenmvvmcompose.data.model.EquipamentoResponse
import com.junior.migracaosevenmvvmcompose.domain.model.Equipamento
import com.junior.migracaosevenmvvmcompose.domain.model.toDomain
import com.junior.migracaosevenmvvmcompose.domain.repository.EquipamentosRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class EquipamentosRepositoryImpl @Inject constructor(
    private val equipamentosDataSource: FirebaseEquipamentosDataSource
): EquipamentosRepository {

    override fun listEquipamentos(login: String): Flow<List<Equipamento>> {
       return equipamentosDataSource.listEquipamentos(login).map {list->
            list.map { it.toDomain() }
       }
    }
}