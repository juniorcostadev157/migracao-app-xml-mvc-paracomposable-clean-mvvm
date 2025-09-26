    package com.junior.migracaosevenmvvmcompose.presentation.screens.login

    import android.widget.Toast
    import androidx.compose.foundation.Image
    import androidx.compose.foundation.background
    import androidx.compose.foundation.layout.*
    import androidx.compose.foundation.rememberScrollState
    import androidx.compose.foundation.shape.RoundedCornerShape
    import androidx.compose.foundation.text.KeyboardOptions
    import androidx.compose.foundation.verticalScroll
    import androidx.compose.material3.*
    import androidx.compose.runtime.*
    import androidx.compose.ui.Alignment
    import androidx.compose.ui.Modifier
    import androidx.compose.ui.platform.LocalContext
    import androidx.compose.ui.res.painterResource
    import androidx.compose.ui.text.font.FontWeight
    import androidx.compose.ui.text.input.KeyboardType
    import androidx.compose.ui.text.input.PasswordVisualTransformation
    import androidx.compose.ui.text.input.VisualTransformation
    import androidx.compose.ui.tooling.preview.Preview
    import androidx.compose.ui.unit.dp
    import androidx.compose.ui.unit.sp
    import androidx.hilt.navigation.compose.hiltViewModel
    import com.junior.migracaosevenmvvmcompose.R
    import com.junior.migracaosevenmvvmcompose.core.contants.Constants
    import com.junior.migracaosevenmvvmcompose.presentation.theme.Black
    import com.junior.migracaosevenmvvmcompose.presentation.theme.Red
    import com.junior.migracaosevenmvvmcompose.presentation.theme.White

    @Composable
    fun LoginScreen(
        viewModel: LoginViewModel = hiltViewModel(),
        onLoginSuccess: (String) -> Unit
    ) {
        val email by viewModel.email.collectAsState()
        val password by viewModel.password.collectAsState()
        val uiState by viewModel.uiState.collectAsState()
        val context = LocalContext.current
        var showPassword by remember { mutableStateOf(false) }

        // Eventos da UI
        LaunchedEffect(true) {
            viewModel.eventFlow.collect { event ->
                when (event) {
                    is LoginUiEvent.ShowToast -> {
                        Toast.makeText(context, event.message, Toast.LENGTH_SHORT).show()
                    }
                    is LoginUiEvent.NavigateToHome -> {
                        onLoginSuccess(event.userId)
                    }
                }
            }
        }

        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(White)
                .verticalScroll(rememberScrollState()),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            // Logo
            Image(
                painter = painterResource(id = R.drawable.logo),
                contentDescription = "Logo",
                modifier = Modifier
                    .fillMaxWidth()
                    .height(250.dp)
                    .padding(top = 16.dp)
            )

            Text(
                text = Constants.LoginStrings.NAME_LOGO,
                fontSize = 25.sp,
                fontWeight = FontWeight.Bold,
                color = Black,
                modifier = Modifier.padding(top = 16.dp)
            )

            Card(
                modifier = Modifier
                    .padding(horizontal = 20.dp, vertical = 24.dp)
                    .fillMaxWidth(),
                colors = CardDefaults.cardColors(containerColor = Black),
                shape = RoundedCornerShape(16.dp),
                elevation = CardDefaults.cardElevation(6.dp)
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp)
                ) {
                    // Email
                    OutlinedTextField(
                        value = email,
                        onValueChange = { viewModel.setEmail(it) },
                        modifier = Modifier.fillMaxWidth(),
                        label = { Text(Constants.LoginStrings.EMAIL_LABEL) },
                        singleLine = true,
                        isError = !uiState.emailError.isNullOrEmpty(),
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email),
                        colors = TextFieldDefaults.colors(
                            focusedTextColor = Black,
                            unfocusedTextColor = Black,
                            focusedContainerColor = White,
                            unfocusedContainerColor = White,
                            focusedIndicatorColor = Red,
                            unfocusedIndicatorColor = Black,
                            cursorColor = Red,
                            errorIndicatorColor = Red,
                            errorLabelColor = Red
                        )
                    )
                    if (!uiState.emailError.isNullOrEmpty()) {
                        Text(
                            text = uiState.emailError ?: "",
                            color = Red,
                            fontSize = 12.sp,
                            modifier = Modifier.padding(top = 4.dp)
                        )
                    }

                    Spacer(modifier = Modifier.height(16.dp))

                    // Senha
                    OutlinedTextField(
                        value = password,
                        onValueChange = { viewModel.setPassword(it) },
                        modifier = Modifier.fillMaxWidth(),
                        label = { Text(Constants.LoginStrings.PASSWORD_LABEL) },
                        singleLine = true,
                        isError = !uiState.passwordError.isNullOrEmpty(),
                        visualTransformation = if (showPassword) VisualTransformation.None else PasswordVisualTransformation(),
                        trailingIcon = {
                            val icon = if (showPassword) R.drawable.ic_visibility_off else R.drawable.ic_visibility
                            IconButton(onClick = { showPassword = !showPassword }) {
                                Icon(painter = painterResource(id = icon), contentDescription = null)
                            }
                        },
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
                        colors = TextFieldDefaults.colors(
                            focusedTextColor = Black,
                            unfocusedTextColor = Black,
                            focusedContainerColor = White,
                            unfocusedContainerColor = White,
                            focusedIndicatorColor = Red,
                            unfocusedIndicatorColor = Black,
                            cursorColor = Red,
                            errorIndicatorColor = Red,
                            errorLabelColor = Red
                        )
                    )
                    if (!uiState.passwordError.isNullOrEmpty()) {
                        Text(
                            text = uiState.passwordError ?: "",
                            color = Red,
                            fontSize = 12.sp,
                            modifier = Modifier.padding(top = 4.dp)
                        )
                    }

                    Spacer(modifier = Modifier.height(24.dp))

                    // Botão Login
                    Button(
                        onClick = { viewModel.login(email, password) },
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 40.dp),
                        shape = RoundedCornerShape(12.dp),
                        colors = ButtonDefaults.buttonColors(containerColor = Red),
                        enabled = !uiState.isLoading
                    ) {
                        Text(
                            text = Constants.LoginStrings.LOGIN_BUTTON,
                            color = White,
                            fontSize = 16.sp,
                            fontWeight = FontWeight.Bold,
                            modifier = Modifier.padding(vertical = 8.dp)
                        )
                    }

                    // Erro geral
                    if (!uiState.generalError.isNullOrEmpty()) {
                        Spacer(modifier = Modifier.height(8.dp))
                        Text(
                            text = uiState.generalError ?: "",
                            color = Red,
                            fontSize = 14.sp,
                            modifier = Modifier.align(Alignment.CenterHorizontally)
                        )
                    }

                    // Loading
                    if (uiState.isLoading) {
                        Spacer(modifier = Modifier.height(16.dp))
                        CircularProgressIndicator(
                            color = Red,
                            modifier = Modifier.align(Alignment.CenterHorizontally)
                        )
                    }
                }
            }
        }
    }

    @Preview(showBackground = true, showSystemUi = true)
    @Composable
    fun PreviewLoginScreen() {
        LoginScreen(onLoginSuccess = {})
    }
