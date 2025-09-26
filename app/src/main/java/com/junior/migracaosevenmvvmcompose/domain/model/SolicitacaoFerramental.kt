package com.junior.migracaosevenmvvmcompose.domain.model

import com.google.firebase.Timestamp
import com.junior.migracaosevenmvvmcompose.data.model.FerramentasResponseList
import com.junior.migracaosevenmvvmcompose.data.model.SolicitacaoFerramentalResponse

data class SolicitacaoFerramental(
    val id: String,
    val ferramental: List<FerramentasList?>?,
    val dataEnvio: Timestamp?,
    val loginTecnico: String?,
    val nomeAlmox: String?,
    val nomeTecnico: String?,
    val status: String?
)

data class FerramentasList(
    val dataValidade: String,
    val nomeFerramenta: String,
    val validade: String,
    val valor: String
)

fun SolicitacaoFerramentalResponse.toDomain() = SolicitacaoFerramental(
    id = this.id,
    ferramental = this.ferramental.map { it.toDomain() },
    dataEnvio = this.dataEnvio,
    loginTecnico = this.loginTecnico,
    nomeAlmox = this.nomeAlmox,
    nomeTecnico = this.nomeTecnico,
    status = this.status
)

fun FerramentasResponseList.toDomain() = FerramentasList(
    dataValidade = this.dataValidade ?: "" ,
    nomeFerramenta = this.nomeFerramenta ?:"",
    validade = this.validade ?:"",
    valor = this.valor ?:""
)