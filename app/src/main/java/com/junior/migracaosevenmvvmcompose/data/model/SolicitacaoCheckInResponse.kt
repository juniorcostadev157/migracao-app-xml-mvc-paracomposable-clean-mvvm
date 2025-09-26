package com.junior.migracaosevenmvvmcompose.data.model

import com.google.firebase.Timestamp


data class SolicitacaoCheckInResponse(

    var id: String = "",
    val DataEnvio: Timestamp? = null,
    val Ferramental: List<FerramentalModel?> = emptyList(),
    val LoginTecnico: String? = null,
    val NomeAlmox: String? = null,
    val Status: String? = null

)

data class FerramentalModel(
    val Nome_ferramenta: String? = null,
    val Status_Check_in: String? = null
)