package br.com.treinamento.atividademvvm.view.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import br.com.treinamento.atividademvvm.view.screens.FormularioNotasScreen
import br.com.treinamento.atividademvvm.view.screens.ResultadoNotasScreen
import br.com.treinamento.atividademvvm.viewmodel.GerenciadorViewModel

@Composable
fun AppNavigation(
	viewModel: GerenciadorViewModel,
	modifier: Modifier = Modifier
) {
	val navController = rememberNavController()
	val uiState by viewModel.uiState.collectAsState()

	NavHost(
		navController = navController,
		startDestination = Destinos.FORMULARIO,
		modifier = modifier
	) {
		composable(Destinos.FORMULARIO) {
			FormularioNotasScreen(
				uiState = uiState,
				onNomeAlunoChange = viewModel::atualizarNomeAluno,
				onPrimeiraNotaChange = viewModel::atualizarPrimeiraNota,
				onSegundaNotaChange = viewModel::atualizarSegundaNota,
				onCadastrarAlunoClick = {
					viewModel.cadastrarAluno()
				},
				onVerResumoClick = {
					if (viewModel.podeAbrirResumo()) {
						navController.navigate(Destinos.RESULTADO)
					}
				},
				onLimparFormularioClick = viewModel::limparFormulario
			)
		}

		composable(Destinos.RESULTADO) {
			ResultadoNotasScreen(
				uiState = uiState,
				onVoltarCadastroClick = {
					navController.popBackStack()
				},
				onRemoverAlunoClick = viewModel::removerAluno
			)
		}
	}
}