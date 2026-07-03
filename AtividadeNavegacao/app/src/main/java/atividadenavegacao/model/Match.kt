package br.com.treinamento.atividadenavegacao.model

data class Match(
    val id: Int,
    val teamA: String,
    val teamB: String,
    val stadium: String,
    val date: String,
    val time: String,
    val stage: Stage
)