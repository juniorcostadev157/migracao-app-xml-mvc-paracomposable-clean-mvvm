package com.junior.migracaosevenmvvmcompose.data.model

import com.google.firebase.Timestamp
import com.google.firebase.firestore.PropertyName

data class EquipamentoResponse(
    var id: String = "",
    @get:PropertyName("CodigoMaterialSAP") @set:PropertyName("CodigoMaterialSAP")
    var codigoMaterialSAP: String? = null,

    @get:PropertyName("DataRecebimento") @set:PropertyName("DataRecebimento")
    var dataRecebimento: Timestamp? = null,

    @get:PropertyName("DataUltimaAlteracao") @set:PropertyName("DataUltimaAlteracao")
    var dataUltimaAlteracao: String? = null,

    @get:PropertyName("Desconectado") @set:PropertyName("Desconectado")
    var desconectado: String? = null,

    @get:PropertyName("Destino") @set:PropertyName("Destino")
    var destino: String? = null,

    @get:PropertyName("Estado") @set:PropertyName("Estado")
    var estado: String? = null,

    @get:PropertyName("Modelo") @set:PropertyName("Modelo")
    var modelo: String? = null,

    @get:PropertyName("NumeroSerie") @set:PropertyName("NumeroSerie")
    var numeroSerie: String? = null,

    @get:PropertyName("Tecnologia") @set:PropertyName("Tecnologia")
    var tecnologia: String? = null,

    @get:PropertyName("Tipo") @set:PropertyName("Tipo")
    var tipo: String? = null

)
