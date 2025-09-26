package com.junior.migracaosevenmvvmcompose.domain.model

import com.google.firebase.Timestamp
import com.junior.migracaosevenmvvmcompose.data.model.FerramentalListCheckInResponse
import com.junior.migracaosevenmvvmcompose.data.model.SolicitacaoCheckInResponse


data class SolicitacaoCheckIn(
    val id: String = "",
    val dataEnvio: Timestamp?,
    val ferramental: List<FerramentalListCheckIn> = emptyList(),
    val loginTecnico: String?,
    val nomeAlmox: String?,
    val status: String?

)

data class FerramentalListCheckIn(
    val nomeFerramenta: String,
    val statusCheckIn: String

)

fun SolicitacaoCheckInResponse.toDomain() = SolicitacaoCheckIn(
    id = this.id,
    dataEnvio = this.dataEnvio,
    ferramental = ferramental.map { it.toDomain() },
    loginTecnico = this.loginTecnico,
    nomeAlmox = this.nomeAlmox,
    status = this.status
)

fun FerramentalListCheckInResponse.toDomain() = FerramentalListCheckIn(
    nomeFerramenta = this.nomeFerramenta ?: "",
    statusCheckIn = this.statusCheckIn ?: ""
)