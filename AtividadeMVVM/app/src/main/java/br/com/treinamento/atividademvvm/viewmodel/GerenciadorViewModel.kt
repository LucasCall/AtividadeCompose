package br.com.treinamento.atividademvvm.viewmodel

import androidx.lifecycle.ViewModel
import br.com.treinamento.atividademvvm.model.Aluno
import br.com.treinamento.atividademvvm.model.NotasUiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

class GerenciadorViewModel : ViewModel() {
	private val _uiState = MutableStateFlow(NotasUiState())
	val uiState: StateFlow<NotasUiState> = _uiState.asStateFlow()
	private var proximoAlunoId = 1

	fun atualizarNomeAluno(nomeAluno: String) {
		_uiState.update {
			it.copy(nomeAluno = nomeAluno, mensagemErro = null)
		}
	}

	fun atualizarPrimeiraNota(nota: String) {
		_uiState.update {
			it.copy(primeiraNota = nota, mensagemErro = null)
		}
	}

	fun atualizarSegundaNota(nota: String) {
		_uiState.update {
			it.copy(segundaNota = nota, mensagemErro = null)
		}
	}

	fun cadastrarAluno(): Boolean {
		val estadoAtual = _uiState.value

		if (estadoAtual.nomeAluno.isBlank()) {
			registrarErro("Informe o nome do aluno.")
			return false
		}

		val notas = listOf(
			"primeira" to estadoAtual.primeiraNota,
			"segunda" to estadoAtual.segundaNota
		).map { (descricao, valor) ->
			validarNota(valor)?.also { nota ->
				if (nota !in 0.0..10.0) {
					registrarErro("A $descricao nota deve estar entre 0 e 10.")
					return false
				}
			} ?: run {
				registrarErro("Preencha a $descricao nota com um valor numérico válido.")
				return false
			}
		}

		val media = notas.average()
		val situacao = when {
			media >= 7.0 -> "Aprovado"
			media >= 5.0 && media < 7.0 -> "Em recuperação"
			else -> "Reprovado"
		}

		val novoAluno = Aluno(
			id = proximoAlunoId++,
			nome = estadoAtual.nomeAluno.trim(),
			primeiraNota = notas[0],
			segundaNota = notas[1],
			mediaFinal = media,
			situacao = situacao
		)

		_uiState.update {
			it.copy(
				nomeAluno = "",
				primeiraNota = "",
				segundaNota = "",
				alunos = it.alunos + novoAluno,
				mensagemErro = null
			)
		}

		return true
	}

	fun podeAbrirResumo(): Boolean {
		if (_uiState.value.alunos.isNotEmpty()) {
			return true
		}

		registrarErro("Cadastre pelo menos um aluno para visualizar o resumo.")
		return false
	}

	fun removerAluno(alunoId: Int) {
		_uiState.update {
			it.copy(alunos = it.alunos.filterNot { aluno -> aluno.id == alunoId })
		}
	}

	fun limparFormulario() {
		_uiState.update {
			it.copy(
				nomeAluno = "",
				primeiraNota = "",
				segundaNota = "",
				mensagemErro = null
			)
		}
	}

	private fun validarNota(valor: String): Double? {
		return valor
			.replace(',', '.')
			.toDoubleOrNull()
	}

	private fun registrarErro(mensagem: String) {
		_uiState.update {
			it.copy(mensagemErro = mensagem)
		}
	}
}