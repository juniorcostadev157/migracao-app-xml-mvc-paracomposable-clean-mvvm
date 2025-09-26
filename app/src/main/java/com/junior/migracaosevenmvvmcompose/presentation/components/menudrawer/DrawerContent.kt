package com.junior.migracaosevenmvvmcompose.presentation.components.menudrawer

import android.annotation.SuppressLint
import androidx.compose.animation.animateContentSize
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.DividerDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationDrawerItem
import androidx.compose.material3.NavigationDrawerItemDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import com.junior.migracaosevenmvvmcompose.R
import com.junior.migracaosevenmvvmcompose.data.model.UserSession // Import mantido para UserSession
import com.junior.migracaosevenmvvmcompose.presentation.theme.RedBlackGradient
import com.junior.migracaosevenmvvmcompose.presentation.theme.White

@SuppressLint("ConfigurationScreenWidthHeight")
@Composable
fun DrawerContent(
    userPrefs: UserSession?, // Recebe a sessão diretamente
    onMenuItemClick: (DrawerMenuItemType) -> Unit,
    onLogoutConfirmed: () -> Unit // Recebe a ação de logout (que agora é a navegação + limpeza de estado)
){

    var expandSolicitacoes by remember { mutableStateOf(false) }
    var expandMovimentacao by remember { mutableStateOf(false) }
    var expandHistorico by remember { mutableStateOf(false) }
    var showLogoutDialog by remember { mutableStateOf(false) }


    val configuration = LocalConfiguration.current
    val screenWidth = configuration.screenWidthDp.dp
    val drawerWidth = screenWidth * 0.7f

    Column(
        modifier = Modifier
            .fillMaxHeight()
            .width(drawerWidth)
            .background(RedBlackGradient)
            .verticalScroll(rememberScrollState())
            .padding(8.dp)
    ) {
        //header
        Column (
            modifier = Modifier.fillMaxWidth()
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ){

            val avatarSize = 100.dp
            Image(
                painter = painterResource(R.drawable.tec2),
                contentDescription = "Avatar",
                contentScale = ContentScale.Crop,
                modifier = Modifier.padding(16.dp)
                    .size(avatarSize)
                    .clip(CircleShape)
                    .border(2.dp, White, CircleShape)
            )
            Spacer(modifier = Modifier.height(8.dp))
            // Usando o valor passado
            Text(
                text = "${userPrefs?.login ?: "Carregando"} - ${userPrefs?.nome ?: "Usuário"}",
                color = White,
                textAlign = TextAlign.Center,
                modifier = Modifier.fillMaxWidth()
            )
        }
        Spacer(modifier = Modifier.height(8.dp))
        HorizontalDivider(Modifier, DividerDefaults.Thickness, color = White.copy(alpha = 0.5f))

        DrawerMenuItem(DrawerMenuItemType.Perfil, onMenuItemClick)

        DrawerSubMenu(
            label = "Solicitações",
            iconRes = R.drawable.ic_solicitacao_menu,
            expanded = expandSolicitacoes,
            onToggle = { expandSolicitacoes = !expandSolicitacoes },
            items = listOf(
                DrawerMenuItemType.SolicitacaoEquipamentos,
                DrawerMenuItemType.SolicitacaoFerramental,
                DrawerMenuItemType.SolicitacaoCheckin
            ),
            onMenuItemClick = onMenuItemClick
        )
        // Equipamentos e Ferramental (itens fixos)
        DrawerMenuItem(DrawerMenuItemType.Equipamento, onMenuItemClick)
        DrawerMenuItem(DrawerMenuItemType.Ferramental, onMenuItemClick)

        DrawerSubMenu(
            label = "Movimentações",
            iconRes = R.drawable.ic_movimentacao_menu,
            expanded = expandMovimentacao,
            onToggle = { expandMovimentacao= !expandMovimentacao},
            items = listOf(
                DrawerMenuItemType.Instalacao,
                DrawerMenuItemType.Troca,
                DrawerMenuItemType.Desconexao
            ),
            onMenuItemClick = onMenuItemClick
        )

        DrawerMenuItem(DrawerMenuItemType.Transferir, onMenuItemClick)
        DrawerMenuItem(DrawerMenuItemType.Devolucao, onMenuItemClick)
        DrawerMenuItem(DrawerMenuItemType.Clientes, onMenuItemClick)

        DrawerSubMenu(
            label = "Históricos",
            iconRes = R.drawable.ic_historico_menu,
            expanded = expandHistorico,
            onToggle = { expandHistorico = !expandHistorico },
            items = listOf(
                DrawerMenuItemType.HistoricoMovimentacoes,
                DrawerMenuItemType.HistoricoCheckin
            ),
            onMenuItemClick = onMenuItemClick
        )
        DrawerMenuItem(DrawerMenuItemType.Sair) {
            showLogoutDialog = true
        }
        if(showLogoutDialog){
            LogoutConfirmDialog(
                onConfirm = {
                    onLogoutConfirmed() // Chama a função que limpa o estado E navega
                    showLogoutDialog = false
                },
                onDismiss = {showLogoutDialog = false}
            )
        }
        Spacer(modifier = Modifier.navigationBarsPadding())
    }
}


