package br.com.treinamento.atividadecompose

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import br.com.treinamento.atividadecompose.ui.screens.AppScreen
import br.com.treinamento.atividadecompose.ui.theme.AtividadeComposeTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            AtividadeComposeTheme {
                AppScreen()
            }
        }
    }
}