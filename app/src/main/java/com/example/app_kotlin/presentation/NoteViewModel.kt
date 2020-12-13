package com.example.app_kotlin.presentation

import androidx.lifecycle.*
import com.example.app_kotlin.data.model.Note
import com.example.app_kotlin.data.model.NotesRepository
import kotlinx.coroutines.launch

class NoteViewModel(private val notesRepository: NotesRepository, var note: Note?) : ViewModel() {

    private val showErrorLiveData = MutableLiveData<Boolean>()

    fun updateNote(text: String) {
        note = (note ?: generateNote()).copy(note = text)
    }

    fun updateTitle(text: String) {
        note = (note ?: generateNote()).copy(title = text)
    }

    fun updateColor(color: Int) {
        note = (note ?: generateNote()).copy(color2 = color)
    }

    fun saveNote() {
        viewModelScope.launch {
            val noteValue = note ?: return@launch
            try {
                notesRepository.addOrReplaceNote(noteValue)
            } catch (th: Throwable) {
                showErrorLiveData.value = true
            }
        }
    }

    fun deleteNote() {
        viewModelScope.launch {
            val noteValue = note ?: return@launch
            try {
                notesRepository.deleteNote(note!!.id.toString())
            } catch (th: Throwable) {
                showErrorLiveData.value = true
            }
        }
    }

    fun showError(): LiveData<Boolean> = showErrorLiveData

    private fun generateNote(): Note {
        return Note()
    }

}