package com.junior.migracaosevenmvvmcompose.domain.repository

import com.junior.migracaosevenmvvmcompose.data.model.FerramentalTecnicoResponse
import com.junior.migracaosevenmvvmcompose.domain.model.FerramentalTecnico
import kotlinx.coroutines.flow.Flow

interface FerramentalRepository {

    fun listFerramental(login: String): Flow<List<FerramentalTecnico>>

}