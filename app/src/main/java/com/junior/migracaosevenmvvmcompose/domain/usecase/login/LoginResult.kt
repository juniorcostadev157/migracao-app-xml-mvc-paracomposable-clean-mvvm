package com.junior.migracaosevenmvvmcompose.domain.usecase.login

sealed class LoginResult {
    data class Success(val userId: String) : LoginResult()
    object WrongPassword : LoginResult()
    object UserNotFound : LoginResult()
    object NetworkError : LoginResult()
    data class UnknownError(val message: String?) : LoginResult()
}

sealed class Login2FAResult{
    object EmptyFields: Login2FAResult()
    object Wrong2FACode: Login2FAResult()
    object Success: Login2FAResult()
    data class UnknownError(val message: String?): Login2FAResult()
}