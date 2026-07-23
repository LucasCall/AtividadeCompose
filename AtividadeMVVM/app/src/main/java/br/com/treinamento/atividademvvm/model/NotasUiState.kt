package br.com.treinamento.atividademvvm.model

data class NotasUiState(
	val nomeAluno: String = "",
	val primeiraNota: String = "",
	val segundaNota: String = "",
	val alunos: List<Aluno> = emptyList(),
	val mensagemErro: String? = null
) {
	val totalAprovados: Int
		get() = alunos.count { it.situacao == "Aprovado" }

	val totalRecuperacao: Int
		get() = alunos.count { it.situacao == "Em recuperação" }

	val totalReprovados: Int
		get() = alunos.count { it.situacao == "Reprovado" }
}