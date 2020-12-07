package com.example.app_kotlin.data.model

import androidx.lifecycle.LiveData

interface NotesRepository {
    fun getCurrentUser(): User?
    fun observeNotes(): LiveData<List<Note>>
    fun addOrReplaceNote(newNote: Note): LiveData<Result<Note>>
    fun deleteNote(noteId: String): LiveData<Result<Unit>>
}