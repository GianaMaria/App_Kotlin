package com.example.app_kotlin.data.model

interface NotesRepository {
    fun getAllNotes(): List<Note>
}