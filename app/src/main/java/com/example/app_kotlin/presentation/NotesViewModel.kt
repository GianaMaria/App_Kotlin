package com.example.app_kotlin.presentation


import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.app_kotlin.data.Repository

class NotesViewModel : ViewModel() {
    private val viewStateLiveData = MutableLiveData<ViewState>(ViewState.EMPTY)

    init {
        val notes = Repository.getAllNotes()
        viewStateLiveData.value = ViewState.Value(notes)
    }

    fun observeViewState(): LiveData<ViewState> = viewStateLiveData
}