package com.junior.migracaosevenmvvmcompose.domain.model

import com.junior.migracaosevenmvvmcompose.data.model.FerramentalTecnicoResponse

data class FerramentalTecnico(
    val id: String,
    val dataRecebimento: String,
    val dataUltimoCheckin: String,
    val dataValidade: String,
    val login: String,
    val nomeFerramenta: String,
    val statusCheckIn: String,
    val statusValidade: String,
    val valor: String,
    val validade: String
)

fun FerramentalTecnicoResponse.toDomain() = FerramentalTecnico(
    id = this.id,
    dataRecebimento = this.dataRecebimento ?: "",
    dataUltimoCheckin = this.dataUltimoCheckin ?: "",
    dataValidade = this.dataValidade ?: "",
    login = this.login ?: "",
    nomeFerramenta = this.nomeFerramenta ?: "",
    statusCheckIn = this.statusCheckIn ?: "",
    statusValidade = this.statusValidade ?: "",
    valor = this.valor ?: "",
    validade = this.validade ?: ""

)
