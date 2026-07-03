package br.com.treinamento.atividadenavegacao.navigation

object Rotas {

    const val HOME = "home"

    const val STAGE = "stage/{stage}"

    const val MATCH_DETAIL = "match/{stage}/{matchId}"

    const val FINAL = "final"

    fun stageRoute(stage: String): String {
        return "stage/$stage"
    }

    fun matchDetailRoute(stage: String, matchId: Int): String {
        return "match/$stage/$matchId"
    }
}