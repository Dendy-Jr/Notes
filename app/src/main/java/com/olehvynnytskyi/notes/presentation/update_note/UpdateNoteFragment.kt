package com.olehvynnytskyi.notes.presentation.update_note

import androidx.fragment.app.viewModels
import by.kirich1409.viewbindingdelegate.viewBinding
import com.olehvynnytskyi.notes.R
import com.olehvynnytskyi.notes.databinding.FragmentUpdateNoteBinding
import com.olehvynnytskyi.notes.presentation.base.BaseDialogFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class UpdateNoteFragment :
    BaseDialogFragment<UpdateNoteViewModel, FragmentUpdateNoteBinding>(R.layout.fragment_update_note) {

    override val viewModel: UpdateNoteViewModel by viewModels()
    override val binding: FragmentUpdateNoteBinding by viewBinding()

    override fun onBind(): Unit = with(binding) {
        btnUpdate.setOnClickListener {
            viewModel.onUpdateClicked()
        }
        btnDiscard.setOnClickListener {
            viewModel.onDiscardClicked()
        }
    }
}