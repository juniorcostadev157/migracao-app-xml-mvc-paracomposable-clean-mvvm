package com.junior.migracaosevenmvvmcompose.presentation.screens.login


sealed class LoginUiEvent {
    data class ShowToast(val message: String): LoginUiEvent()
    data class NavigateToHome(val userId: String): LoginUiEvent()
}
