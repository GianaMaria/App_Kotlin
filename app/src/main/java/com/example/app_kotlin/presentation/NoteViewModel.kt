package com.example.app_kotlin.presentation

import androidx.lifecycle.ViewModel
import com.example.app_kotlin.data.Repository
import com.example.app_kotlin.data.model.Note

class NoteViewModel(var note: Note?) : ViewModel() {

    fun updateNote(text: String) {
        note = (note ?: generateNote()).copy(note = text)
    }

    fun updateTitle(text: String) {
        note = (note ?: generateNote()).copy(title = text)
    }

    private fun generateNote(): Note {
        //рандомный цвет из палитры
        return Note()
    }

    //для сохранения
    override fun onCleared() {
        super.onCleared()
        note?.let {
            Repository.addOrReplaceNote(it)
        }
    }

}