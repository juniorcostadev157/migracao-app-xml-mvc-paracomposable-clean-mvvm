package com.junior.migracaosevenmvvmcompose.data.repository.remote

import com.junior.migracaosevenmvvmcompose.data.datasource.remote.FirebaseFerramentalDataSource
import com.junior.migracaosevenmvvmcompose.domain.model.FerramentalTecnico
import com.junior.migracaosevenmvvmcompose.domain.model.toDomain
import com.junior.migracaosevenmvvmcompose.domain.repository.FerramentalRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class FerramentalRepositoryImpl @Inject constructor(
    private val dataSource: FirebaseFerramentalDataSource
): FerramentalRepository {

    override fun listFerramental(login: String): Flow<List<FerramentalTecnico>> {
        return dataSource.listFerramental(login).map { list->
            list.map { it.toDomain() }
        }
    }
}