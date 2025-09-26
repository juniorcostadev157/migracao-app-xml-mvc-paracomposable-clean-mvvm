package com.junior.migracaosevenmvvmcompose.domain.usecase.solicitacoes

import com.junior.migracaosevenmvvmcompose.domain.model.SolicitacaoEquipamentos
import com.junior.migracaosevenmvvmcompose.domain.repository.SolicitacaoRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class ListSolicitacaoEquipamentosUseCase @Inject constructor(
    private val repository: SolicitacaoRepository
) {
     operator fun invoke(login: String): Flow<List<SolicitacaoEquipamentos>> {
        return  repository.listSolicitacoesEquipamentos(login)
    }
}