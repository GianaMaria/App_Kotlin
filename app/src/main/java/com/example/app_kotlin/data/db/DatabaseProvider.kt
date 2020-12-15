package com.example.app_kotlin.data.db

import com.example.app_kotlin.data.model.Note
import com.example.app_kotlin.data.model.User
import kotlinx.coroutines.flow.Flow

interface DatabaseProvider {
    fun getCurrentUser(): User?
    fun observeNotes(): Flow<List<Note>>
    suspend fun addOrReplaceNote(newNote: Note)
    suspend fun deleteNote(noteId: String)
}