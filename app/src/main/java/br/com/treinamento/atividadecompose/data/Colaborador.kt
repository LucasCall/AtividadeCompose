package br.com.treinamento.atividadecompose.data

enum class Nivel { ADMINISTRATIVO, FINANCEIRO, GERENCIA, SUPORTE }

data class Colaborador(
    val id: Int = 0,
    val nome: String,
    val email: String,
    val nivel: Nivel
)