@Composable
fun DrawerMenuItem(item: DrawerMenuItemType, onClick: (DrawerMenuItemType) -> Unit) {
    NavigationDrawerItem(
        label = { Text(item.title, color = Color.White) },
        selected = false,
        onClick = { onClick(item) },
        icon = item.icon?.let {
            { Icon(painterResource(it), contentDescription = item.title, tint = Color.White) }
        },
        colors = NavigationDrawerItemDefaults.colors(
            selectedContainerColor = Color.Transparent,
            unselectedContainerColor = Color.Transparent,
            selectedTextColor = Color.White,
            unselectedTextColor = Color.White,
            selectedIconColor = Color.White,
            unselectedIconColor = Color.White
        )
    )
}



@Composable
fun DrawerSubMenu(
    label: String,
    iconRes: Int,
    expanded: Boolean,
    onToggle: () -> Unit,
    items: List<DrawerMenuItemType>,
    onMenuItemClick: (DrawerMenuItemType) -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .animateContentSize(animationSpec = tween(300))
    ) {
        NavigationDrawerItem(
            label = { Text("$label ${if (expanded) "▾" else "▸"}", color = Color.White) },
            selected = expanded,
            onClick = onToggle,
            icon = { Icon(painterResource(iconRes), contentDescription = label, tint = Color.White) },
            colors = NavigationDrawerItemDefaults.colors(
                selectedContainerColor = Color.Transparent,
                unselectedContainerColor = Color.Transparent,
                selectedTextColor = Color.White,
                unselectedTextColor = Color.White,
                selectedIconColor = Color.White,
                unselectedIconColor = Color.White
            )
        )
        if (expanded) {
            Column(modifier = Modifier.padding(start = 16.dp)) {
                items.forEach { DrawerMenuItem(it, onMenuItemClick) }
            }
        }
    }
}

@Composable
fun LogoutConfirmDialog(
    onConfirm: () -> Unit,
    onDismiss: () -> Unit
) {


    Dialog(onDismissRequest = onDismiss) {
        Box(
            modifier = Modifier
                .fillMaxWidth(0.8f)
                .clip(RoundedCornerShape(16.dp))
                .background(Color.White) // fundo do container geral
                .border(2.dp, Color.White, shape = RoundedCornerShape(16.dp))
        ) {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                // Container do título e texto com gradiente
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(RedBlackGradient, shape = RoundedCornerShape(topStart = 16.dp, topEnd = 16.dp))
                        .padding(16.dp),
                    contentAlignment = Alignment.Center
                ) {
                    Column(horizontalAlignment = Alignment.CenterHorizontally) {
                        Text(
                            text = "Confirmação",
                            style = MaterialTheme.typography.titleLarge,
                            color = Color.White
                        )
                        Spacer(modifier = Modifier.height(8.dp))
                        Text(
                            text = "Deseja realmente sair?",
                            color = Color.White,
                            textAlign = TextAlign.Center
                        )
                    }
                }

                // Divisor branco
                HorizontalDivider(thickness = 1.dp, color = Color.White)

                // Botões
                Row(
                    horizontalArrangement = Arrangement.SpaceEvenly,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp)
                ) {
                    TextButton(
                        onClick = onConfirm,
                        colors = ButtonDefaults.textButtonColors(contentColor = Color.Red)
                    ) {
                        Text("Sim")
                    }
                    TextButton(
                        onClick = onDismiss,
                        colors = ButtonDefaults.textButtonColors(contentColor = Color.Red)
                    ) {
                        Text("Não")
                    }
                }
            }
        }
    }
}