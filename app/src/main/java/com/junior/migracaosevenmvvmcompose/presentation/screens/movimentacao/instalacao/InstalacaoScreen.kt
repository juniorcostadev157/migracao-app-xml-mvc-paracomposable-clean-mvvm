package com.junior.migracaosevenmvvmcompose.presentation.screens.movimentacao.instalacao

import android.widget.Toast
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.junior.migracaosevenmvvmcompose.presentation.components.AppTopBar
import com.junior.migracaosevenmvvmcompose.presentation.theme.Red
import com.junior.migracaosevenmvvmcompose.presentation.theme.White

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun InstalacaoScreen(
    onMenuClick: () -> Unit = {},
    viewModel: InstalacaoViewModel = hiltViewModel()
) {
    val context = LocalContext.current
    val scope = rememberCoroutineScope()
    val uiState by viewModel.uiState.collectAsState()
    val selected by viewModel.selected.collectAsState()

    var tipoOs by remember { mutableStateOf("ESCOLHA O TIPO DE OS") }
    var expandedTipoOs by remember { mutableStateOf(false) }
    var qtdEquip by remember { mutableStateOf("") }
    var qtdGerada by remember  {mutableStateOf(0)}
    var contrato by remember { mutableStateOf("") }
    var showDialog by remember { mutableStateOf(false) }
    val grupos = remember(qtdGerada) {
        List(qtdGerada) { index -> "Grupo ${index + 1}" }
    }

    val tiposOs  = listOf("ESCOLHA O TIPO DE OS","INSTALAÇÃO", "INSTALAÇÃO MESH", "INSTALAÇÃO PONTO ADICIONAL" )



    Scaffold(
        containerColor = White,
        topBar = { AppTopBar(title = "Instalar Equipamento", onMenuClick = onMenuClick) }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .padding(innerPadding)
                .padding(16.dp)
                .fillMaxSize(),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            // Tipo de OS
            Text("Tipo de OS", fontWeight = FontWeight.SemiBold)
            ExposedDropdownMenuBox(
                expanded = expandedTipoOs,
                onExpandedChange = { expandedTipoOs = !expandedTipoOs }
            ) {
                OutlinedTextField(
                    value = tipoOs,
                    onValueChange = {},
                    readOnly = true,
                    label = { Text("Selecione") },
                    modifier = Modifier
                        .menuAnchor(MenuAnchorType.PrimaryEditable, true)
                        .fillMaxWidth(),
                    trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = expandedTipoOs) }
                )
                ExposedDropdownMenu(
                    expanded = expandedTipoOs,
                    onDismissRequest = { expandedTipoOs = false }
                ) {
                    tiposOs.forEach { item ->
                        DropdownMenuItem(
                            text = { Text(item) },
                            onClick = {
                                tipoOs = item
                                expandedTipoOs = false
                            }
                        )
                    }
                }
            }

            // Quantidade
            OutlinedTextField(
                value = qtdEquip,
                onValueChange = { qtdEquip = it.filter { c -> c.isDigit() } },
                label = { Text("Quantidade de equipamentos") },
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                modifier = Modifier.fillMaxWidth()
            )

            // Botão Gerar
            Button(
                onClick = {
                    val qtd = qtdEquip.toIntOrNull() ?: 0
                    when {
                        tipoOs == "ESCOLHA O TIPO DE OS" ->
                            Toast.makeText(context, "Escolha o tipo de OS primeiro ⚠️", Toast.LENGTH_SHORT).show()
                        qtd <= 0 ->
                            Toast.makeText(context, "Informe uma quantidade válida ⚠️", Toast.LENGTH_SHORT).show()
                        qtd > 7 ->
                            Toast.makeText(context, "Máximo de 7 equipamentos permitidos 🚨", Toast.LENGTH_SHORT).show()
                        else -> {
                            qtdGerada = qtd
                            Toast.makeText(context, "Gerados $qtd equipamentos 🔧", Toast.LENGTH_SHORT).show()
                        }
                    }
                },
                colors = ButtonDefaults.buttonColors(Red),
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(
                    text="Gerar",
                    color = White,
                    fontWeight = FontWeight.Bold)
            }

            // Grupos dinâmicos
            grupos.forEachIndexed { index, _ ->
                Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                    Text("Equipamento ${index + 1}", fontWeight = FontWeight.Bold)

                    // Tipo
                    var expandedTipo by remember { mutableStateOf(false) }
                    val tipoSelecionado =selected[index]?.first ?: "Selecione tipo"

                    ExposedDropdownMenuBox(
                        expanded = expandedTipo,
                        onExpandedChange = { expandedTipo = !expandedTipo }
                    ) {
                        OutlinedTextField(
                            value = tipoSelecionado,
                            onValueChange = {},
                            readOnly = true,
                            label = { Text("Tipo") },
                            modifier = Modifier
                                .menuAnchor(MenuAnchorType.PrimaryEditable, true)
                                .fillMaxWidth(),
                            trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = expandedTipo) }
                        )
                        ExposedDropdownMenu(
                            expanded = expandedTipo,
                            onDismissRequest = { expandedTipo = false }
                        ) {
                            uiState.data.map{it.tipo }.distinct().forEach {tipo ->
                                DropdownMenuItem(
                                    text = { Text(tipo!!) },
                                    onClick = {
                                        viewModel.updateSelection(index, tipo!!, "")
                                        expandedTipo = false
                                    }
                                )
                            }
                        }
                    }

                    // Serial
                    var expandedSerial by remember { mutableStateOf(false) }
                    val serialsDisponiveis = viewModel.getSerialsAvailable(tipoSelecionado, index)
                    val serialSelecionado = selected[index]?.second ?: "Selecione o Serial"

                    ExposedDropdownMenuBox(
                        expanded = expandedSerial,
                        onExpandedChange = { expandedSerial = !expandedSerial }
                    ) {
                        OutlinedTextField(
                            value = serialSelecionado,
                            onValueChange = {},
                            readOnly = true,
                            label = { Text("Número de Série") },
                            modifier = Modifier
                                .menuAnchor(MenuAnchorType.PrimaryEditable, true)
                                .fillMaxWidth(),
                            trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = expandedSerial) }
                        )
                        ExposedDropdownMenu(
                            expanded = expandedSerial,
                            onDismissRequest = { expandedSerial = false }
                        ) {
                            serialsDisponiveis.forEach { serial ->
                                DropdownMenuItem(
                                    text = { Text(serial) },
                                    onClick = {
                                       viewModel.updateSelection(index, tipoSelecionado, serial)
                                        expandedSerial = false
                                    }
                                )
                            }
                        }
                    }
                }
                Spacer(Modifier.height(12.dp))
            }

            // Contrato
            OutlinedTextField(
                value = contrato,
                onValueChange = { contrato = it.filter { c -> c.isDigit() } },
                label = { Text("Contrato") },
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(Modifier.height(24.dp))

            // Botão instalar
            Button(
                modifier = Modifier.fillMaxWidth(),
                colors = ButtonDefaults.buttonColors(Red),
                onClick = {
                    val duplicados = selected.values.map { it.second }.groupBy { it }.filter { it.value.size > 1 }
                    when {
                        tipoOs == "ESCOLHA O TIPO DE OS" ->
                            Toast.makeText(context, "Escolha um tipo de OS ⚠️", Toast.LENGTH_SHORT).show()
                        contrato.isEmpty() ->
                            Toast.makeText(context, "Contrato inválido ❌", Toast.LENGTH_SHORT).show()
                        grupos.isEmpty() ->
                            Toast.makeText(context, "Informe quantidade de equipamentos ⚠️", Toast.LENGTH_SHORT).show()
                        selected.size != grupos.size || selected.values.any { it.first.isEmpty() || it.second.isEmpty() } ->
                            Toast.makeText(context, "Preencha todos os campos ⚠️", Toast.LENGTH_SHORT).show()
                        duplicados.isNotEmpty() ->
                            Toast.makeText(context, "Número de série duplicado ❌", Toast.LENGTH_SHORT).show()
                        else -> showDialog = true
                    }
                }
            ) {
                Text("Instalar")
            }
        }

        // 🔹 Dialog de confirmação
        if (showDialog) {
            AlertDialog(
                onDismissRequest = { showDialog = false },
                title = { Text("Confirmar Instalação") },
                text = {
                    Column {
                        selected.forEach { (idx, par) ->
                            Text("Equip ${idx + 1}: Tipo=${par.first}, Série=${par.second}")
                        }
                    }
                },
                confirmButton = {
                    TextButton(onClick = {
                        showDialog = false
                        Toast.makeText(context, "Instalação feita com sucesso ✅", Toast.LENGTH_SHORT).show()
                    }) {
                        Text("Confirmar")
                    }
                },
                dismissButton = {
                    TextButton(onClick = { showDialog = false }) {
                        Text("Cancelar")
                    }
                }
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewInstalacaoFake() {
    InstalacaoScreen()
}
