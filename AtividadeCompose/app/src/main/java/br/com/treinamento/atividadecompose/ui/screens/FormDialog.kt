package br.com.treinamento.atividadecompose.ui.screens

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import br.com.treinamento.atividadecompose.data.Colaborador
import br.com.treinamento.atividadecompose.data.Nivel
import br.com.treinamento.atividadecompose.ui.theme.AtividadeComposeTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FormDialog(
    colaborador: Colaborador?,
    onDismiss: () -> Unit,
    onSave: (String, String, Nivel) -> Unit
) {
    val nomeState = remember { mutableStateOf(colaborador?.nome ?: "") }
    val emailState = remember { mutableStateOf(colaborador?.email ?: "") }
    val nivelState = remember { mutableStateOf(colaborador?.nivel ?: Nivel.SUPORTE) }
    val expanded = remember { mutableStateOf(false) }

    AlertDialog(
        onDismissRequest = onDismiss,
        title = { Text(if (colaborador == null) "Adicionar" else "Editar") },
        text = {
            Column {
                OutlinedTextField(
                    value = nomeState.value,
                    onValueChange = { nomeState.value = it },
                    label = { Text("Nome") },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(bottom = 8.dp)
                )
                OutlinedTextField(
                    value = emailState.value,
                    onValueChange = { emailState.value = it },
                    label = { Text("E-mail") },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(bottom = 8.dp)
                )

                Row(modifier = Modifier.fillMaxWidth()) {
                    Button(onClick = { expanded.value = !expanded.value }) {
                        Icon(
                            imageVector = nivelIcon(nivelState.value),
                            contentDescription = nivelLabel(nivelState.value)
                        )
                        Text(text = "  ${nivelLabel(nivelState.value)}")
                        Icon(
                            imageVector = Icons.Default.ArrowDropDown,
                            contentDescription = null
                        )
                    }

                    DropdownMenu(
                        expanded = expanded.value,
                        onDismissRequest = { expanded.value = false }
                    ) {
                        Nivel.values().forEach { n ->
                            DropdownMenuItem(
                                text = {
                                    Row {
                                        Icon(
                                            imageVector = nivelIcon(n),
                                            contentDescription = nivelLabel(n)
                                        )
                                        Text(text = "  ${nivelLabel(n)}")
                                    }
                                },
                                onClick = {
                                    nivelState.value = n
                                    expanded.value = false
                                }
                            )
                        }
                    }
                }
            }
        },
        confirmButton = {
            Button(onClick = {
                onSave(
                    nomeState.value.trim(),
                    emailState.value.trim(),
                    nivelState.value
                )
            }) {
                Text("Salvar")
            }
        },
        dismissButton = {
            Button(onClick = onDismiss) {
                Text("Cancelar")
            }
        }
    )
}

@Composable
fun FormDialogContent(
    nome: String,
    email: String,
    nivel: Nivel
) {
    Column {
        Text("Adicionar")

        OutlinedTextField(
            value = nome,
            onValueChange = {},
            label = { Text("Nome") }
        )

        OutlinedTextField(
            value = email,
            onValueChange = {},
            label = { Text("E-mail") }
        )

        Row {
            Button(onClick = {}) {
                Icon(
                    imageVector = Icons.Default.ArrowDropDown,
                    contentDescription = null
                )
                Text(" $nivel")
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun FormDialogPreview() {
    AtividadeComposeTheme {
        FormDialogContent(
            nome = "Lucas",
            email = "lucas@email.com",
            nivel = Nivel.SUPORTE
        )
    }
}