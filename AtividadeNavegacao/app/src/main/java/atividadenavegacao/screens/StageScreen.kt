package br.com.treinamento.atividadenavegacao.screens

import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import br.com.treinamento.atividadenavegacao.components.MatchCard
import br.com.treinamento.atividadenavegacao.data.FakeData
import br.com.treinamento.atividadenavegacao.model.Stage
import br.com.treinamento.atividadenavegacao.model.TournamentState
import br.com.treinamento.atividadenavegacao.navigation.Rotas

@Composable
fun StageScreen(
    navController: NavHostController,
    stage: String,
    tournamentState: TournamentState
) {

    val currentStage = try {
        Stage.valueOf(stage)
    } catch (e: Exception) {
        Stage.ROUND_16
    }

    val matches = FakeData.matchesForStage(
        stage = currentStage,
        tournamentState = tournamentState
    )

    val allStages = remember {
        listOf(
            Stage.ROUND_16,
            Stage.QUARTER_FINAL,
            Stage.SEMI_FINAL,
            Stage.FINAL
        )
    }

    val nextStage = when (currentStage) {
        Stage.ROUND_16 -> Stage.QUARTER_FINAL
        Stage.QUARTER_FINAL -> Stage.SEMI_FINAL
        Stage.SEMI_FINAL -> Stage.FINAL
        Stage.FINAL -> null
    }

    val completedMatches = tournamentState.winnersFor(currentStage).size
    val stageWinners = tournamentState.winnersFor(currentStage)
    val canAdvance = tournamentState.isStageComplete(currentStage, matches.size)
    val stageScroll = rememberScrollState()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {

        Text(
            text = currentStage.title
        )

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .horizontalScroll(stageScroll),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            allStages.forEach { targetStage ->
                val hasAccess = targetStage == Stage.ROUND_16 ||
                    FakeData.matchesForStage(targetStage, tournamentState).isNotEmpty()
                val isCurrentStage = targetStage == currentStage
                val label = when (targetStage) {
                    Stage.ROUND_16 -> "Oitavas"
                    Stage.QUARTER_FINAL -> "Quartas"
                    Stage.SEMI_FINAL -> "Semi"
                    Stage.FINAL -> "Final"
                }

                if (isCurrentStage) {
                    Button(
                        enabled = false,
                        onClick = {}
                    ) {
                        Text(label)
                    }
                } else {
                    OutlinedButton(
                        enabled = hasAccess,
                        onClick = {
                            navController.navigate(Rotas.stageRoute(targetStage.name)) {
                                launchSingleTop = true
                            }
                        }
                    ) {
                        Text(label)
                    }
                }
            }
        }

        if (matches.isNotEmpty()) {
            Text(
                text = "Partidas escolhidas: $completedMatches/${matches.size}",
                style = MaterialTheme.typography.bodyMedium
            )
        }

        if (matches.isEmpty()) {
            Text(
                text = "Conclua a etapa anterior para montar esta fase."
            )
        } else {
            LazyColumn(
                modifier = Modifier.weight(1f),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {

                items(matches, key = { match -> match.id }) { match ->

                    MatchCard(
                        match = match,
                        selectedWinner = stageWinners[match.id],
                        onClick = {
                            navController.navigate(
                                Rotas.matchDetailRoute(currentStage.name, match.id)
                            )
                        }
                    )
                }
            }
        }

        val nextLabel = if (nextStage == null) {
            "Ver campeão"
        } else {
            "Avançar para ${nextStage.title}"
        }

        Column(
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            if (matches.isNotEmpty() && !canAdvance) {
                Text(
                    text = "Escolha os vencedores desta fase para liberar a próxima etapa.",
                    style = MaterialTheme.typography.bodySmall
                )
            }

            Button(
                modifier = Modifier.fillMaxWidth(),
                enabled = matches.isNotEmpty() && canAdvance,
                onClick = {
                    if (nextStage == null) {
                        navController.navigate(Rotas.FINAL)
                    } else {
                        navController.navigate(Rotas.stageRoute(nextStage.name))
                    }
                }
            ) {
                Text(nextLabel)
            }
        }
    }
}