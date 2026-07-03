package br.com.treinamento.atividadenavegacao.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import br.com.treinamento.atividadenavegacao.data.FakeData
import br.com.treinamento.atividadenavegacao.model.Stage
import br.com.treinamento.atividadenavegacao.model.TournamentState
import br.com.treinamento.atividadenavegacao.navigation.Rotas
import br.com.treinamento.atividadenavegacao.utils.getFlag

@Composable
fun MatchDetailScreen(
    navController: NavHostController,
    stage: String,
    matchId: Int,
    tournamentState: TournamentState
) {

    val currentStage = try {
        Stage.valueOf(stage)
    } catch (e: Exception) {
        Stage.ROUND_16
    }

    val match = FakeData.matchesForStage(currentStage, tournamentState).firstOrNull {
        it.id == matchId
    }

    if (match == null) {
        Text("Partida não encontrada")
        return
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {

        Text(
            text = "${getFlag(match.teamA)} ${match.teamA} x ${getFlag(match.teamB)} ${match.teamB}",
            style = MaterialTheme.typography.headlineMedium
        )

        Text(
            text = "${match.date} às ${match.time}"
        )

        Text(
            text = match.stadium
        )

        Button(
            modifier = Modifier.fillMaxWidth(),
            onClick = {
                tournamentState.saveWinner(currentStage, match.id, match.teamA)

                if (currentStage == Stage.FINAL) {
                    navController.navigate(Rotas.FINAL)
                } else {
                    navController.popBackStack()
                }
            }
        ) {
            Text(match.teamA)
        }

        Button(
            modifier = Modifier.fillMaxWidth(),
            onClick = {
                tournamentState.saveWinner(currentStage, match.id, match.teamB)

                if (currentStage == Stage.FINAL) {
                    navController.navigate(Rotas.FINAL)
                } else {
                    navController.popBackStack()
                }
            }
        ) {
            Text(match.teamB)
        }
    }
}