package br.com.treinamento.atividadenavegacao

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.navigation.compose.rememberNavController
import br.com.treinamento.atividadenavegacao.model.TournamentState
import br.com.treinamento.atividadenavegacao.navigation.NavGraph

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun WorldCupApp() {

    val navController = rememberNavController()

    val tournamentState = remember {
        TournamentState()
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text("🏆 Copa do Mundo 2026")
                }
            )
        }
    ) { innerPadding ->

        NavGraph(
            navController = navController,
            tournamentState = tournamentState,
            modifier = Modifier.padding(innerPadding)
        )
    }
}