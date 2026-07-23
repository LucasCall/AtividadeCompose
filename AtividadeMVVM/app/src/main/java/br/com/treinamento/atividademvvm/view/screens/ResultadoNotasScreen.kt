package br.com.treinamento.atividademvvm.view.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import br.com.treinamento.atividademvvm.model.Aluno
import br.com.treinamento.atividademvvm.model.NotasUiState
import java.util.Locale

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ResultadoNotasScreen(
	uiState: NotasUiState,
	onVoltarCadastroClick: () -> Unit,
	onRemoverAlunoClick: (Int) -> Unit
) {
	Scaffold(
		topBar = {
			TopAppBar(
				title = {
					Text(text = "Resumo dos alunos")
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
			verticalArrangement = Arrangement.spacedBy(16.dp)
		) {
			Card(modifier = Modifier.fillMaxWidth()) {
				Column(
					modifier = Modifier.padding(16.dp),
					verticalArrangement = Arrangement.spacedBy(12.dp)
				) {
					Text(
						text = "Total de alunos cadastrados: ${uiState.alunos.size}",
						style = MaterialTheme.typography.titleLarge
					)
					ResumoItem("Aprovados", uiState.totalAprovados, Color(0xFF2E7D32))
					ResumoItem("Em recuperação", uiState.totalRecuperacao, Color(0xFFF9A825))
					ResumoItem("Reprovados", uiState.totalReprovados, Color(0xFFC62828))
				}
			}

			Text(
				text = "Regras: 7 ou superior aprovado, entre 5 e 6,9 em recuperação, abaixo de 5 reprovado.",
				style = MaterialTheme.typography.bodyMedium
			)

			OutlinedButton(
				onClick = onVoltarCadastroClick,
				modifier = Modifier.fillMaxWidth()
			) {
				Text(text = "Voltar para cadastro")
			}

			Text(
				text = "Lista de alunos cadastrados",
				style = MaterialTheme.typography.titleMedium
			)

			if (uiState.alunos.isEmpty()) {
				Text(
					text = "Nenhum aluno cadastrado.",
					style = MaterialTheme.typography.bodyLarge
				)
			} else {
				uiState.alunos.forEach { aluno ->
					AlunoCard(
						aluno = aluno,
						onRemoverAlunoClick = { onRemoverAlunoClick(aluno.id) }
					)
				}
			}
		}
	}
}

@Composable
private fun ResumoItem(
	titulo: String,
	quantidade: Int,
	cor: Color
) {
	Text(
		text = "$titulo: $quantidade",
		color = Color.White,
		modifier = Modifier
			.clip(RoundedCornerShape(12.dp))
			.background(cor)
			.fillMaxWidth()
			.padding(horizontal = 12.dp, vertical = 10.dp)
	)
}

@Composable
private fun AlunoCard(
	aluno: Aluno,
	onRemoverAlunoClick: () -> Unit
) {
	val corSituacao = when (aluno.situacao) {
		"Aprovado" -> Color(0xFF2E7D32)
		"Em recuperação" -> Color(0xFFF9A825)
		else -> Color(0xFFC62828)
	}

	Card(modifier = Modifier.fillMaxWidth()) {
		Column(
			modifier = Modifier.padding(16.dp),
			verticalArrangement = Arrangement.spacedBy(10.dp)
		) {
			Text(
				text = aluno.nome,
				style = MaterialTheme.typography.titleMedium
			)
			Text(text = "Primeira nota: ${formatarMedia(aluno.primeiraNota)}")
			Text(text = "Segunda nota: ${formatarMedia(aluno.segundaNota)}")
			Text(text = "Média: ${formatarMedia(aluno.mediaFinal)}")
			Text(
				text = aluno.situacao,
				color = Color.White,
				modifier = Modifier
					.clip(RoundedCornerShape(12.dp))
					.background(corSituacao)
					.padding(horizontal = 12.dp, vertical = 8.dp)
			)
			Button(
				onClick = onRemoverAlunoClick,
				modifier = Modifier.fillMaxWidth()
			) {
				Text(text = "Remover aluno")
			}
		}
	}
}

private fun formatarMedia(media: Double): String {
	return String.format(Locale.US, "%.1f", media).replace('.', ',')
}