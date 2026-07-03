package br.com.treinamento.atividadenavegacao

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import br.com.treinamento.atividadenavegacao.ui.theme.AtividadeDeNavegacaoTheme

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        enableEdgeToEdge()

        setContent {
            AtividadeDeNavegacaoTheme {
                WorldCupApp()
            }
        }
    }
}