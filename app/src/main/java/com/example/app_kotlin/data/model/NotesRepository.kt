package com.example.app_kotlin.data.model

import kotlinx.coroutines.flow.Flow

interface NotesRepository {
    suspend fun getCurrentUser(): User?
    fun observeNotes(): Flow<List<Note>>
    suspend fun addOrReplaceNote(newNote: Note)
    suspend fun deleteNote(noteId: String)
}