package com.junior.migracaosevenmvvmcompose.domain.usecase.solicitacoes


import com.junior.migracaosevenmvvmcompose.domain.model.SolicitacaoCheckIn
import com.junior.migracaosevenmvvmcompose.domain.repository.SolicitacaoRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class ListSolicitacaoCheckInUseCase @Inject constructor(
    private val repository: SolicitacaoRepository
) {
    operator fun invoke(login: String): Flow<List<SolicitacaoCheckIn>>{
        return repository.listSolicitacaoCheckIn(login)

    }
}