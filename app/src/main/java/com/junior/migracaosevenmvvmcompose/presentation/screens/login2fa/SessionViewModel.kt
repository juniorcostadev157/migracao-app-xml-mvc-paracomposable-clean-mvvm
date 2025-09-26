package com.junior.migracaosevenmvvmcompose.presentation.screens.login2fa

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.junior.migracaosevenmvvmcompose.domain.usecase.login.ObserveLoginStageUseCase
import com.junior.migracaosevenmvvmcompose.presentation.navigation.LoginStage
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SessionViewModel @Inject constructor(
    observeLoginStageUseCase: ObserveLoginStageUseCase
) : ViewModel() {


    private val _userLoginStage = MutableStateFlow<LoginStage?>(null)
    val userLoginStage: StateFlow<LoginStage?> = _userLoginStage.asStateFlow()

    init {

        viewModelScope.launch {
            observeLoginStageUseCase().collect { stage ->
                _userLoginStage.value = stage
            }
        }
    }
}
