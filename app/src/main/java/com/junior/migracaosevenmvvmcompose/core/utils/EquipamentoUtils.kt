    package com.junior.migracaosevenmvvmcompose.core.utils


    import com.junior.migracaosevenmvvmcompose.domain.model.Equipamento

    object EquipamentoUtils {

        fun isCritico(equip: Equipamento): Boolean{
            val today  = System.currentTimeMillis()
            val dateRecebimentoMillis = equip.dataRecebimento.toDate().time
            val  daysPassed = (today - dateRecebimentoMillis) / (1000 * 60 * 60 * 24)
            return (equip.estado == "INICIALIZADO" && daysPassed >= 30) ||
                    (equip.estado == "SUSPEITO" && daysPassed > 7)
        }

    }