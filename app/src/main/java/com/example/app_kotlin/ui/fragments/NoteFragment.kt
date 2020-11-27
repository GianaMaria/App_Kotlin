package com.example.app_kotlin.ui.fragments

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.app_kotlin.R
import com.example.app_kotlin.data.model.Note
import com.example.app_kotlin.presentation.NoteViewModel
import com.example.app_kotlin.ui.main.MainActivity
import kotlinx.android.synthetic.main.fragment_note.*

class NoteFragment : Fragment(R.layout.fragment_note) {
    private val note: Note? by lazy { arguments?.getParcelable(NOTE_KEY) }

    private val noteViewModel by lazy(LazyThreadSafetyMode.NONE) {
        ViewModelProvider(this, object : ViewModelProvider.Factory {
            override fun <T : ViewModel?> create(modelClass: Class<T>): T {
                return NoteViewModel(note) as T
            }

        }).get(NoteViewModel::class.java)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val toolbar = view.findViewById<Toolbar>(R.id.toolbar)

        (activity as AppCompatActivity).also {
            it.setSupportActionBar(toolbar)
            it.supportActionBar?.setHomeButtonEnabled(true)
            it.supportActionBar?.setDisplayHomeAsUpEnabled(true)
        }

        toolbar.setNavigationOnClickListener {
            requireActivity().onBackPressed()
        }

        noteViewModel.note?.let {
            titleEt.setText(it.title)
            bodyEt.setText(it.note)
        }

        noteViewModel.showError().observe(viewLifecycleOwner) {
            Toast.makeText(requireContext(), "Error while saving note!", Toast.LENGTH_SHORT).show()
        }

        titleEt.addTextChangedListener {
            noteViewModel.updateTitle(it?.toString() ?: "")
        }

        bodyEt.addTextChangedListener {
            noteViewModel.updateNote(it?.toString() ?: "")
        }

        fab_save.setOnClickListener {
            noteViewModel.saveNote()
            (requireActivity() as MainActivity).navigateToMain(MainFragment())
        }
    }

    companion object {
        const val NOTE_KEY = "Note"

        fun create(note: Note? = null): NoteFragment {
            val fragment = NoteFragment()
            val arguments = Bundle()
            arguments.putParcelable(NOTE_KEY, note)
            fragment.arguments = arguments

            return fragment
        }
    }
}