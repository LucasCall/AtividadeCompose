package br.com.treinamento.atividadenavegacao.utils

fun getFlag(team: String): String {
    return when (team) {
        "Brasil" -> "🇧🇷"
        "Argentina" -> "🇦🇷"
        "França" -> "🇫🇷"
        "Inglaterra" -> "GB"
        "Portugal" -> "🇵🇹"
        "Espanha" -> "🇪🇸"
        "Noruega" -> "🇳🇴"
        "México" -> "🇲🇽"
        "Canadá" -> "🇨🇦"
        "Marrocos" -> "🇲🇦"
        "Estados Unidos" -> "🇺🇸"
        "Bélgica" -> "🇧🇪"
        "Suíça" -> "🇨🇭"
        "Colômbia" -> "🇨🇴"
        "Paraguai" -> "🇵🇾"
        "Egito" -> "🇪🇬"
        else -> "⚽"
    }
}
