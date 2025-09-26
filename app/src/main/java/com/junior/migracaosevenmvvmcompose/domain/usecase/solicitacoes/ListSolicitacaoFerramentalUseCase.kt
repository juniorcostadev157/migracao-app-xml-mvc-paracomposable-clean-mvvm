package com.junior.migracaosevenmvvmcompose.data.usecase.solicitacoes

import com.junior.migracaosevenmvvmcompose.data.model.SolicitacaoFerramentalResponse
import com.junior.migracaosevenmvvmcompose.domain.repository.SolicitacaoRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class ListSolicitacaoFerramentalUseCase @Inject constructor(
    private val repository: SolicitacaoRepository
) {
    operator fun invoke(login: String): Flow<List<SolicitacaoFerramentalResponse>> {
        return repository.listSolicitacaoFerramental(login)
    }
}