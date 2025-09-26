package com.junior.migracaosevenmvvmcompose.data.repository.remote

import com.junior.migracaosevenmvvmcompose.data.datasource.remote.FirebaseFerramentalDataSource
import com.junior.migracaosevenmvvmcompose.data.model.FerramentalTecnicoModel
import com.junior.migracaosevenmvvmcompose.domain.repository.FerramentalRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class FerramentalRepositoryImpl @Inject constructor(
    private val dataSource: FirebaseFerramentalDataSource
): FerramentalRepository {

    override fun listFerramental(login: String): Flow<List<FerramentalTecnicoModel>> {
        return dataSource.listFerramental(login)
    }
}