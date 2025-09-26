package com.junior.migracaosevenmvvmcompose.domain.model

import com.google.firebase.Timestamp
import com.junior.migracaosevenmvvmcompose.data.model.EquipamentoResponse

data class Equipamento(
    val codigoMaterialSap: String,
    val dataRecebimento: Timestamp,
    val dataUltimaAlteracao: String,
    val destino: String,
    val desconectado: String,
    val estado: String,
    val modelo: String,
    val numeroSerie: String,
    val tecnologia: String,
    val tipo: String
){
    var critico: Boolean = false
}

fun EquipamentoResponse.toDomain() = Equipamento(
    codigoMaterialSap = this.codigoMaterialSAP ?: "",
    dataRecebimento = this.dataRecebimento ?: Timestamp.now(),
    dataUltimaAlteracao = this.dataUltimaAlteracao ?: "",
    destino = this.destino ?:"",
    estado = this.estado ?:"",
    modelo = this.modelo ?:"",
    numeroSerie = this.numeroSerie ?:"",
    tecnologia = this.tecnologia ?:"",
    tipo = this.tipo ?:"",
    desconectado = this.desconectado ?: ""


)