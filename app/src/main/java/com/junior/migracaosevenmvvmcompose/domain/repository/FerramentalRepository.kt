package com.junior.migracaosevenmvvmcompose.domain.repository

import com.junior.migracaosevenmvvmcompose.data.model.FerramentalTecnicoModel
import kotlinx.coroutines.flow.Flow

interface FerramentalRepository {

    fun listFerramental(login: String): Flow<List<FerramentalTecnicoModel>>

}