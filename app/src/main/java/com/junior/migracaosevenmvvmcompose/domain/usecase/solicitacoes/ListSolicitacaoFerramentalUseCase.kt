package com.junior.migracaosevenmvvmcompose.domain.usecase.solicitacoes

import com.junior.migracaosevenmvvmcompose.domain.model.SolicitacaoFerramental
import com.junior.migracaosevenmvvmcompose.domain.repository.SolicitacaoRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class ListSolicitacaoFerramentalUseCase @Inject constructor(
    private val repository: SolicitacaoRepository
) {
    operator fun invoke(login: String): Flow<List<SolicitacaoFerramental>> {
        return repository.listSolicitacaoFerramental(login)
    }
}