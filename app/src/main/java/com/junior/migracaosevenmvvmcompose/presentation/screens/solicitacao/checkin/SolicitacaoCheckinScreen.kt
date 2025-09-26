package com.junior.migracaosevenmvvmcompose.presentation.screens.solicitacao.checkin


import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.junior.migracaosevenmvvmcompose.core.contants.Constants
import com.junior.migracaosevenmvvmcompose.core.utils.DateUtils
import com.junior.migracaosevenmvvmcompose.domain.model.SolicitacaoCheckIn
import com.junior.migracaosevenmvvmcompose.presentation.components.AppTopBar
import com.junior.migracaosevenmvvmcompose.presentation.theme.Black
import com.junior.migracaosevenmvvmcompose.presentation.theme.Green
import com.junior.migracaosevenmvvmcompose.presentation.theme.IceWhite
import com.junior.migracaosevenmvvmcompose.presentation.theme.Red
import com.junior.migracaosevenmvvmcompose.presentation.theme.White
import com.junior.migracaosevenmvvmcompose.presentation.theme.Yellow

@Composable
fun SolicitacaoCheckinScreen(
    onMenuClick: ()-> Unit,
    viewModel: SolicitacaoCheckinViewModel = hiltViewModel()
) {


    val uiState by viewModel.uiState.collectAsState()



    Scaffold(
        containerColor = White,
        topBar = { AppTopBar(title = "Solicitação - Check in", onMenuClick = onMenuClick) }
    ) { padding ->

        if (uiState.isLoading) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(padding),
                contentAlignment = Alignment.Center
            ) {
                CircularProgressIndicator(color = Black)
            }
        } else if (uiState.data.isEmpty()) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(padding),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = Constants.SolicitacaoStrings.MESSAGE_EMPTY_SOLICITACAO,
                    color = Black,
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold
                )
            }
        } else {
            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(padding),
                contentPadding = PaddingValues(12.dp),
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                items(uiState.data) { solicitacao ->
                    SolicitacaoCardCheckin(solicitacao, viewModel)
                }
            }


        }
    }
}

@Composable
fun SolicitacaoCardCheckin(
    solicitacao: SolicitacaoCheckIn,
    viewModel: SolicitacaoCheckinViewModel = hiltViewModel()
) {

    val context = LocalContext.current

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp),
        colors = CardDefaults.cardColors(containerColor = IceWhite),
        elevation = CardDefaults.cardElevation(defaultElevation = 6.dp),
        shape = MaterialTheme.shapes.medium
    ) {
        Column(modifier = Modifier.padding(12.dp)) {
            // Cabeçalho
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(Black)
                    .padding(8.dp),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = "Solicitação Check in",
                    color = White,
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold
                )
            }

            Spacer(modifier = Modifier.height(8.dp))


            Text(
                "Almoxarife: ${solicitacao.nomeAlmox ?: "Desconhecido"}",
                fontWeight = FontWeight.Medium
            )
            Text(
                "Login Técnico: ${solicitacao.loginTecnico ?: "Desconhecido"}",
                fontWeight = FontWeight.Medium
            )
            Text(
                "Quant. Ferramentas: ${solicitacao.ferramental.size}",
                fontWeight = FontWeight.Medium
            )

            Spacer(modifier = Modifier.height(6.dp))

            // Lista de equipamentos
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(Color(0xFFF5F5F5))
                    .padding(8.dp)
            ) {
                Text(
                    "Ferramental Check in:",
                    fontWeight = FontWeight.Bold,
                    fontSize = 16.sp,
                    modifier = Modifier.align(Alignment.CenterHorizontally)
                )
                Spacer(modifier = Modifier.height(4.dp))

                solicitacao.ferramental.forEach { ferramental ->
                    Text(
                        "• ${ferramental.nomeFerramenta}",
                        fontSize = 14.sp,
                        modifier = Modifier.align(Alignment.CenterHorizontally)
                    )
                    Text(
                        text = "Status: ${ferramental.statusCheckIn}",
                        fontSize = 14.sp,
                        modifier = Modifier.align(Alignment.CenterHorizontally)
                    )
                    Spacer(modifier = Modifier.height(12.dp))


                }

                Spacer(modifier = Modifier.height(6.dp))

                // Data formatada
                val dataFormatada = DateUtils.formatTimestamp(solicitacao.dataEnvio)

                Text("Data: $dataFormatada", fontWeight = FontWeight.Medium)


                Text(
                    "Status: ${solicitacao.status ?: "Pendente"}",
                    color = Yellow,
                    fontWeight = FontWeight.Bold
                )

                Spacer(modifier = Modifier.height(12.dp))

                // Botões
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    Button(
                        onClick = {
                            viewModel.updateStatusSolicitacao(solicitacao, "ACEITO")
                            Toast.makeText(
                                context,
                                Constants.SolicitacaoStrings.MESSAGE_ACCEPTED_SUCESSO,
                                Toast.LENGTH_SHORT
                            ).show()
                        },
                        colors = ButtonDefaults.buttonColors(containerColor = Green),
                        modifier = Modifier.weight(1f)
                    ) {
                        Text(
                            Constants.SolicitacaoStrings.BUTTON_ACCEPTED,
                            color = White,
                            fontWeight = FontWeight.Bold
                        )
                    }

                    Button(
                        onClick = {
                            viewModel.updateStatusSolicitacao(solicitacao, "REJEITADO")
                            Toast.makeText(
                                context,
                                Constants.SolicitacaoStrings.MESSAGE_REJECTED_SUCESSO,
                                Toast.LENGTH_SHORT
                            ).show()
                        },
                        colors = ButtonDefaults.buttonColors(containerColor = Red),
                        modifier = Modifier.weight(1f)
                    ) {
                        Text(
                            Constants.SolicitacaoStrings.BUTTON_REJECTED,
                            color = White,
                            fontWeight = FontWeight.Bold
                        )
                    }
                }
            }
        }
    }
}
















