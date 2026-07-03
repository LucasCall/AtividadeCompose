package br.com.treinamento.atividadenavegacao.model

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateMapOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue

class TournamentState {

    val round16Winners = mutableStateMapOf<Int, String>()

    val quarterFinalWinners = mutableStateMapOf<Int, String>()

    val semiFinalWinners = mutableStateMapOf<Int, String>()

    var champion by mutableStateOf<String?>(null)

    fun reset() {
        round16Winners.clear()
        quarterFinalWinners.clear()
        semiFinalWinners.clear()
        champion = null
    }

    fun winnersFor(stage: Stage): Map<Int, String> {
        return when (stage) {
            Stage.ROUND_16 -> round16Winners
            Stage.QUARTER_FINAL -> quarterFinalWinners
            Stage.SEMI_FINAL -> semiFinalWinners
            Stage.FINAL -> if (champion == null) emptyMap() else mapOf(0 to champion!!)
        }
    }

    fun isStageComplete(stage: Stage, totalMatches: Int): Boolean {
        if (totalMatches == 0) {
            return false
        }

        return when (stage) {
            Stage.FINAL -> champion != null
            else -> winnersFor(stage).size == totalMatches
        }
    }

    fun saveWinner(stage: Stage, matchId: Int, winner: String) {
        when (stage) {
            Stage.ROUND_16 -> {
                if (round16Winners[matchId] != winner) {
                    quarterFinalWinners.clear()
                    semiFinalWinners.clear()
                    champion = null
                }
                round16Winners[matchId] = winner
            }

            Stage.QUARTER_FINAL -> {
                if (quarterFinalWinners[matchId] != winner) {
                    semiFinalWinners.clear()
                    champion = null
                }
                quarterFinalWinners[matchId] = winner
            }

            Stage.SEMI_FINAL -> {
                if (semiFinalWinners[matchId] != winner) {
                    champion = null
                }
                semiFinalWinners[matchId] = winner
            }

            Stage.FINAL -> {
                champion = winner
            }
        }
    }
}