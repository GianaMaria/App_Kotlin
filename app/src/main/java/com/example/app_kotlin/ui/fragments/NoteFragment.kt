package com.example.app_kotlin.ui.fragments

import android.content.DialogInterface
import android.os.Bundle
import android.util.Log
import android.view.*
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.Fragment
import com.example.app_kotlin.R
import com.example.app_kotlin.data.model.Note
import com.example.app_kotlin.databinding.FragmentNoteBinding
import com.example.app_kotlin.presentation.NoteViewModel
import com.example.app_kotlin.ui.main.MainActivity
import com.flask.colorpicker.ColorPickerView
import com.flask.colorpicker.builder.ColorPickerDialogBuilder
import kotlinx.android.synthetic.main.fragment_note.*
import org.koin.android.viewmodel.ext.android.viewModel
import org.koin.core.parameter.parametersOf


class NoteFragment : Fragment() {
    private val note: Note? by lazy { arguments?.getParcelable(NOTE_KEY) }

    private val noteViewModel by viewModel<NoteViewModel> {
        parametersOf(note)
    }

    private var _binding: FragmentNoteBinding? = null
    private val binding: FragmentNoteBinding get() = _binding!!

    private var currentBackgroundColor = -0x1

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentNoteBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        setHasOptionsMenu(true)
        super.onCreate(savedInstanceState)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        with(binding) {

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
                Toast.makeText(requireContext(), "Error while saving note!", Toast.LENGTH_SHORT)
                    .show()
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
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.note_menu, menu)
        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean =
        when (item.itemId) {
            R.id.palette -> paletteNote().let { true }
            R.id.delete -> deleteNote().let { true }
            else -> super.onOptionsItemSelected(item)
        }

    private fun paletteNote() {
        ColorPickerDialogBuilder
            .with(context)
            .setTitle(R.string.title_color_dialog)
            .initialColor(currentBackgroundColor)
            .wheelType(ColorPickerView.WHEEL_TYPE.FLOWER)
            .density(12)
            .setPositiveButton(R.string.dialog_ok) { _, selectedColor, _ ->
                Log.d("COLOR", "Color selected: " + Integer.toHexString(selectedColor))
                noteViewModel.updateColor(selectedColor)
            }
            .setNegativeButton(R.string.dialog_cancel) { d: DialogInterface, _ ->
                d.dismiss()
            }
            .showColorEdit(true)
            .build()
            .show()
    }

    private fun deleteNote() {
        context?.let {
            AlertDialog.Builder(it)
                .setTitle(R.string.confirmation)
                .setMessage(R.string.delete_note)
                .setPositiveButton(R.string.dialog_ok) { _: DialogInterface, _ ->
                    noteViewModel.deleteNote()
                    (requireActivity() as MainActivity).navigateToMain(MainFragment())
                }
                .setNegativeButton(R.string.dialog_cancel) { dialog: DialogInterface, _ ->
                    dialog.dismiss()
                }
                .show()
        }
    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
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