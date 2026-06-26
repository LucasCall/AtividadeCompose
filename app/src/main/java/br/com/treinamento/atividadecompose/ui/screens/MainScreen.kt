package br.com.treinamento.atividadecompose.ui.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import br.com.treinamento.atividadecompose.data.Colaborador
import br.com.treinamento.atividadecompose.data.ColaboradorRepository

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainScreen() {
    val colaboradores = ColaboradorRepository.colaboradores
    val snackbarHostState = remember { SnackbarHostState() }
    val showForm = remember { mutableStateOf(false) }
    val editing = remember { mutableStateOf<Colaborador?>(null) }
    val pendingDelete = remember { mutableStateOf<Colaborador?>(null) }

    Scaffold(
        snackbarHost = { SnackbarHost(snackbarHostState) },
        topBar = {
            TopAppBar(
                title = { Text("Colaboradores") }
            )
        },
        floatingActionButton = {
            FloatingActionButton(onClick = {
                editing.value = null
                showForm.value = true
            }) {
                Icon(Icons.Default.Add, contentDescription = "Adicionar colaborador")
            }
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .padding(16.dp)
        ) {
            Spacer(modifier = Modifier.height(4.dp))

            if (colaboradores.isEmpty()) {
                EmptyState(
                    onAdd = {
                        editing.value = null
                        showForm.value = true
                    }
                )
            } else {
                LazyColumn(
                    modifier = Modifier.fillMaxSize(),
                    contentPadding = PaddingValues(bottom = 88.dp),
                    verticalArrangement = Arrangement.spacedBy(10.dp)
                ) {
                    items(colaboradores, key = { it.id }) { colaborador ->
                        ColaboradorCard(
                            colaborador = colaborador,
                            onEdit = {
                                editing.value = it
                                showForm.value = true
                            },
                            onDelete = {
                                pendingDelete.value = it
                            }
                        )
                    }
                }
            }
        }
    }

    if (showForm.value) {
        FormDialog(
            colaborador = editing.value,
            onDismiss = { showForm.value = false },
            onSave = { nome, email, nivel ->
                val current = editing.value
                if (current == null) {
                    ColaboradorRepository.add(nome, email, nivel)
                } else {
                    ColaboradorRepository.update(current.copy(nome = nome, email = email, nivel = nivel))
                }
                showForm.value = false
            }
        )
    }

    pendingDelete.value?.let { colaborador ->
        AlertDialog(
            onDismissRequest = { pendingDelete.value = null },
            title = { Text("Remover colaborador") },
            text = { Text("Tem certeza que deseja remover ${colaborador.nome}?") },
            confirmButton = {
                Button(onClick = {
                    ColaboradorRepository.remove(colaborador)
                    pendingDelete.value = null
                }) {
                    Text("Remover")
                }
            },
            dismissButton = {
                Button(onClick = { pendingDelete.value = null }) {
                    Text("Cancelar")
                }
            }
        )
    }
}

@Composable
private fun EmptyState(onAdd: () -> Unit) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors()
    ) {
        Column(modifier = Modifier.padding(20.dp)) {
            Text(
                text = "Nenhum colaborador cadastrado",
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.Bold
            )
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = "Toque no botão + para cadastrar o primeiro colaborador.",
                style = MaterialTheme.typography.bodyMedium
            )
            Spacer(modifier = Modifier.height(16.dp))
            Button(onClick = onAdd) {
                Text("Cadastrar colaborador")
            }
        }
    }
}

@Composable
private fun ColaboradorCard(
    colaborador: Colaborador,
    onEdit: (Colaborador) -> Unit,
    onDelete: (Colaborador) -> Unit
) {
    Card(modifier = Modifier.fillMaxWidth()) {
        Column(modifier = Modifier.padding(16.dp)) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Column(modifier = Modifier.weight(1f)) {
                    Text(
                        text = colaborador.nome,
                        style = MaterialTheme.typography.titleMedium,
                        fontWeight = FontWeight.Bold
                    )
                    Text(text = colaborador.email, style = MaterialTheme.typography.bodyMedium)
                }
                Row {
                    IconButton(onClick = { onEdit(colaborador) }) {
                        Icon(Icons.Default.Edit, contentDescription = "Editar")
                    }
                    IconButton(onClick = { onDelete(colaborador) }) {
                        Icon(Icons.Default.Delete, contentDescription = "Remover")
                    }
                }
            }

            Spacer(modifier = Modifier.height(12.dp))
            HorizontalDivider()
            Spacer(modifier = Modifier.height(12.dp))
            Text(
                text = "Nível",
                style = MaterialTheme.typography.bodyMedium,
                fontWeight = FontWeight.Medium
            )
            Spacer(modifier = Modifier.height(6.dp))
            Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                Icon(
                    imageVector = nivelIcon(colaborador.nivel),
                    contentDescription = nivelLabel(colaborador.nivel)
                )
                Text(text = nivelLabel(colaborador.nivel), style = MaterialTheme.typography.bodyMedium)
            }
        }
    }
}

