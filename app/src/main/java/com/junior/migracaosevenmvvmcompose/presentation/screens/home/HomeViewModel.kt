package com.junior.migracaosevenmvvmcompose.presentation.screens.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.junior.migracaosevenmvvmcompose.data.datasource.local.UsersPreference
import com.junior.migracaosevenmvvmcompose.data.model.UserSession
import com.junior.migracaosevenmvvmcompose.domain.usecase.login.LogoutUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import jakarta.inject.Inject
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val logoutUseCase: LogoutUseCase,
    userPreferences: UsersPreference
) : ViewModel() {


    private val _showWelcome = MutableStateFlow(true)
    val showWelcome: StateFlow<Boolean> = _showWelcome.asStateFlow()

    fun finishWelcome() {
        _showWelcome.value = false
    }


    private val _userSession = MutableStateFlow<UserSession?>(null)
    val userSession: StateFlow<UserSession?> = _userSession.asStateFlow()

    init {
        // Coletando do DataStore e atualizando o StateFlow local
        viewModelScope.launch {
            userPreferences.getUserSession().collect { session ->
                _userSession.value = session
            }
        }
    }


    fun logout() {
        viewModelScope.launch {
            logoutUseCase()
            _userSession.value = null
        }
    }
}
