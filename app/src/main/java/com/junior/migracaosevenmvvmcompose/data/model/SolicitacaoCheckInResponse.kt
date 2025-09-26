package com.junior.migracaosevenmvvmcompose.data.model

import com.google.firebase.Timestamp
import com.google.firebase.firestore.PropertyName


data class SolicitacaoCheckInResponse(

    var id: String = "",
    @get: PropertyName("DataEnvio") @set:PropertyName("DataEnvio")
    var dataEnvio: Timestamp? = null,
    @get: PropertyName("Ferramental") @set:PropertyName("Ferramental")
    var ferramental: List<FerramentalListCheckInResponse> = emptyList(),
    @get: PropertyName("LoginTecnico") @set:PropertyName("LoginTecnico")
    var loginTecnico: String? = null,
    @get: PropertyName("NomeAlmox") @set:PropertyName("NomeAlmox")
    var nomeAlmox: String? = null,
    @get:PropertyName("Status") @set:PropertyName("Status")
    var status: String? = null

)

data class FerramentalListCheckInResponse(
    @get: PropertyName("Nome_ferramenta") @set:PropertyName("Nome_ferramenta")
    var nomeFerramenta: String? = null,
    @get: PropertyName("Status_Check_in") @set:PropertyName("Status_Check_in")
    var statusCheckIn: String? = null
)