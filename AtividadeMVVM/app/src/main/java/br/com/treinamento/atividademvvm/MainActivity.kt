package br.com.treinamento.atividademvvm

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import br.com.treinamento.atividademvvm.ui.theme.AtividadeMVVMTheme
import br.com.treinamento.atividademvvm.view.navigation.AppNavigation
import br.com.treinamento.atividademvvm.viewmodel.GerenciadorViewModel

class MainActivity : ComponentActivity() {
    private val gerenciadorViewModel: GerenciadorViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            AtividadeMVVMTheme {
                AppNavigation(viewModel = gerenciadorViewModel)
            }
        }
    }
}