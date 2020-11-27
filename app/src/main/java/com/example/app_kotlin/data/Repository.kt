package com.example.app_kotlin.data

import androidx.lifecycle.LiveData
import com.example.app_kotlin.data.db.FireStoreDatabaseProvider
import com.example.app_kotlin.data.model.Note
import com.example.app_kotlin.data.model.NotesRepository
import kotlin.random.Random

private val idRandom = Random(0)

val noteId: Long
    get() = idRandom.nextLong()

class Repository(private val provider: FireStoreDatabaseProvider) : NotesRepository {

    override fun observeNotes(): LiveData<List<Note>> {
        return provider.observeNotes()
    }

    override fun addOrReplaceNote(newNote: Note): LiveData<Result<Note>> {
        return provider.addOrReplaceNote(newNote)
    }
}

val notesRepository: NotesRepository by lazy { Repository(FireStoreDatabaseProvider()) }