package com.junior.migracaosevenmvvmcompose.data.usecase.solicitacoes

import com.junior.migracaosevenmvvmcompose.domain.repository.SolicitacaoRepository
import javax.inject.Inject

class UpdateStatusSolicitacaoEquipamentosUseCase @Inject constructor(
    private val repository: SolicitacaoRepository
) {

    suspend operator fun invoke(id: String, status: String){

        val statusFinal = when(status){
            "Aceita"-> "Aceita"
            "Rejeitada"-> "Rejeitada"
            else -> return
        }

        repository.updateStatusSolicitacao(id, statusFinal)

    }
}