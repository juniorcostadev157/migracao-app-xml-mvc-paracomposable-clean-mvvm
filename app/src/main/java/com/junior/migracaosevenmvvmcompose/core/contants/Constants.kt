package com.junior.migracaosevenmvvmcompose.core.contants

class Constants {

    object NavRoutes{
        const val LOGIN_SCREEN = "login"
        const val HOME_SCREEN = "home"
        const val LOGIN2FA_SCREEN = "login2fa"
        const val SOLICITACAO_EQUIPAMENTOS_SCREEN = "solicitacao_equipamentos"
        const val PERFIL_SCREEN = "perfil"
        const val SOLICITACAO_FERRAMENTAL_SCREEN = "solicitacao_ferramental"
        const val SOLICITACAO_CHECKIN_SCREEN = "solicitacao_checkin"
        const val EQUIPAMENTOS_SCREEN = "equipamentos"
        const val FERRAMENTAL_SCREEN = "ferramental"
        const val INSTALACAO_SCREEN = "instalacao"
        const val TRANSFERIR_SCREEN = "transferir"
        const val DESCONEXAO_SCREEN = "desconexao"
        const val TROCA_SCREEN = "troca"


    }

    object LoginStrings{

        //labels and buttons
        const val NAME_LOGO = "Seven Tecnico"
        const val EMAIL_LABEL = "E-mail"
        const val PASSWORD_LABEL = "Senha"
        const val LOGIN_BUTTON = "Entrar"
        const val CODE_LABEL = "Código"
        const val VERIFY_BUTTON = "Verificar"
        const val EMAIL_PLACEHOLDER = "Digite seu e-mail"

        //messages and errors
        const val INVALID_EMAIL_ERROR = "E-mail invalido"
        const val USER_NOT_FOUND_ERROR = "Usuário não encontrado"
        const val WRONG_PASSWORD_ERROR = "Senha ou email incorreto"
        const val NETWORK_ERROR = "Sem conexão com a internet"
        const val UNKNOWN_ERROR = "Erro desconhecido"
        const val EMPTY_FIELDS_ERROR = "Preencha todos os campos"
        const val EMPTY_CODE_ERROR = "Preencha o código"
        const val WRONG_CODE_ERROR = "Código incorreto"

    }

    object DataStore{

             const val PREFERENCES_NAME = "users"

    }

    object Permissions{
        const val MESSAGE_UPDATE_SUCCESS = "A atualização foi concluída ✅"
        const val MESSAGE_UPDATE_FAILED = "A atualização foi cancelada ❌"
        const val MESSAGE_PERMISSIONS = "Precisamos das permissões para continuar 🚨"

    }

    object FireBase{
        const val USERS_COLLECTION = "Usuarios"
        const val EQUIPAMENTOS_COLLECTION = "Equipamentos"
        const val SOLICITACOES_COLLECTION = "Solicitacoes"
        const val SOLICITACOES_FERRAMENTAL_COLLECTION = "SolicitacaoFerramental"
        const val SOLICITACOES_CHECKIN_COLLECTION = "SolicitacaoCheckin"
        const val FERRAMENTAL_TECNICO_COLLECTION = "FerramentalTecnico"

    }

    object SolicitacaoStrings{

        const val MESSAGE_ACCEPTED_SUCESSO = "Solicitação aceita com sucesso"
        const val MESSAGE_REJECTED_SUCESSO = "Solicitação rejeitada com sucesso"
        const val BUTTON_ACCEPTED = "Aceitar"
        const val BUTTON_REJECTED = "Rejeitar"
        const val MESSAGE_EMPTY_SOLICITACAO = "Sem solicitações no momento"

    }



}