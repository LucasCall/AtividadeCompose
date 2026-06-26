package br.com.treinamento.atividadecompose.ui.screens

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AttachMoney
import androidx.compose.material.icons.filled.Build
import androidx.compose.material.icons.filled.ManageAccounts
import androidx.compose.material.icons.filled.SupervisorAccount
import androidx.compose.ui.graphics.vector.ImageVector
import br.com.treinamento.atividadecompose.data.Nivel

fun nivelIcon(nivel: Nivel): ImageVector = when (nivel) {
    Nivel.ADMINISTRATIVO -> Icons.Default.ManageAccounts
    Nivel.FINANCEIRO -> Icons.Default.AttachMoney
    Nivel.GERENCIA -> Icons.Default.SupervisorAccount
    Nivel.SUPORTE -> Icons.Default.Build
}

fun nivelLabel(nivel: Nivel): String = when (nivel) {
    Nivel.ADMINISTRATIVO -> "Administrativo"
    Nivel.FINANCEIRO -> "Financeiro"
    Nivel.GERENCIA -> "Gerência"
    Nivel.SUPORTE -> "Suporte"
}
