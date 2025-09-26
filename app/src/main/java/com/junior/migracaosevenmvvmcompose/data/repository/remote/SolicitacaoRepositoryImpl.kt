package com.junior.migracaosevenmvvmcompose.data.repository.remote

import com.junior.migracaosevenmvvmcompose.data.datasource.remote.FireBaseSolicitacaoDataSource
import com.junior.migracaosevenmvvmcompose.data.model.SolicitacaoCheckInResponse
import com.junior.migracaosevenmvvmcompose.domain.model.SolicitacaoEquipamentos
import com.junior.migracaosevenmvvmcompose.domain.model.SolicitacaoFerramental
import com.junior.migracaosevenmvvmcompose.domain.model.toDomain
import com.junior.migracaosevenmvvmcompose.domain.repository.SolicitacaoRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject


class SolicitacaoRepositoryImpl @Inject constructor(
    private val solicitacaoDataSource: FireBaseSolicitacaoDataSource
): SolicitacaoRepository {

    override  fun listSolicitacoesEquipamentos(login: String): Flow<List<SolicitacaoEquipamentos>> {
        return solicitacaoDataSource.listSolicitacoesEquipamentos(login).map {
            it.map { solicitacao ->
                solicitacao.toDomain()
            }
        }
    }

    override fun listSolicitacaoCheckIn(login: String): Flow<List<SolicitacaoCheckInResponse>> {
        return solicitacaoDataSource.listSolicitacaoCheckIn(login)
    }

    override suspend fun updateStatusSolicitacao(id: String, status: String) {
        solicitacaoDataSource.updateStatusSolicitacao(id, status)
    }

    override fun listSolicitacaoFerramental(login: String): Flow<List<SolicitacaoFerramental>> {
        return solicitacaoDataSource.listSolicitacaoFerramental(login).map {list->
            list.map {solicitacaoFerramentalResponse ->
                solicitacaoFerramentalResponse.toDomain()
            }
        }
    }

    override suspend fun updateStatusSolicitacaoFerramental(
        id: String,
        status: String
    ) {
        solicitacaoDataSource.updateStatusSolicitacaoFerramental(id, status)
    }

    override suspend fun updateStatusSolicitacaoCheckIn(id: String, status: String) {
        solicitacaoDataSource.updateStatusSolicitacaoCheckIn(id, status)
    }
}