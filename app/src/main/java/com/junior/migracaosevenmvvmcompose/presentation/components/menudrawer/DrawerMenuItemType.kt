package com.junior.migracaosevenmvvmcompose.presentation.components.menudrawer

import com.junior.migracaosevenmvvmcompose.R

sealed class DrawerMenuItemType(val title: String, val icon: Int?) {
    object Perfil : DrawerMenuItemType("Perfil", R.drawable.ic_perfil_menu)
    object SolicitacaoEquipamentos : DrawerMenuItemType("Solicitação Equipamentos", null)
    object SolicitacaoFerramental : DrawerMenuItemType("Solicitação Ferramental", null)
    object SolicitacaoCheckin : DrawerMenuItemType("Solicitação Check-in", null)
    object Equipamento : DrawerMenuItemType("Equipamentos", R.drawable.ic_equipamentos_menu)
    object Ferramental : DrawerMenuItemType("Ferramental", R.drawable.ic_ferramental)
    object Movimentacao : DrawerMenuItemType("Movimentações", R.drawable.ic_movimentacao_menu)
    object Instalacao : DrawerMenuItemType("Instalação Equipamento", null)
    object Troca : DrawerMenuItemType("Troca Equipamento", null)
    object Desconexao : DrawerMenuItemType("Desc. Equipamento", null)
    object Transferir : DrawerMenuItemType("Transferência", R.drawable.ic_troca_tecnico)
    object Devolucao : DrawerMenuItemType("Devolução", R.drawable.ic_devolucao)
    object Clientes : DrawerMenuItemType("Clientes", R.drawable.ic_clientes)
    object HistoricoMovimentacoes : DrawerMenuItemType("Histórico Movimentação", null)
    object HistoricoCheckin : DrawerMenuItemType("Histórico Check-in", null)
    object Sair : DrawerMenuItemType("Sair", R.drawable.ic_sair_menu)
}