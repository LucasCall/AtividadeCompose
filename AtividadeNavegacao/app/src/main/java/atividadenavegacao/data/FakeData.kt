package br.com.treinamento.atividadenavegacao.data

import br.com.treinamento.atividadenavegacao.model.Match
import br.com.treinamento.atividadenavegacao.model.Stage
import br.com.treinamento.atividadenavegacao.model.TournamentState

object FakeData {

    private data class MatchSeed(
        val id: Int,
        val stadium: String,
        val date: String,
        val time: String
    )

    private data class KnockoutPairing(
        val seed: MatchSeed,
        val sourceMatchA: Int,
        val sourceMatchB: Int
    )

    private val quarterFinalSeeds = listOf(
        MatchSeed(9, "Hard Rock Stadium", "09/07/2026", "17:00"),
        MatchSeed(10, "SoFi Stadium", "10/07/2026", "16:00"),
        MatchSeed(11, "AT&T Stadium", "11/07/2026", "18:00"),
        MatchSeed(12, "Lumen Field", "11/07/2026", "22:00")
    )

    private val quarterFinalPairings = listOf(
        KnockoutPairing(
            seed = quarterFinalSeeds[0],
            sourceMatchA = 2,
            sourceMatchB = 4
        ),
        KnockoutPairing(
            seed = quarterFinalSeeds[1],
            sourceMatchA = 5,
            sourceMatchB = 6
        ),
        KnockoutPairing(
            seed = quarterFinalSeeds[2],
            sourceMatchA = 1,
            sourceMatchB = 3
        ),
        KnockoutPairing(
            seed = quarterFinalSeeds[3],
            sourceMatchA = 7,
            sourceMatchB = 8
        )
    )

    private val semiFinalSeeds = listOf(
        MatchSeed(13, "AT&T Stadium", "14/07/2026", "16:00"),
        MatchSeed(14, "Mercedes-Benz Stadium", "15/07/2026", "16:00")
    )

    private val finalSeed = MatchSeed(
        id = 16,
        stadium = "MetLife Stadium",
        date = "19/07/2026",
        time = "16:00"
    )

    val roundOf16Matches = listOf(

        Match(
            id = 1,
            teamA = "Brasil",
            teamB = "Noruega",
            stadium = "MetLife Stadium",
            date = "05/07/2026",
            time = "17:00",
            stage = Stage.ROUND_16
        ),

        Match(
            id = 2,
            teamA = "Paraguai",
            teamB = "França",
            stadium = "Lincoln Financial Field",
            date = "04/07/2026",
            time = "18:00",
            stage = Stage.ROUND_16
        ),

        Match(
            id = 3,
            teamA = "México",
            teamB = "Inglaterra",
            stadium = "Estádio Azteca",
            date = "05/07/2026",
            time = "21:00",
            stage = Stage.ROUND_16
        ),

        Match(
            id = 4,
            teamA = "Canadá",
            teamB = "Marrocos",
            stadium = "NRG Stadium",
            date = "04/07/2026",
            time = "14:00",
            stage = Stage.ROUND_16
        ),

        Match(
            id = 5,
            teamA = "Portugal",
            teamB = "Espanha",
            stadium = "AT&T Stadium",
            date = "06/07/2026",
            time = "16:00",
            stage = Stage.ROUND_16
        ),

        Match(
            id = 6,
            teamA = "Estados Unidos",
            teamB = "Bélgica",
            stadium = "Lumen Field",
            date = "06/07/2026",
            time = "21:00",
            stage = Stage.ROUND_16
        ),

        Match(
            id = 7,
            teamA = "Argentina",
            teamB = "Egito",
            stadium = "Mercedes-Benz Stadium",
            date = "07/07/2026",
            time = "13:00",
            stage = Stage.ROUND_16
        ),

        Match(
            id = 8,
            teamA = "Suíça",
            teamB = "Colômbia",
            stadium = "BC Place",
            date = "07/07/2026",
            time = "17:00",
            stage = Stage.ROUND_16
        )
    )

    fun matchesForStage(
        stage: Stage,
        tournamentState: TournamentState
    ): List<Match> {
        return when (stage) {
            Stage.ROUND_16 -> roundOf16Matches
            Stage.QUARTER_FINAL -> buildQuarterFinalMatches(tournamentState)

            Stage.SEMI_FINAL -> buildMatches(
                seeds = semiFinalSeeds,
                qualifiedTeams = tournamentState.quarterFinalWinners
                    .toSortedMap()
                    .values
                    .toList(),
                stage = stage
            )

            Stage.FINAL -> buildMatches(
                seeds = listOf(finalSeed),
                qualifiedTeams = tournamentState.semiFinalWinners
                    .toSortedMap()
                    .values
                    .toList(),
                stage = stage
            )
        }
    }

    private fun buildQuarterFinalMatches(
        tournamentState: TournamentState
    ): List<Match> {
        return quarterFinalPairings.mapNotNull { pairing ->
            val teamA = tournamentState.round16Winners[pairing.sourceMatchA]
            val teamB = tournamentState.round16Winners[pairing.sourceMatchB]

            if (teamA == null || teamB == null) {
                return emptyList()
            }

            Match(
                id = pairing.seed.id,
                teamA = teamA,
                teamB = teamB,
                stadium = pairing.seed.stadium,
                date = pairing.seed.date,
                time = pairing.seed.time,
                stage = Stage.QUARTER_FINAL
            )
        }
    }

    private fun buildMatches(
        seeds: List<MatchSeed>,
        qualifiedTeams: List<String>,
        stage: Stage
    ): List<Match> {
        if (qualifiedTeams.size < seeds.size * 2) {
            return emptyList()
        }

        return seeds.mapIndexed { index, seed ->
            val teamIndex = index * 2

            Match(
                id = seed.id,
                teamA = qualifiedTeams[teamIndex],
                teamB = qualifiedTeams[teamIndex + 1],
                stadium = seed.stadium,
                date = seed.date,
                time = seed.time,
                stage = stage
            )
        }
    }
}