package com.example.app_kotlin.ui.fragments

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.app_kotlin.R
import com.example.app_kotlin.data.model.Note
import com.example.app_kotlin.presentation.NotesViewModel
import com.example.app_kotlin.presentation.ViewState
import com.example.app_kotlin.ui.adapter.NotesAdapter
import com.example.app_kotlin.ui.main.MainActivity
import kotlinx.android.synthetic.main.fragment_main.*

class MainFragment : Fragment(R.layout.fragment_main) {

    private val viewMode by lazy(LazyThreadSafetyMode.NONE) {
        ViewModelProvider(this).get(NotesViewModel::class.java)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        (activity as AppCompatActivity).also {
            it.setSupportActionBar(toolbar)
        }

        val adapter = NotesAdapter {
            navigateToNote(it)
        }

        mainRecycler.adapter = adapter

        viewMode.observeViewState().observe(viewLifecycleOwner) {
            when (it) {
                is ViewState.Value -> {
                    adapter.submitList(it.notes)
                }
                ViewState.EMPTY -> Unit
            }
        }

        fab.setOnClickListener {
            navigateToCreation()
        }
    }

    private fun navigateToNote(note: Note) {
        (requireActivity() as MainActivity).navigateTo(NoteFragment.create(note))
    }

    private fun navigateToCreation() {
        (requireActivity() as MainActivity).navigateTo(NoteFragment.create(null))
    }
}


