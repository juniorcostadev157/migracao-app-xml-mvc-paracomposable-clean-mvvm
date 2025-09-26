package com.junior.migracaosevenmvvmcompose.data.model

import com.google.firebase.firestore.PropertyName


data class UserResponse(


    @get:PropertyName("Email") @set:PropertyName("Email")
    var email: String = "",

    @get:PropertyName("Login") @set:PropertyName("Login")
    var login: String = "",

    @get:PropertyName("Nome") @set:PropertyName("Nome")
    var nome: String = "",

    @get:PropertyName("Telefone") @set:PropertyName("Telefone")
    var telefone: String = "",

    @get:PropertyName("Tipo") @set:PropertyName("Tipo")
    var tipo: String = "",

    @get:PropertyName("Uid") @set:PropertyName("Uid")
    var uid: String = "",

    var currentTotp: String = "",
    var fcmToken: String = "",
    var profileImageUrl: String = "",
    var totpUsed: Boolean = false

)
