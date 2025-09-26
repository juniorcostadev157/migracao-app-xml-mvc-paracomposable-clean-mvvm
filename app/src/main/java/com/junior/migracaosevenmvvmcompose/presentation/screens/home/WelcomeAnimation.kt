package com.junior.migracaosevenmvvmcompose.presentation.screens.home

import android.annotation.SuppressLint
import androidx.compose.animation.core.*
import androidx.compose.foundation.layout.*
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.airbnb.lottie.compose.*
import com.junior.migracaosevenmvvmcompose.R


@SuppressLint("ConfigurationScreenWidthHeight")
@Composable
fun WelcomeWithMarquee(nome: String, onFinish: () -> Unit) {

    val composition by rememberLottieComposition(LottieCompositionSpec.RawRes(R.raw.hello))
    val progress by animateLottieCompositionAsState(
        composition,
        iterations = LottieConstants.IterateForever // roda infinito
    )

    // --- Animação do texto tipo "cobrinha" ---
    val configuration = LocalConfiguration.current
    val screenWidth = configuration.screenWidthDp.dp
    val textWidth = 300.dp

    val offsetX = remember { Animatable(-textWidth.value) } // começa fora da tela
    LaunchedEffect(Unit) {
        offsetX.animateTo(
            targetValue = screenWidth.value,
            animationSpec = tween(durationMillis = 12000, easing = LinearEasing)
        )
        onFinish()
    }

    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            // Boneco Lottie
            LottieAnimation(
                composition = composition,
                progress = { progress },
                modifier = Modifier.size(180.dp)
            )

            Spacer(modifier = Modifier.height(8.dp))

            // Texto tipo cobrinha sobre o boneco
            Box(modifier = Modifier.height(30.dp)) {
                Text(
                    text = "Bem-vindo, $nome 👋",
                    style = MaterialTheme.typography.headlineSmall.copy(fontSize = 24.sp),
                    modifier = Modifier.offset(x = offsetX.value.dp, y = 0.dp)
                )
            }
        }
    }
}

