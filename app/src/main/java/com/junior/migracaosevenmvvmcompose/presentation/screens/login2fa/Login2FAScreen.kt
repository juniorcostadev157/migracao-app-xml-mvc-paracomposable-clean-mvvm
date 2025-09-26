package com.junior.migracaosevenmvvmcompose.presentation.screens.login2fa

import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.LocalTextStyle
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.junior.migracaosevenmvvmcompose.R
import com.junior.migracaosevenmvvmcompose.core.contants.Constants
import com.junior.migracaosevenmvvmcompose.presentation.theme.Black
import com.junior.migracaosevenmvvmcompose.presentation.theme.DarkGrey
import com.junior.migracaosevenmvvmcompose.presentation.theme.Red
import com.junior.migracaosevenmvvmcompose.presentation.theme.White


@Composable
fun Login2FAScreen(
    viewModel: Login2FAViewModel = hiltViewModel(),
    userId: String,
    onSuccess: () -> Unit
) {
    val code by viewModel.code.collectAsState()
    val eventFlow = viewModel.eventFlow
    val context = LocalContext.current
    val uiState by viewModel.uiState.collectAsState()

    LaunchedEffect(true) {
        eventFlow.collect { event->
            when(event){
                Constants.LoginStrings.EMPTY_CODE_ERROR->{
                    Toast.makeText(context, Constants.LoginStrings.EMPTY_CODE_ERROR, Toast.LENGTH_SHORT).show()
                }
                Constants.LoginStrings.WRONG_CODE_ERROR->{
                    Toast.makeText(context, Constants.LoginStrings.WRONG_CODE_ERROR, Toast.LENGTH_SHORT).show()
                }
                Constants.LoginStrings.UNKNOWN_ERROR->{
                    Toast.makeText(context, Constants.LoginStrings.UNKNOWN_ERROR, Toast.LENGTH_SHORT).show()
                }
            }

        }
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(
                brush = Brush.verticalGradient(listOf(White, DarkGrey))
            )
            .padding(16.dp)
    ) {
        Column(
            modifier = Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            // Logo
            Image(
                painter = painterResource(id = R.drawable.logo),
                contentDescription = "Logo",
                modifier = Modifier
                    .size(220.dp)
                    .padding(top = 32.dp)
            )

            Spacer(modifier = Modifier.weight(1f))


            OutlinedTextField(
                value = code,
                onValueChange = { viewModel.setCode(it) },
                label = { Text(Constants.LoginStrings.CODE_LABEL) },
                singleLine = true,
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                modifier = Modifier
                    .fillMaxWidth()
                    .height(70.dp)
                    .padding(horizontal = 8.dp),
                textStyle = LocalTextStyle.current.copy(
                    textAlign = TextAlign.Center,
                    color = Black
                ),
                colors = TextFieldDefaults.colors(
                    focusedTextColor = Black,
                    unfocusedTextColor = Black,
                    focusedContainerColor = White,
                    unfocusedContainerColor = White,
                    focusedIndicatorColor = Red,
                    unfocusedIndicatorColor = Black,
                    cursorColor = Red,
                    errorCursorColor = Red,
                    errorIndicatorColor = Red
                ),

            )




            Button(
                onClick = {
                    viewModel.verifyCode(userId, code, onSuccess)
                },
                modifier = Modifier
                    .padding(top = 24.dp)
                    .width(150.dp)
                    .height(50.dp),
                colors = ButtonDefaults.buttonColors(containerColor = Red)
            ) {
                Text(
                    text = Constants.LoginStrings.VERIFY_BUTTON,
                    color = White,
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold
                )
            }
            if(uiState.isLoading){
                Spacer(modifier = Modifier.height(16.dp))
                CircularProgressIndicator(color = Red,
                    modifier = Modifier.align(Alignment.CenterHorizontally))
            }

            Spacer(modifier = Modifier.weight(2f))
        }
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun PreviewLogin2FA(){


}