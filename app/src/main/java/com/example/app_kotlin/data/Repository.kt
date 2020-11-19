package com.example.app_kotlin.data

import android.graphics.Color
import com.example.app_kotlin.data.model.Note
import com.example.app_kotlin.data.model.NotesRepository

object Repository : NotesRepository {
    val notes: List<Note> = listOf(
        Note(
            title = "Моя первая заметка",
            note = "Kotlin",
            color = Color.rgb(202, 217, 214)
        ),
        Note(
            title = "Моя первая заметка",
            note = "Kotlin очень краткий, но при этом выразительный язык",
            color = Color.rgb(68, 129, 131)
        ),
        Note(
            title = "Моя первая заметка",
            note = "Kotlin очень краткий, но при этом выразительный язык",
            color = Color.rgb(92, 108, 121)
        ),
        Note(
            title = "Моя первая заметка",
            note = "Kotlin очень краткий, но при этом выразительный язык",
            color = Color.rgb(191, 209, 211)
        ),
        Note(
            title = "Моя первая заметка",
            note = "Kotlin очень краткий, но при этом выразительный язык",
            color = Color.rgb(167, 207, 209)
        ),
        Note(
            title = "Моя первая заметка",
            note = "Kotlin очень краткий, но при этом выразительный язык",
            color = Color.rgb(168, 180, 186)
        ),
        Note(
            title = "Моя первая заметка",
            note = "Kotlin очень краткий, но при этом выразительный язык",
            color = Color.rgb(122, 190, 194)
        ),
        Note(
            title = "Моя первая заметка",
            note = "Kotlin очень краткий, но при этом выразительный язык",
            color = Color.rgb(202, 217, 214)
        ),
        Note(
            title = "Моя первая заметка",
            note = "Kotlin очень краткий, но при этом выразительный язык",
            color = Color.rgb(191, 209, 211)
        ),
        Note(
            title = "Моя первая заметка",
            note = "Kotlin очень краткий, но при этом выразительный язык",
            color = Color.rgb(68, 129, 131)
        ),
        Note(
            title = "Моя первая заметка",
            note = "Kotlin очень краткий, но при этом выразительный язык",
            color = Color.rgb(92, 108, 121)
        ),
        Note(
            title = "Моя первая заметка",
            note = "Kotlin очень краткий, но при этом выразительный язык",
            color = Color.rgb(167, 207, 209)
        )

    )

    override fun getAllNotes(): List<Note> {
        return notes
    }

}