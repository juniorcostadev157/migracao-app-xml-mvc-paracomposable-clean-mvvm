package com.junior.migracaosevenmvvmcompose.presentation.theme

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import com.google.accompanist.systemuicontroller.rememberSystemUiController

private val DarkColorScheme = darkColorScheme(
    primary = Purple80,
    secondary = PurpleGrey80,
    tertiary = Pink80
)

private val LightColorScheme = lightColorScheme(
    primary = Purple40,
    secondary = PurpleGrey40,
    tertiary = Pink40

    /* Other default colors to override
    background = Color(0xFFFFFBFE),
    surface = Color(0xFFFFFBFE),
    onPrimary = Color.White,
    onSecondary = Color.White,
    onTertiary = Color.White,
    onBackground = Color(0xFF1C1B1F),
    onSurface = Color(0xFF1C1B1F),
    */
)


private val AppColorScheme = lightColorScheme(
    primary = White,   // usado no TopBar, FAB, botões
    onPrimary = Black,    // cor do texto/ícone em cima do primary
    background = Black,   // fundo da tela
    surface = Black,      // fundo dos cards/áreas
    onBackground = White,
    onSurface = Black
)

@Composable
fun MigracaoSevenMVVMComposeTheme(

    content: @Composable () -> Unit
) {
    val systemUiController = rememberSystemUiController()
    SideEffect {
        systemUiController.setStatusBarColor(
            color = Black,
            darkIcons = false
        )
    }
    MaterialTheme (
        colorScheme = AppColorScheme,
        typography = Typography,
        content = content
    )
}