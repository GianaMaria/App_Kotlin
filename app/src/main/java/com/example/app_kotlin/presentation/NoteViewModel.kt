package com.example.app_kotlin.presentation

import androidx.lifecycle.*
import com.example.app_kotlin.data.model.Note
import com.example.app_kotlin.data.model.NotesRepository

class NoteViewModel(private val notesRepository: NotesRepository, var note: Note?) : ViewModel(),
    LifecycleOwner {

    private val showErrorLiveData = MutableLiveData<Boolean>()
    private val lifecycle = LifecycleRegistry(this).also {
        it.currentState = Lifecycle.State.RESUMED
    }

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
        note?.let { note ->
            notesRepository.addOrReplaceNote(note)
                .observe(this) {
                    it.onFailure {
                        showErrorLiveData.value = true
                    }
                }
        }
    }

    fun deleteNote() {
        note?.let { note ->
            notesRepository.deleteNote(note.id.toString())
                .observe(this) {
                    it.onFailure {
                        showErrorLiveData.value = true
                    }
                }
        }
    }

    fun showError(): LiveData<Boolean> = showErrorLiveData

    override fun onCleared() {
        super.onCleared()
        lifecycle.currentState = Lifecycle.State.DESTROYED
    }

    private fun generateNote(): Note {
        return Note()
    }

    override fun getLifecycle(): Lifecycle {
        return lifecycle
    }

}