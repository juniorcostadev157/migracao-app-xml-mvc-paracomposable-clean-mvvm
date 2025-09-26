package com.junior.migracaosevenmvvmcompose.presentation.navigation

import android.annotation.SuppressLint
import androidx.compose.material3.DrawerState
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.platform.LocalContext
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.junior.migracaosevenmvvmcompose.data.model.UserSession
import com.junior.migracaosevenmvvmcompose.presentation.components.SplashScreen
import com.junior.migracaosevenmvvmcompose.presentation.components.menudrawer.DrawerContent
import com.junior.migracaosevenmvvmcompose.presentation.components.menudrawer.DrawerMenuItemType
import com.junior.migracaosevenmvvmcompose.presentation.screens.equipmentos.EquipamentosScreen
import com.junior.migracaosevenmvvmcompose.presentation.screens.ferramental.FerramentalTecnicoScreen
import com.junior.migracaosevenmvvmcompose.presentation.screens.home.HomeScreenWithPermissions
import com.junior.migracaosevenmvvmcompose.presentation.screens.home.HomeViewModel
import com.junior.migracaosevenmvvmcompose.presentation.screens.login.LoginScreen
import com.junior.migracaosevenmvvmcompose.presentation.screens.login.LoginViewModel
import com.junior.migracaosevenmvvmcompose.presentation.screens.login2fa.Login2FAScreen
import com.junior.migracaosevenmvvmcompose.presentation.screens.login2fa.Login2FAViewModel
import com.junior.migracaosevenmvvmcompose.presentation.screens.login2fa.SessionViewModel
import com.junior.migracaosevenmvvmcompose.presentation.screens.movimentacao.instalacao.InstalacaoScreen
import com.junior.migracaosevenmvvmcompose.presentation.screens.perfil.PerfilScreen
import com.junior.migracaosevenmvvmcompose.presentation.screens.solicitacao.checkin.SolicitacaoCheckinScreen
import com.junior.migracaosevenmvvmcompose.presentation.screens.solicitacao.equipamentos.SolicitacaoEquipamentoScreen
import com.junior.migracaosevenmvvmcompose.presentation.screens.solicitacao.ferramental.SolicitacaoFerramentalScreen
import com.junior.migracaosevenmvvmcompose.presentation.theme.Black
import kotlinx.coroutines.launch

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun AppNavHost() {
    val navController = rememberNavController()
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination?.route

    val sessionViewModel: SessionViewModel = hiltViewModel()
    val homeViewModel: HomeViewModel = hiltViewModel()

    val loginViewModel: LoginViewModel = hiltViewModel()
    val login2FAViewModel: Login2FAViewModel = hiltViewModel()

    val loginStage by sessionViewModel.userLoginStage.collectAsState(initial = null)
    val userPrefs by homeViewModel.userSession.collectAsState(initial = UserSession()) // Pegando a sessão
    val drawerState = rememberDrawerState(DrawerValue.Closed)
    val scope = rememberCoroutineScope()


    val initialStartDestination = when (loginStage) {
        is LoginStage.Authenticated -> NavRoutes.Home.route
        is LoginStage.LoggedOut, is LoginStage.Unknow, null -> NavRoutes.Login.route
    }


    LaunchedEffect(loginStage) {
        when (loginStage) {
            is LoginStage.Authenticated -> {

                if (currentRoute != NavRoutes.Home.route && currentRoute != NavRoutes.Login2FA.route) {
                    navController.navigateToHome(popUpToStart = true)
                }
            }
            is LoginStage.LoggedOut -> {

                navController.navigateToLoginAndClearStack()
                drawerState.close()
                loginViewModel.clearFields()
                login2FAViewModel.clearCodeField()
            }
            is LoginStage.Unknow, null -> {

            }
        }
    }


    if (loginStage == null || loginStage is LoginStage.Unknow) {
        SplashScreen()
        return
    }


    val drawerEnabled = currentRoute !in listOf(
        NavRoutes.Login.route,
        NavRoutes.Login2FA.route + "/{userId}"
    )

    if (drawerEnabled) {
        ModalNavigationDrawer(
            drawerState = drawerState,
            drawerContent = {
                DrawerContent(
                    userPrefs = userPrefs, // Passando a sessão diretamente
                    onMenuItemClick = { menuItem ->
                        scope.launch { drawerState.close() }
                        when (menuItem) {
                            DrawerMenuItemType.SolicitacaoEquipamentos ->
                                navController.navigateSingleTopTo(NavRoutes.SolicitacaoEquipamentos.route)

                            DrawerMenuItemType.Perfil ->
                                navController.navigateSingleTopTo(NavRoutes.Perfil.route)

                            DrawerMenuItemType.Clientes -> TODO()
                            DrawerMenuItemType.Devolucao -> TODO()
                            DrawerMenuItemType.Equipamento -> {
                                navController.navigateSingleTopTo(NavRoutes.Equipamentos.route)
                            }
                            DrawerMenuItemType.Ferramental -> {
                                navController.navigateSingleTopTo(NavRoutes.Ferramental.route)
                            }
                            DrawerMenuItemType.HistoricoCheckin -> TODO()
                            DrawerMenuItemType.HistoricoMovimentacoes -> TODO()
                            DrawerMenuItemType.Movimentacao -> TODO()
                            // No clique do menu 'Sair', chamamos a função que faz o logout no ViewModel
                            DrawerMenuItemType.Sair -> {
                                scope.launch { homeViewModel.logout() }
                            }
                            DrawerMenuItemType.SolicitacaoCheckin -> {
                                navController.navigateSingleTopTo(NavRoutes.SolicitacaoCheckin.route)
                            }
                            DrawerMenuItemType.SolicitacaoFerramental -> {
                                navController.navigateSingleTopTo(NavRoutes.SolicitacaoFerramental.route)
                            }
                            DrawerMenuItemType.Transferir -> TODO()
                            DrawerMenuItemType.Desconexao -> TODO()
                            DrawerMenuItemType.Instalacao -> {
                                navController.navigateSingleTopTo(NavRoutes.Instalacao.route)
                            }
                            DrawerMenuItemType.Troca -> TODO()
                        }
                    },
                    // A função onLogoutConfirmed agora apenas chama o logout do ViewModel
                    onLogoutConfirmed = {
                        scope.launch { homeViewModel.logout() }
                    }
                )
            },
            scrimColor = Black.copy(alpha = 0.3f)
        ) {
            NavigationGraph(
                navController = navController,
                startDestination = initialStartDestination,
                drawerState = drawerState
            )
        }
    } else {
        // Quando drawer não deve aparecer (login e 2FA)
        NavigationGraph(
            navController = navController,
            startDestination = initialStartDestination,
            drawerState = drawerState
        )
    }
}

