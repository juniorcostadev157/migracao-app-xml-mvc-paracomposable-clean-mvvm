package com.junior.migracaosevenmvvmcompose.presentation.navigation

sealed class LoginStage {
    object Unknow: LoginStage()
    object Authenticated: LoginStage()
    object LoggedOut: LoginStage()


}