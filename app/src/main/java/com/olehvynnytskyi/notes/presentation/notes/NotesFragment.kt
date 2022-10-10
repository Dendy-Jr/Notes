package com.olehvynnytskyi.notes.presentation.notes

import androidx.core.view.isVisible
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.viewModels
import by.kirich1409.viewbindingdelegate.viewBinding
import com.olehvynnytskyi.notes.R
import com.olehvynnytskyi.notes.core.extension.collectWithLifecycle
import com.olehvynnytskyi.notes.databinding.FragmentNotesBinding
import com.olehvynnytskyi.notes.domain.model.Note
import com.olehvynnytskyi.notes.domain.util.NoteOrder
import com.olehvynnytskyi.notes.domain.util.OrderType
import com.olehvynnytskyi.notes.presentation.base.BaseFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class NotesFragment : BaseFragment<NotesViewModel, FragmentNotesBinding>(R.layout.fragment_notes) {

    override val viewModel: NotesViewModel by viewModels()
    override val binding: FragmentNotesBinding by viewBinding()
    private var orderVisibility = true

    override fun onBind() = with(binding) {
        val adapter = NotesRecyclerView(object : NotesRecyclerView.Listener {
            override fun onChooseNote(note: Note) {
                viewModel.onItemClicked(note)
            }

            override fun onDeleteNote(note: Note) {
                viewModel.onDeleteNoteClicked(note)
            }
        })

        btnAddNote.setOnClickListener {
            viewModel.onAddClicked()
        }

        editText.addTextChangedListener {
            viewModel.searchNotes(it.toString())
        }

        collectWithLifecycle(viewModel.state) { noteState ->
            adapter.submitList(noteState.notes)
        }

        ivFilter.setOnClickListener {
            groupOrder.isVisible = orderVisibility
            orderVisibility = orderVisibility.not()
        }

        recyclerView.adapter = adapter

        rgNoteOrder.setOnCheckedChangeListener { radioGroup, _ ->
            val radioButton = radioGroup.checkedRadioButtonId
            viewModel.onOrder(
                when (radioButton) {
                    R.id.rbTitle -> {
                        when {
                            rbAscending.isChecked -> NoteOrder.Title(OrderType.Ascending)
                            else -> NoteOrder.Title(OrderType.Descending)
                        }
                    }
                    R.id.rbColor -> {
                        when {
                            rbAscending.isChecked -> NoteOrder.Color(OrderType.Ascending)
                            else -> NoteOrder.Color(OrderType.Descending)
                        }
                    }
                    else -> {
                        when {
                            rbAscending.isChecked -> NoteOrder.Date(OrderType.Ascending)
                            else -> NoteOrder.Date(OrderType.Descending)
                        }
                    }
                }
            )
        }

        rgOrderType.setOnCheckedChangeListener { radioGroup, _ ->
            val radioButton = radioGroup.checkedRadioButtonId
            viewModel.onOrder(
                when (radioButton) {
                    R.id.rbAscending -> {
                        when {
                            rbTitle.isChecked -> NoteOrder.Title(OrderType.Ascending)
                            rbColor.isChecked -> NoteOrder.Color(OrderType.Ascending)
                            else -> NoteOrder.Date(OrderType.Ascending)
                        }
                    }
                    else -> {
                        when {
                            rbTitle.isChecked -> NoteOrder.Title(OrderType.Descending)
                            rbColor.isChecked -> NoteOrder.Color(OrderType.Descending)
                            else -> NoteOrder.Date(OrderType.Descending)
                        }
                    }
                }
            )
        }
    }
}