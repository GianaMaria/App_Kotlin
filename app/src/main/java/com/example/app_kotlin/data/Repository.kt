package com.example.app_kotlin.data

import android.graphics.Color
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.app_kotlin.data.model.Note
import com.example.app_kotlin.data.model.NotesRepository
import com.example.app_kotlin.data.model.randomColor
import kotlin.random.Random

private val idRandom = Random(0)

val noteId: Long
    get() = idRandom.nextLong()

object Repository : NotesRepository {
    private val notes: MutableList<Note> = mutableListOf(
        Note(
            title = "Моя первая заметка",
            note = "Kotlin",
            color = randomColor()
        ),
        Note(
            title = "Моя первая заметка",
            note = "Kotlin очень краткий, но при этом выразительный язык",
            color = randomColor()
        ),
        Note(
            title = "Моя первая заметка",
            note = "Kotlin очень краткий, но при этом выразительный язык",
            color = randomColor()
        ),
        Note(
            title = "Моя первая заметка",
            note = "Kotlin очень краткий, но при этом выразительный язык",
            color = randomColor()
        ),
        Note(
            title = "Моя первая заметка",
            note = "Kotlin очень краткий, но при этом выразительный язык",
            color = randomColor()
        )
    )

    private val allNotes = MutableLiveData(getListForNotify())

    override fun observeNotes(): LiveData<List<Note>> {
        return allNotes
    }

    override fun addOrReplaceNote(newNote: Note) {
        val note = notes.find { it.id == newNote.id }?.let {
            if (it == newNote) return
            notes.remove(it)
        }

        notes.add(newNote)

        allNotes.postValue(getListForNotify())
    }

    private fun getListForNotify(): List<Note> = notes.toMutableList().also {
        it.reverse()
    }

}