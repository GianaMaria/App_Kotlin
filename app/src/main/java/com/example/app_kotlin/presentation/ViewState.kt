package com.example.app_kotlin.presentation

import com.example.app_kotlin.data.model.Note

sealed class ViewState {
    data class Value(val notes: List<Note>) : ViewState()
    object EMPTY : ViewState()
}