package com.junior.migracaosevenmvvmcompose.data.usecase.solicitacoes

import com.junior.migracaosevenmvvmcompose.domain.repository.SolicitacaoRepository
import javax.inject.Inject

class UpdateStatusSolicitacaoFerramentalUseCase @Inject constructor(
    private val repository: SolicitacaoRepository
) {

    suspend operator fun invoke(id: String, status: String){

        val statusFinal = when(status){
            "ACEITO"-> "ACEITO"
            "REJEITADO"-> "REJEITADO"
            else -> return
        }
        repository.updateStatusSolicitacaoFerramental(id,statusFinal)

    }
}