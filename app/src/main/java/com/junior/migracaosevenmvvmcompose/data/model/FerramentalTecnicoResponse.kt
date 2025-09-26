package com.junior.migracaosevenmvvmcompose.data.model

import com.google.firebase.firestore.PropertyName

data class FerramentalTecnicoResponse(

    var id: String = "",
    @get: PropertyName("DataRecebimento") @set:PropertyName("DataRecebimento")
    var dataRecebimento: String? = null,

    @get: PropertyName("DataUltimoCheckin") @set:PropertyName("DataUltimoCheckin")
    var dataUltimoCheckin: String? = null,

    @get: PropertyName("DataValidade") @set:PropertyName("DataValidade")
    var dataValidade: String? = null,

    @get:PropertyName("Login") @set:PropertyName("Login")
    var login: String? = null,

    @get: PropertyName("Nome_ferramenta") @set:PropertyName("Nome_ferramenta")
    var nomeFerramenta: String? = null,

    @get: PropertyName("Status_Check_in") @set:PropertyName("Status_Check_in")
    var statusCheckIn: String? = null,

    @get: PropertyName("Status_Validade") @set:PropertyName("Status_Validade")
    var statusValidade: String? = null,

    @get: PropertyName("Valor") @set:PropertyName("Valor")
    var valor: String? = null,

    @get: PropertyName("Validade") @set:PropertyName("Validade")
    var validade:String? = null

)
