package com.example.app_kotlin.presentation

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.map
import com.example.app_kotlin.data.notesRepository

class NotesViewModel : ViewModel() {
    fun observeViewState(): LiveData<ViewState> = notesRepository.observeNotes()
        .map {
            if (it.isEmpty()) ViewState.EMPTY else ViewState.Value(it)
        }
}