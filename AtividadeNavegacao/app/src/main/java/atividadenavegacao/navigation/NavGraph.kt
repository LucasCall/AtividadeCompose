package br.com.treinamento.atividadenavegacao.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavType
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import br.com.treinamento.atividadenavegacao.model.TournamentState
import br.com.treinamento.atividadenavegacao.screens.FinalScreen
import br.com.treinamento.atividadenavegacao.screens.HomeScreen
import br.com.treinamento.atividadenavegacao.screens.MatchDetailScreen
import br.com.treinamento.atividadenavegacao.screens.StageScreen

@Composable
fun NavGraph(
    navController: NavHostController,
    tournamentState: TournamentState,
    modifier: Modifier = Modifier
) {

    NavHost(
        navController = navController,
        startDestination = Rotas.HOME,
        modifier = modifier
    ) {

        composable(Rotas.HOME) {
            HomeScreen(
                navController = navController,
                tournamentState = tournamentState
            )
        }

        composable(
            route = Rotas.STAGE
        ) { backStackEntry ->

            val stage =
                backStackEntry.arguments?.getString("stage") ?: ""

            StageScreen(
                navController = navController,
                stage = stage,
                tournamentState = tournamentState
            )
        }

        composable(
            route = Rotas.MATCH_DETAIL,
            arguments = listOf(
                navArgument("stage") {
                    type = NavType.StringType
                },
                navArgument("matchId") {
                    type = NavType.IntType
                }
            )
        ) { backStackEntry ->

            val stage =
                backStackEntry.arguments?.getString("stage") ?: ""

            val matchId =
                backStackEntry.arguments?.getInt("matchId") ?: 0

            MatchDetailScreen(
                navController = navController,
                stage = stage,
                matchId = matchId,
                tournamentState = tournamentState
            )
        }

        composable(Rotas.FINAL) {
            FinalScreen(
                navController = navController,
                tournamentState = tournamentState
            )
        }
    }
}