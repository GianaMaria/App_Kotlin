package com.example.app_kotlin.data

import com.example.app_kotlin.data.db.DatabaseProvider
import com.example.app_kotlin.data.model.Note
import com.example.app_kotlin.data.model.NotesRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.withContext
import kotlin.random.Random

private val idRandom = Random(0)

val noteId: Long
    get() = idRandom.nextLong()

class Repository(private val provider: DatabaseProvider) : NotesRepository {

    override suspend fun getCurrentUser() = withContext(Dispatchers.IO) {
        provider.getCurrentUser()
    }

    override fun observeNotes(): Flow<List<Note>> {
        return provider.observeNotes()
    }

    override suspend fun addOrReplaceNote(newNote: Note) = withContext(Dispatchers.IO) {
        provider.addOrReplaceNote(newNote)
    }

    override suspend fun deleteNote(noteId: String) = withContext(Dispatchers.IO) {
        provider.deleteNote(noteId)
    }

}
