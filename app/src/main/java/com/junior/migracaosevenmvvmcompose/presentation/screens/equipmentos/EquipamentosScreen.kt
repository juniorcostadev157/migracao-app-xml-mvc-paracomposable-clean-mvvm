package com.junior.migracaosevenmvvmcompose.presentation.screens.equipmentos


import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.junior.migracaosevenmvvmcompose.core.utils.DateUtils
import com.junior.migracaosevenmvvmcompose.core.utils.EquipamentoUtils
import com.junior.migracaosevenmvvmcompose.domain.model.Equipamento
import com.junior.migracaosevenmvvmcompose.presentation.components.AppTopBar
import com.junior.migracaosevenmvvmcompose.presentation.components.Badge
import com.junior.migracaosevenmvvmcompose.presentation.theme.Black
import com.junior.migracaosevenmvvmcompose.presentation.theme.FaintRed
import com.junior.migracaosevenmvvmcompose.presentation.theme.IceWhite
import com.junior.migracaosevenmvvmcompose.presentation.theme.Red
import com.junior.migracaosevenmvvmcompose.presentation.theme.White
import com.junior.migracaosevenmvvmcompose.presentation.theme.Yellow

@Composable
fun EquipamentosScreen(
    onMenuClick: () -> Unit,
    viewModel: EquipamentosViewModel = hiltViewModel()
) {

    LaunchedEffect(Unit) {
        viewModel.onSearchChange("")
        viewModel.onFilterChange("Todos")
    }


    val uiState by viewModel.uiState
    var expanded by remember { mutableStateOf(false) }

    Scaffold(
        containerColor = White,
        topBar = {
            AppTopBar(
                title = "Equipamentos",
                onMenuClick = onMenuClick,

                showSearch = true,
                onSearchQueryChange = { viewModel.onSearchChange(it) }
            )
        }
    ) { padding ->

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .padding(12.dp)
        ) {
            // 📊 Linha com quantidade + filtro
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "Quant. Equipamentos: ${uiState.data.size}" ,// isso virá do ViewModel
                    fontWeight = FontWeight.Bold,
                    fontSize = 16.sp
                )

                val filterOptions = remember(uiState.allData) {
                    listOf("Todos") + uiState.allData.map { it.estado }.distinct()
                }

                Box {
                    OutlinedButton(onClick = { expanded = true }) {
                        Text(uiState.selectedFilter, color = Black)
                    }

                    DropdownMenu(
                        expanded = expanded,
                        onDismissRequest = { expanded = false }
                    ) {
                        filterOptions.forEach { option ->
                            DropdownMenuItem(
                                text = { Text(option) },
                                onClick = {
                                    viewModel.onFilterChange(option)
                                    expanded = false
                                }
                            )
                        }
                    }
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            // 📋 Lista de equipamentos
            LazyColumn(
                modifier = Modifier.fillMaxSize(),
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                items(uiState.data) {equipamento-> // mock
                    EquipamentosItem(equipamento)
                }
            }
        }
    }
}


@Composable
fun EquipamentosItem(
    equipamentos: Equipamento,
) {
    // Calcula dinamicamente se é crítico ou desconectado
    val isCritico = EquipamentoUtils.isCritico(equipamentos)
    val isDesconectado = equipamentos.desconectado.equals("sim", ignoreCase = true)

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(4.dp),
        colors = CardDefaults.cardColors(
            containerColor = when {
                isCritico -> FaintRed // fundo vermelho
                isDesconectado -> White // fundo cinza claro
                else -> IceWhite
            }
        ),
        elevation = CardDefaults.cardElevation(defaultElevation = 6.dp),
        shape = MaterialTheme.shapes.medium
    ) {
        Column(modifier = Modifier.padding(12.dp)) {

            // 🔹 Header com Tipo + Badges
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "Tipo: ${equipamentos.tipo}",
                    color = Black,
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold
                )




            }

            Spacer(modifier = Modifier.height(8.dp))

            // 🔹 Detalhes do equipamento
            Column(verticalArrangement = Arrangement.spacedBy(4.dp)) {
                Text("Serial: ${equipamentos.numeroSerie}", fontWeight = FontWeight.Medium, fontSize = 15.sp)
                Text("Modelo: ${equipamentos.modelo}", fontWeight = FontWeight.Medium, fontSize = 15.sp)
                Text("Estado: ${equipamentos.estado}", fontWeight = FontWeight.Medium, fontSize = 15.sp)
                val dateFormatted = DateUtils.formatTimestamp(equipamentos.dataRecebimento)
                Text("Recebido em: $dateFormatted", fontWeight = FontWeight.Medium, fontSize = 15.sp)
                if (isCritico) Badge(text = "CRÍTICO ⚠️", background = Red)
                if (isDesconectado) Badge(text = "DESCONECTADO 🔌", background = Yellow)
            }
        }
    }
}




@Preview(showSystemUi = true, showBackground = true)
@Composable
fun PreviewEquipamentosScreen() {
    EquipamentosScreen(onMenuClick = {})
}
