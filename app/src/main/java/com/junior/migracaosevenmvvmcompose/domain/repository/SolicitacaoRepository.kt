package com.junior.migracaosevenmvvmcompose.domain.repository

import com.junior.migracaosevenmvvmcompose.data.model.SolicitacaoCheckInResponse
import com.junior.migracaosevenmvvmcompose.domain.model.SolicitacaoEquipamentos
import com.junior.migracaosevenmvvmcompose.domain.model.SolicitacaoFerramental
import kotlinx.coroutines.flow.Flow

interface SolicitacaoRepository {
    fun listSolicitacoesEquipamentos(login: String): Flow<List<SolicitacaoEquipamentos>>
    fun listSolicitacaoCheckIn(login: String): Flow<List<SolicitacaoCheckInResponse>>
    fun listSolicitacaoFerramental(login: String): Flow<List<SolicitacaoFerramental>>
    suspend fun updateStatusSolicitacaoFerramental(id: String, status: String)
    suspend fun updateStatusSolicitacaoCheckIn(id: String, status: String)
    suspend fun updateStatusSolicitacao(id: String, status: String)



}