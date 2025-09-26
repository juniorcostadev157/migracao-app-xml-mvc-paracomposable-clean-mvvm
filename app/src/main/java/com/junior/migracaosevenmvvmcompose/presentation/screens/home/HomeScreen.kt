package com.junior.migracaosevenmvvmcompose.presentation.screens.home


import android.content.res.Configuration
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import com.junior.migracaosevenmvvmcompose.R
import com.junior.migracaosevenmvvmcompose.data.model.UserSession
import com.junior.migracaosevenmvvmcompose.presentation.components.AppTopBar
import com.junior.migracaosevenmvvmcompose.presentation.theme.White

@Composable
fun HomeScreen(onMenuClick: () -> Unit,
                   viewModel: HomeViewModel = hiltViewModel()
) {
    val showWelcome by viewModel.showWelcome.collectAsState()
    val userPref by viewModel.userSession.collectAsState(initial = UserSession())

    Scaffold(
        containerColor = White,
        topBar = { AppTopBar(title = "Home", onMenuClick = onMenuClick) }
    ) { padding ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .background(White),
            contentAlignment = Alignment.Center
        ) {


            if(!showWelcome){

                Image(
                    painter = painterResource(R.drawable.logo2),
                    contentDescription = "Logo"
                )
            }

            // HomeScreen.kt

            if (showWelcome) {
                val nomeDoUsuario = userPref?.nome

                if (nomeDoUsuario != null) {

                    WelcomeWithMarquee(nome = nomeDoUsuario) {
                        viewModel.finishWelcome()
                    }
                } else {

                    CircularProgressIndicator(Modifier.align(Alignment.Center))
                }
            }

        }
    }
}




@Preview(showBackground = true)
@Composable
fun PreviewHomeScreenLight() {
    HomeScreen(onMenuClick = {})
}

@Preview(showBackground = true, uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
fun PreviewHomeScreenDark() {
    HomeScreen(onMenuClick = {})
}