package br.com.treinamento.atividadenavegacao.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import br.com.treinamento.atividadenavegacao.model.Match
import br.com.treinamento.atividadenavegacao.utils.getFlag

@Composable
fun MatchCard(
    match: Match,
    selectedWinner: String?,
    onClick: () -> Unit
) {

    val isResultSelected = selectedWinner != null

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp)
            .clickable { onClick() },
        border = if (isResultSelected) {
            BorderStroke(2.dp, MaterialTheme.colorScheme.primary)
        } else {
            null
        },
        colors = CardDefaults.cardColors(
            containerColor = if (isResultSelected) {
                MaterialTheme.colorScheme.primaryContainer
            } else {
                MaterialTheme.colorScheme.surface
            }
        ),
        elevation = CardDefaults.cardElevation(
            defaultElevation = 4.dp
        )
    ) {

        Column(
            modifier = Modifier.padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {

            Text(
                text = "${getFlag(match.teamA)} ${match.teamA} x ${getFlag(match.teamB)} ${match.teamB}",
                style = MaterialTheme.typography.titleMedium
            )

            Text(
                text = "${match.date} às ${match.time}",
                style = MaterialTheme.typography.bodyMedium
            )

            Text(
                text = match.stadium,
                style = MaterialTheme.typography.bodySmall
            )

            Text(
                text = if (isResultSelected) {
                    "Vencedor escolhido: ${selectedWinner}"
                } else {
                    "Resultado ainda não escolhido"
                },
                style = MaterialTheme.typography.labelMedium,
                color = if (isResultSelected) {
                    MaterialTheme.colorScheme.primary
                } else {
                    MaterialTheme.colorScheme.onSurfaceVariant
                }
            )
        }
    }
}