package com.junior.migracaosevenmvvmcompose.domain.repository


import com.junior.migracaosevenmvvmcompose.domain.model.Equipamento
import kotlinx.coroutines.flow.Flow

interface EquipamentosRepository  {

    fun listEquipamentos(login: String): Flow<List<Equipamento>>
}