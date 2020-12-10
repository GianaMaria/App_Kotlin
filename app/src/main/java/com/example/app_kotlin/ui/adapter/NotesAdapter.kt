package com.example.app_kotlin.ui.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.app_kotlin.data.model.Note
import com.example.app_kotlin.data.model.mapToColor
import com.example.app_kotlin.databinding.ItemNoteBinding

val DIFF_UTIL: DiffUtil.ItemCallback<Note> = object : DiffUtil.ItemCallback<Note>() {
    override fun areItemsTheSame(oldItem: Note, newItem: Note): Boolean {
        return oldItem == newItem
    }

    override fun areContentsTheSame(oldItem: Note, newItem: Note): Boolean {
        return true
    }
}

class NotesAdapter(val noteHandler: (Note) -> Unit) :
    ListAdapter<Note, NotesAdapter.NoteViewHolder>(DIFF_UTIL) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NoteViewHolder {
        return NoteViewHolder(parent)
    }

    override fun onBindViewHolder(holder: NoteViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    inner class NoteViewHolder(
        parent: ViewGroup, private val binding: ItemNoteBinding = ItemNoteBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false,
        )
    ) : RecyclerView.ViewHolder(
        binding.root
    ) {

        lateinit var currentNote: Note

        private val clickListener: View.OnClickListener = View.OnClickListener {
            noteHandler.invoke(currentNote)
        }

        fun bind(item: Note) {
            currentNote = item
            with(binding) {
                title.text = item.title
                body.text = item.note
                if (item.color2 == 0) {
                    root.setBackgroundColor(item.color.mapToColor(binding.root.context))
                } else {
                    root.setBackgroundColor(item.color2)
                }
                root.setOnClickListener(clickListener)
            }
        }
    }
}