package br.com.treinamento.atividadecompose.data

import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.snapshots.SnapshotStateList
import kotlin.jvm.Synchronized

object ColaboradorRepository {
    private val _colaboradores: SnapshotStateList<Colaborador> = mutableStateListOf()
    private var nextId = 1

    val colaboradores: List<Colaborador> get() = _colaboradores

    @Synchronized
    fun add(nome: String, email: String, nivel: Nivel) {
        val id = nextId++
        _colaboradores.add(Colaborador(id = id, nome = nome, email = email, nivel = nivel))
    }

    fun update(updated: Colaborador) {
        val idx = _colaboradores.indexOfFirst { it.id == updated.id }
        if (idx >= 0) _colaboradores[idx] = updated
    }

    fun remove(colaborador: Colaborador) {
        _colaboradores.removeIf { it.id == colaborador.id }
    }

    fun clear() { _colaboradores.clear() }
}
