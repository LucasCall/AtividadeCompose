package br.com.treinamento.atividadenavegacao.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import br.com.treinamento.atividadenavegacao.components.WinnerCard
import br.com.treinamento.atividadenavegacao.model.TournamentState
import br.com.treinamento.atividadenavegacao.navigation.Rotas

@Composable
fun FinalScreen(
    navController: NavHostController,
    tournamentState: TournamentState
) {

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(20.dp)
    ) {

        Text(
            text = "Resultado da Copa"
        )

        WinnerCard(
            champion = tournamentState.champion ?: "Campeão não definido"
        )

        Button(
            onClick = {
                navController.navigate(Rotas.HOME)
            }
        ) {
            Text("Voltar ao início")
        }
    }
}