package com.junior.migracaosevenmvvmcompose.presentation.screens.login

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.junior.migracaosevenmvvmcompose.core.contants.Constants
import com.junior.migracaosevenmvvmcompose.domain.usecase.login.LoginUseCase
import com.junior.migracaosevenmvvmcompose.domain.usecase.login.LoginResult
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch
import javax.inject.Inject
import android.util.Patterns

@HiltViewModel
class LoginViewModel @Inject constructor(private val loginUseCase: LoginUseCase) : ViewModel() {

    private val _email = MutableStateFlow("")
    val email: StateFlow<String> = _email

    private val _password = MutableStateFlow("")
    val password: StateFlow<String> = _password

    private val _uiState = MutableStateFlow(LoginUiState())
    val uiState: StateFlow<LoginUiState> = _uiState

    private val _eventFlow = MutableSharedFlow<LoginUiEvent>()
    val eventFlow = _eventFlow.asSharedFlow()

    fun setEmail(value: String) { _email.value = value }
    fun setPassword(value: String) { _password.value = value }

    fun login(email: String, password: String) {
        viewModelScope.launch {

            when {
                email.isBlank() || password.isBlank() -> {
                    _uiState.value = LoginUiState(
                        emailError = if (email.isBlank()) "Campo obrigatório" else null,
                        passwordError = if (password.isBlank()) "Campo obrigatório" else null
                    )
                    _eventFlow.emit(LoginUiEvent.ShowToast(Constants.LoginStrings.EMPTY_FIELDS_ERROR))
                    return@launch
                }
                !Patterns.EMAIL_ADDRESS.matcher(email).matches() -> {
                    _uiState.value = LoginUiState(emailError = "Email inválido")
                    _eventFlow.emit(LoginUiEvent.ShowToast(Constants.LoginStrings.INVALID_EMAIL_ERROR))
                    return@launch
                }
            }

            _uiState.value = LoginUiState(isLoading = true)

            when (val result = loginUseCase(email, password)) {
                is LoginResult.Success -> {
                    _uiState.value = LoginUiState(isSuccess = true)
                    _eventFlow.emit(LoginUiEvent.NavigateToHome(result.userId))
                }
                is LoginResult.WrongPassword -> {
                    _uiState.value = LoginUiState(passwordError = "Senha incorreta")
                    _eventFlow.emit(LoginUiEvent.ShowToast(Constants.LoginStrings.WRONG_PASSWORD_ERROR))
                }
                is LoginResult.UserNotFound -> {
                    _uiState.value = LoginUiState(generalError = "Usuário não encontrado")
                    _eventFlow.emit(LoginUiEvent.ShowToast(Constants.LoginStrings.USER_NOT_FOUND_ERROR))
                }
                is LoginResult.NetworkError -> {
                    _uiState.value = LoginUiState(generalError = "Erro de rede")
                    _eventFlow.emit(LoginUiEvent.ShowToast(Constants.LoginStrings.NETWORK_ERROR))
                }
                is LoginResult.UnknownError -> {
                    _uiState.value = LoginUiState(generalError = "Erro desconhecido")
                    _eventFlow.emit(LoginUiEvent.ShowToast(Constants.LoginStrings.UNKNOWN_ERROR))
                }
            }
        }
    }

    fun clearFields() {
        _email.value = ""
        _password.value = ""
        _uiState.value = LoginUiState()
    }
}

