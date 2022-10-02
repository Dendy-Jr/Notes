package com.olehvynnytskyi.notes.presentation.delete_note

import androidx.fragment.app.viewModels
import by.kirich1409.viewbindingdelegate.viewBinding
import com.olehvynnytskyi.notes.R
import com.olehvynnytskyi.notes.databinding.FragmentDeleteNoteBinding
import com.olehvynnytskyi.notes.presentation.base.BaseDialogFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class DeleteNoteFragment :
    BaseDialogFragment<DeleteNoteViewModel, FragmentDeleteNoteBinding>(R.layout.fragment_delete_note) {

    override val viewModel: DeleteNoteViewModel by viewModels()
    override val binding: FragmentDeleteNoteBinding by viewBinding()

    override fun onBind() = with(binding) {
        btnDelete.setOnClickListener {
            viewModel.onDeleteClicked()
        }
        btnDiscard.setOnClickListener {
            viewModel.onDiscardClicked()
        }
    }
}