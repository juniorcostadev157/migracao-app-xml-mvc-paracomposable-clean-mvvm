package com.junior.migracaosevenmvvmcompose.presentation.screens.solicitacao.ferramental

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
import com.junior.migracaosevenmvvmcompose.domain.model.SolicitacaoFerramental
import com.junior.migracaosevenmvvmcompose.presentation.components.AppTopBar
import com.junior.migracaosevenmvvmcompose.presentation.theme.Black
import com.junior.migracaosevenmvvmcompose.presentation.theme.Green
import com.junior.migracaosevenmvvmcompose.presentation.theme.IceWhite
import com.junior.migracaosevenmvvmcompose.presentation.theme.Red
import com.junior.migracaosevenmvvmcompose.presentation.theme.White
import com.junior.migracaosevenmvvmcompose.presentation.theme.Yellow

@Composable
fun SolicitacaoFerramentalScreen(
    onMenuClick: ()-> Unit,
    viewModel: SolicitacaoFerramentalViewModel = hiltViewModel()
) {


    val uiState by viewModel.uiState.collectAsState()



    Scaffold(
        containerColor = White,
        topBar = { AppTopBar(title = "Solicitação - Ferramental", onMenuClick = onMenuClick) }
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

        }else if(uiState.data.isEmpty()){
            // Se a lista estiver vazia após o carregamento
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
        }else {
            // Exibe a lista se houver dados
            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(padding),
                contentPadding = PaddingValues(12.dp),
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                items(uiState.data) { solicitacao ->
                    SolicitacaoCardFerramental(solicitacao, viewModel)
                }
            }
        }
        }



    }


@Composable
fun SolicitacaoCardFerramental(solicitacao: SolicitacaoFerramental,
                               viewModel: SolicitacaoFerramentalViewModel = hiltViewModel()) {

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
                    text = "Solicitação Ferramental",
                    color = Color.White,
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold
                )
            }

            Spacer(modifier = Modifier.height(8.dp))


            Text("Almoxarife: ${solicitacao.nomeAlmox ?: "Desconhecido"}", fontWeight = FontWeight.Medium)
            Text("Login Técnico: ${solicitacao.loginTecnico ?: "Desconhecido"}", fontWeight = FontWeight.Medium)
            Text("Quant. Ferramentas: ${solicitacao.ferramental?.size}", fontWeight = FontWeight.Medium)

            Spacer(modifier = Modifier.height(6.dp))

            // Lista de equipamentos
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(Color(0xFFF5F5F5))
                    .padding(8.dp)
            ) {
                Text("Equipamentos:", fontWeight = FontWeight.Bold, fontSize = 16.sp, modifier = Modifier.align (Alignment.CenterHorizontally))
                Spacer(modifier = Modifier.height(4.dp))

                solicitacao.ferramental?.forEach { ferramental ->
                    Text(
                        text = "${ferramental?.nomeFerramenta}",
                        fontSize = 14.sp,
                        fontWeight = FontWeight.Bold,
                        modifier = Modifier.align(Alignment.CenterHorizontally)
                    )

                    Text(
                        text = "${ferramental?.valor} - Validade: ${ferramental?.validade} - Data Validade: ${ferramental?.dataValidade}",
                        fontSize = 14.sp,
                        modifier = Modifier.align(Alignment.CenterHorizontally)
                    )
                    Spacer(modifier = Modifier.height(4.dp))
                }
            }

            Spacer(modifier = Modifier.height(6.dp))

            // Data formatada
            val dataFormatada = DateUtils.formatTimestamp(solicitacao.dataEnvio)

            Text("Data: $dataFormatada", fontWeight = FontWeight.Medium)


            Text("Status: ${solicitacao.status ?: "Pendente"}", color = Yellow, fontWeight = FontWeight.Bold)

            Spacer(modifier = Modifier.height(12.dp))

            // Botões
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                Button(
                    onClick = {

                        viewModel.updateSolicitacaoFerramental(solicitacao, "ACEITO")
                        Toast.makeText(context, Constants.SolicitacaoStrings.MESSAGE_ACCEPTED_SUCESSO, Toast.LENGTH_SHORT).show()},
                    colors = ButtonDefaults.buttonColors(containerColor = Green),
                    modifier = Modifier.weight(1f)
                ) {
                    Text(Constants.SolicitacaoStrings.BUTTON_ACCEPTED, color = White, fontWeight = FontWeight.Bold)
                }

                Button(
                    onClick = {
                        viewModel.updateSolicitacaoFerramental(solicitacao, "REJEITADO")
                        Toast.makeText(context, Constants.SolicitacaoStrings.MESSAGE_REJECTED_SUCESSO, Toast.LENGTH_SHORT).show()},
                    colors = ButtonDefaults.buttonColors(containerColor = Red),
                    modifier = Modifier.weight(1f)
                ) {
                    Text(Constants.SolicitacaoStrings.BUTTON_REJECTED, color = White, fontWeight = FontWeight.Bold)
                }
            }
        }
    }
}



