package com.olehvynnytskyi.notes.presentation.notes

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.olehvynnytskyi.notes.R
import com.olehvynnytskyi.notes.core.extension.getColorFromAttr
import com.olehvynnytskyi.notes.databinding.ItemNoteBinding
import com.olehvynnytskyi.notes.domain.model.Note

class NotesRecyclerView(
    private val listener: Listener
) : ListAdapter<Note, NotesRecyclerView.NoteViewHolder>(DiffCallback), View.OnClickListener {

    override fun onClick(view: View) {
        val note = view.tag as Note
        when (view.id) {
            R.id.ivDelete -> listener.onDeleteNote(note)
            else -> listener.onChooseNote(note)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NoteViewHolder {
        return ItemNoteBinding.inflate(
            LayoutInflater.from(parent.context), parent, false
        ).apply {
            ivDelete.setOnClickListener(this@NotesRecyclerView)
            root.setOnClickListener(this@NotesRecyclerView)
        }.let(::NoteViewHolder)
    }

    override fun onBindViewHolder(holder: NoteViewHolder, position: Int) {
        holder.onBind(getItem(position))
    }

    inner class NoteViewHolder(
        private val binding: ItemNoteBinding
    ) : RecyclerView.ViewHolder(binding.root) {

        fun onBind(note: Note) = with(binding) {
            root.tag = note
            ivDelete.tag = note
            tvTitle.text = note.title
            tvDate.text = note.date
            container.setBackgroundColor(itemView.context.getColorFromAttr(note.color))
        }
    }

    companion object DiffCallback : DiffUtil.ItemCallback<Note>() {
        override fun areItemsTheSame(oldItem: Note, newItem: Note): Boolean =
            oldItem.id == newItem.id

        override fun areContentsTheSame(oldItem: Note, newItem: Note): Boolean =
            oldItem == newItem
    }

    interface Listener {
        fun onChooseNote(note: Note)
        fun onDeleteNote(note: Note)
    }
}