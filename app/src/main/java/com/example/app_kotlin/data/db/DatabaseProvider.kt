package com.example.app_kotlin.data.db

import androidx.lifecycle.LiveData
import com.example.app_kotlin.data.model.Note
import com.example.app_kotlin.data.model.User

interface DatabaseProvider {
    fun getCurrentUser(): User?
    fun observeNotes(): LiveData<List<Note>>
    fun addOrReplaceNote(newNote: Note): LiveData<Result<Note>>
    fun deleteNote(noteId: String): LiveData<Result<Unit>>
}