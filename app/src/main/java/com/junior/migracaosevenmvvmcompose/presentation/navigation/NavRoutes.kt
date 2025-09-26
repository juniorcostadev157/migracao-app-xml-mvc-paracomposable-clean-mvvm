package com.junior.migracaosevenmvvmcompose.presentation.navigation

import com.junior.migracaosevenmvvmcompose.core.contants.Constants

sealed class NavRoutes(val route: String) {
    data object Login: NavRoutes(Constants.NavRoutes.LOGIN_SCREEN)
    data object Login2FA: NavRoutes(Constants.NavRoutes.LOGIN2FA_SCREEN){
        fun createRoute(userId: String) = "$route/$userId"
    }
    data object Home: NavRoutes(Constants.NavRoutes.HOME_SCREEN)

    data object SolicitacaoEquipamentos: NavRoutes(Constants.NavRoutes.SOLICITACAO_EQUIPAMENTOS_SCREEN)
    data object Perfil: NavRoutes(Constants.NavRoutes.PERFIL_SCREEN)
    data object SolicitacaoFerramental: NavRoutes(Constants.NavRoutes.SOLICITACAO_FERRAMENTAL_SCREEN)
    data object SolicitacaoCheckin: NavRoutes(Constants.NavRoutes.SOLICITACAO_CHECKIN_SCREEN)
    data object Equipamentos: NavRoutes(Constants.NavRoutes.EQUIPAMENTOS_SCREEN)
    data object Ferramental: NavRoutes(Constants.NavRoutes.FERRAMENTAL_SCREEN)
    data object Instalacao: NavRoutes(Constants.NavRoutes.INSTALACAO_SCREEN)


}