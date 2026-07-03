package br.com.treinamento.atividadenavegacao.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import br.com.treinamento.atividadenavegacao.model.Stage
import br.com.treinamento.atividadenavegacao.model.TournamentState
import br.com.treinamento.atividadenavegacao.navigation.Rotas

@Composable
fun HomeScreen(
    navController: NavHostController,
    tournamentState: TournamentState
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {

        Text(
            text = "🏆 Copa do Mundo 2026",
            style = MaterialTheme.typography.headlineMedium
        )

        Text(
            text = "Monte sua chave e escolha o campeão da competição.",
            modifier = Modifier.padding(vertical = 16.dp)
        )

        Button(
            onClick = {
                tournamentState.reset()

                navController.navigate(
                    Rotas.stageRoute(Stage.ROUND_16.name)
                )
            }
        ) {
            Text("Iniciar Simulação")
        }
    }
}