@Composable
private fun NavigationGraph(
    navController: NavHostController,
    startDestination: String,
    drawerState: DrawerState
) {
    val scope = rememberCoroutineScope()
    val loginViewModel: LoginViewModel = hiltViewModel()
    val login2FAViewModel: Login2FAViewModel = hiltViewModel()
    val context = LocalContext.current
    val activity = context as android.app.Activity

    NavHost(
        navController = navController,
        startDestination = startDestination
    ) {
        // Login
        composable(NavRoutes.Login.route) {
            LoginScreen(
                viewModel = loginViewModel,
                onLoginSuccess = { userId ->
                    navController.navigateToLogin2FA(userId)
                }
            )
        }

        // Login 2FA
        composable(
            route = "${NavRoutes.Login2FA.route}/{userId}",
            arguments = listOf(navArgument("userId") { type = NavType.StringType })
        ) {
            val userId = it.arguments?.getString("userId") ?: ""
            Login2FAScreen(
                viewModel = login2FAViewModel,
                userId = userId,
                onSuccess = {
                    navController.navigateToHome(popUpToStart = true)
                }
            )
        }

        // Home
        composable(NavRoutes.Home.route) {
            HomeScreenWithPermissions(activity = activity, onMenuClick = { scope.launch { drawerState.open() }})
        }

        // Solicitação
        composable(NavRoutes.SolicitacaoEquipamentos.route) {
            SolicitacaoEquipamentoScreen(onMenuClick = { scope.launch { drawerState.open() } })
        }

        // Perfil
        composable(NavRoutes.Perfil.route) {
            PerfilScreen(onMenuClick = { scope.launch { drawerState.open() } })
        }

        composable(NavRoutes.SolicitacaoFerramental.route){
            SolicitacaoFerramentalScreen(onMenuClick = {scope.launch { drawerState.open() }})
        }

        composable(NavRoutes.SolicitacaoCheckin.route){
            SolicitacaoCheckinScreen(onMenuClick = {scope.launch { drawerState.open() }})
        }

        composable(NavRoutes.Equipamentos.route){
            EquipamentosScreen(onMenuClick = {scope.launch { drawerState.open() }})
        }

        composable(NavRoutes.Ferramental.route){
            FerramentalTecnicoScreen(onMenuClick = {scope.launch { drawerState.open() }})
        }

        composable(NavRoutes.Instalacao.route){
            InstalacaoScreen(onMenuClick = {scope.launch { drawerState.open() }})
        }
    }
}