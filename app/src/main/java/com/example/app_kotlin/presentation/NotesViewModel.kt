package com.example.app_kotlin.presentation


import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.map
import com.example.app_kotlin.data.Repository

class NotesViewModel : ViewModel() {
    fun observeViewState(): LiveData<ViewState> = Repository.observeNotes()
        .map {
            if (it.isEmpty()) ViewState.EMPTY else ViewState.Value(it)
        }
}