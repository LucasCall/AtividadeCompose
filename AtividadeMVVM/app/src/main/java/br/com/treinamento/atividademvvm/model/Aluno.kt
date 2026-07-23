package br.com.treinamento.atividademvvm.model

data class Aluno(
	val id: Int,
	val nome: String,
	val primeiraNota: Double,
	val segundaNota: Double,
	val mediaFinal: Double,
	val situacao: String
)