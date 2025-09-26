package com.junior.migracaosevenmvvmcompose.presentation.screens.ferramental

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.junior.migracaosevenmvvmcompose.domain.model.FerramentalTecnico
import com.junior.migracaosevenmvvmcompose.presentation.components.AppTopBar
import com.junior.migracaosevenmvvmcompose.presentation.components.Badge
import com.junior.migracaosevenmvvmcompose.presentation.theme.Black
import com.junior.migracaosevenmvvmcompose.presentation.theme.DarkGrey
import com.junior.migracaosevenmvvmcompose.presentation.theme.Green
import com.junior.migracaosevenmvvmcompose.presentation.theme.IceWhite
import com.junior.migracaosevenmvvmcompose.presentation.theme.Red
import com.junior.migracaosevenmvvmcompose.presentation.theme.White
import com.junior.migracaosevenmvvmcompose.presentation.theme.Yellow

@Composable
fun FerramentalTecnicoScreen(
    onMenuClick: () -> Unit,
    viewModel: FerramentalTecnicoViewModel = hiltViewModel()
){
    val uiState by  viewModel.uiState.collectAsState()
    var expanded by remember { mutableStateOf(false) }

    LaunchedEffect(Unit){
        viewModel.onSearchChange("")
        viewModel.onFilterChange("Todos")
    }

    Scaffold(
        containerColor = White,
        topBar = {
            AppTopBar(
                title = "Ferramental",
                onMenuClick = onMenuClick,
                showSearch = true,
                onSearchQueryChange = {viewModel.onSearchChange(it)}

            )
        }
    ) {paddingValues ->

        Column(
            modifier = Modifier.fillMaxSize().padding(paddingValues).padding(12.dp)

        ) {
            Row (
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ){

                Text(
                    text = "Quant.Ferramentas: ${uiState.data.size}",
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold
                )

                val filterOptions = remember (uiState.allData){
                    listOf("Todos") + uiState.allData.map { it.statusValidade }.distinct()
                }

                Box {
                    OutlinedButton(
                        onClick = { expanded = true }
                    ) {
                        Text(
                            text = uiState.selectedFilter,
                            color = Black
                        )
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
            Spacer(modifier = Modifier.padding(16.dp))
            LazyColumn(
                modifier = Modifier.fillMaxSize(),
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                items(uiState.data){ferramental->
                    FerramentalItem(ferramenta = ferramental)
                }
            }
        }
    }

}
@Composable
fun FerramentalItem(ferramenta: FerramentalTecnico) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(4.dp),
        colors = CardDefaults.cardColors(
            containerColor = IceWhite
        ),
        elevation = CardDefaults.cardElevation(defaultElevation = 6.dp),
        shape = MaterialTheme.shapes.medium
    ) {
        Column(modifier = Modifier.padding(12.dp)) {

            // Nome da ferramenta
            Text(
                text = "🔧 ${ferramenta.nomeFerramenta}",
                color = Black,
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold
            )

            Spacer(modifier = Modifier.height(8.dp))

            // Detalhes básicos
            Column(verticalArrangement = Arrangement.spacedBy(4.dp)) {
                Text("💲 Valor: ${ferramenta.valor}", fontWeight = FontWeight.Medium, fontSize = 15.sp)
                Text("📆 Validade: ${ferramenta.validade}", fontWeight = FontWeight.Medium, fontSize = 15.sp)
                Text("📅 Data Validade: ${ferramenta.dataValidade}", fontWeight = FontWeight.Medium, fontSize = 15.sp)
                Text("📥 Recebido em: ${ferramenta.dataRecebimento}", fontWeight = FontWeight.Medium, fontSize = 15.sp)
                Text("🔄 Último Check In: ${ferramenta.dataUltimoCheckin}", fontWeight = FontWeight.Medium, fontSize = 15.sp)
            }

            Spacer(modifier = Modifier.height(8.dp))

            // Badges de Status
            Column(verticalArrangement = Arrangement.spacedBy(6.dp)) {
                // Status Check-in
                when (ferramenta.statusCheckIn) {
                    "PERDA" -> Badge("Status Check In: ❌ Perdido", Red)
                    "COM O TECNICO" -> Badge("Status Check In: ✅ Com Técnico", Green)
                    else -> Badge("ℹ ${ferramenta.statusCheckIn}", DarkGrey)
                }

                // Status Validade
                when (ferramenta.statusValidade) {
                    "NO PRAZO" -> Badge("Status Validade: ⏱ No Prazo", Green)
                    "VENCIDO" -> Badge("Status Validade: ⚠️ Vencido", Red)
                    "VENCENDO" -> Badge("Status Validade: ⌛ Vencendo", Yellow)
                    else -> Badge("ℹ ${ferramenta.statusValidade}", DarkGrey)
                }
            }
        }
    }
}
