package br.com.treinamento.atividademvvm.view.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import br.com.treinamento.atividademvvm.model.NotasUiState

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FormularioNotasScreen(
	uiState: NotasUiState,
	onNomeAlunoChange: (String) -> Unit,
	onPrimeiraNotaChange: (String) -> Unit,
	onSegundaNotaChange: (String) -> Unit,
	onCadastrarAlunoClick: () -> Unit,
	onVerResumoClick: () -> Unit,
	onLimparFormularioClick: () -> Unit
) {
	Scaffold(
		topBar = {
			TopAppBar(
				title = {
					Text(text = "Cadastro de alunos")
				}
			)
		}
	) { innerPadding ->
		Column(
			modifier = Modifier
				.fillMaxSize()
				.padding(innerPadding)
				.padding(16.dp)
				.verticalScroll(rememberScrollState()),
			verticalArrangement = Arrangement.spacedBy(12.dp)
		) {
			Text(
				text = "Informe o nome do aluno e as duas notas para adicionar na lista.",
				style = MaterialTheme.typography.bodyLarge
			)

			Card(modifier = Modifier.fillMaxWidth()) {
				Text(
					text = "Alunos cadastrados: ${uiState.alunos.size}",
					modifier = Modifier.padding(16.dp),
					style = MaterialTheme.typography.titleMedium
				)
			}

			OutlinedTextField(
				value = uiState.nomeAluno,
				onValueChange = onNomeAlunoChange,
				label = { Text("Nome do aluno") },
				modifier = Modifier.fillMaxWidth(),
				singleLine = true
			)

			CampoNota(
				valor = uiState.primeiraNota,
				rotulo = "Primeira nota",
				onValorChange = onPrimeiraNotaChange
			)

			CampoNota(
				valor = uiState.segundaNota,
				rotulo = "Segunda nota",
				onValorChange = onSegundaNotaChange
			)

			if (uiState.mensagemErro != null) {
				Card(modifier = Modifier.fillMaxWidth()) {
					Text(
						text = uiState.mensagemErro,
						color = MaterialTheme.colorScheme.error,
						modifier = Modifier.padding(16.dp)
					)
				}
			}

			Row(
				modifier = Modifier.fillMaxWidth(),
				horizontalArrangement = Arrangement.spacedBy(12.dp)
			) {
				OutlinedButton(
					onClick = onLimparFormularioClick,
					modifier = Modifier.weight(1f)
				) {
					Text(text = "Limpar")
				}

				Button(
					onClick = onCadastrarAlunoClick,
					modifier = Modifier.weight(1f)
				) {
					Text(text = "Cadastrar aluno")
				}
			}

			Button(
				onClick = onVerResumoClick,
				modifier = Modifier.fillMaxWidth()
			) {
				Text(text = "Ver resumo final")
			}
		}
	}
}

@Composable
private fun CampoNota(
	valor: String,
	rotulo: String,
	onValorChange: (String) -> Unit
) {
	OutlinedTextField(
		value = valor,
		onValueChange = onValorChange,
		label = { Text(rotulo) },
		modifier = Modifier.fillMaxWidth(),
		singleLine = true,
		keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
	)
}