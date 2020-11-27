package com.example.app_kotlin.data.db

import androidx.lifecycle.LiveData
import com.example.app_kotlin.data.model.Note

interface DatabaseProvider {
    fun observeNotes(): LiveData<List<Note>>
    fun addOrReplaceNote(newNote: Note): LiveData<Result<Note>>
}