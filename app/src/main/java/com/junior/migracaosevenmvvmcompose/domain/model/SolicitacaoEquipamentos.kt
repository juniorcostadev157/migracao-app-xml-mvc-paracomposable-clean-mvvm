package com.junior.migracaosevenmvvmcompose.domain.model

import com.google.firebase.Timestamp
import com.junior.migracaosevenmvvmcompose.data.model.EquipamentosSolicitacaoResponse
import com.junior.migracaosevenmvvmcompose.data.model.SolicitacaoEquipamentosResponse

data class SolicitacaoEquipamentos (
    val id: String,
    val equipamentos: List<EquipamentosSolicitacao> = listOf(),
    val idAlmoxarife: String,
    val loginTecnico: String,
    val nomeAlmoxarife: String,
    val status: String ,
    val data: Timestamp
)

data class EquipamentosSolicitacao(
    val numeroSerie: String ,
    val tipo: String,
)

fun SolicitacaoEquipamentosResponse.toDomain() = SolicitacaoEquipamentos(
    id = this.id,
    equipamentos = this.equipamentos.map { it.toDomain() },
    idAlmoxarife = this.idAlmoxarife ?: "",
    loginTecnico = this.loginTecnico ?: "",
    nomeAlmoxarife = this.nomeAlmoxarife ?: "",
    status = this.status ?: "",
    data = this.data ?: Timestamp.now()
)

fun EquipamentosSolicitacaoResponse.toDomain() = EquipamentosSolicitacao(
    numeroSerie = this.numeroSerie,
    tipo = this.tipo,

)