package com.junior.migracaosevenmvvmcompose.domain.usecase.solicitacoes

import com.junior.migracaosevenmvvmcompose.data.model.SolicitacaoCheckInResponse
import com.junior.migracaosevenmvvmcompose.domain.repository.SolicitacaoRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class ListSolicitacaoCheckInUseCase @Inject constructor(
    private val repository: SolicitacaoRepository
) {
    operator fun invoke(login: String): Flow<List<SolicitacaoCheckInResponse>>{
        return repository.listSolicitacaoCheckIn(login)

    }
}