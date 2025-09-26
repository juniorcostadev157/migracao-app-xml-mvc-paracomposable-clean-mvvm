package com.junior.migracaosevenmvvmcompose.presentation.screens.login2fa


import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.junior.migracaosevenmvvmcompose.core.contants.Constants
import com.junior.migracaosevenmvvmcompose.domain.usecase.login.Login2FAResult
import com.junior.migracaosevenmvvmcompose.domain.usecase.login.SaveUserSessionUseCase
import com.junior.migracaosevenmvvmcompose.domain.usecase.login.Verify2FAUseCase
import com.junior.migracaosevenmvvmcompose.domain.usecase.users.GetUserDataUseCase
import com.junior.migracaosevenmvvmcompose.presentation.screens.login.LoginUiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class Login2FAViewModel @Inject constructor(
    private val verify2FAUseCase: Verify2FAUseCase,
    private val saveUserSessionUseCase: SaveUserSessionUseCase,
    private val getUserDataUseCase: GetUserDataUseCase):
    ViewModel() {

    private val _code = MutableStateFlow("")
    val code: StateFlow<String> = _code

    private val _eventFlow = MutableSharedFlow<String>()
    val eventFlow = _eventFlow.asSharedFlow()

    private val _uiState = MutableStateFlow(LoginUiState())
    val uiState = _uiState.asStateFlow()




    fun setCode(code: String){
        _code.value = code
    }

    fun verifyCode(userId: String, code: String, onSuccess: ()-> Unit){
        viewModelScope.launch {


            _uiState.value = LoginUiState(isLoading = true)
            val result = verify2FAUseCase(userId, code)



            when(result){
                is Login2FAResult.Success -> {


                    val userData = getUserDataUseCase(userId).getOrNull()


                    userData?.let {

                        saveUserSessionUseCase(it)
                    }

                    _uiState.value =  LoginUiState(isSuccess = true)
                    onSuccess()
                }
                is Login2FAResult.EmptyFields -> {
                    _eventFlow.emit(Constants.LoginStrings.EMPTY_CODE_ERROR)
                    _uiState.value = LoginUiState()
                }
                is Login2FAResult.Wrong2FACode -> {
                    _eventFlow.emit(Constants.LoginStrings.WRONG_CODE_ERROR)
                    _uiState.value = LoginUiState()
                }
                is Login2FAResult.UnknownError -> {

                    _eventFlow.emit(Constants.LoginStrings.UNKNOWN_ERROR)
                    _uiState.value = LoginUiState()
                }
            }
        }
    }

    fun clearCodeField() {
        _code.value = ""
    }

}