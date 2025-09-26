package com.junior.migracaosevenmvvmcompose.presentation.navigation

import androidx.navigation.NavController

fun NavController.navigateSingleTopTo(route: String) {
    this.navigate(route) {
        popUpTo(this@navigateSingleTopTo.graph.startDestinationId) {
            saveState = true
        }
        launchSingleTop = true
        restoreState = true
    }
}

fun NavController.navigateToLogin2FA(userId: String){
    this.navigate(NavRoutes.Login2FA.createRoute(userId))
}

// **NOVA FUNÇÃO MAIS ROBUSTA PARA LOGOUT/LIMPEZA**
fun NavController.navigateToLoginAndClearStack(){
    this.navigate(NavRoutes.Login.route){
        // Remove TODAS as rotas da pilha de backstack.
        popUpTo(this@navigateToLoginAndClearStack.graph.id){
            inclusive = true
        }
        launchSingleTop = true
    }
}

fun NavController.navigateToHome(popUpToStart: Boolean = false){
    if (popUpToStart){
        this.navigate(NavRoutes.Home.route){
            popUpTo(NavRoutes.Home.route){ inclusive = true }
        }
    } else {
        this.navigate(NavRoutes.Home.route)
    }
}