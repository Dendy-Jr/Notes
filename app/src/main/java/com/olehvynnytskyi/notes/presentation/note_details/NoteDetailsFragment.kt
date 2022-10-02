package com.olehvynnytskyi.notes.presentation.note_details

import androidx.fragment.app.viewModels
import androidx.navigation.fragment.navArgs
import by.kirich1409.viewbindingdelegate.viewBinding
import com.olehvynnytskyi.notes.R
import com.olehvynnytskyi.notes.core.extension.collectWithLifecycle
import com.olehvynnytskyi.notes.core.extension.getColorFromAttr
import com.olehvynnytskyi.notes.databinding.FragmentNoteDetailsBinding
import com.olehvynnytskyi.notes.presentation.base.BaseFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class NoteDetailsFragment :
    BaseFragment<NoteDetailsViewModel, FragmentNoteDetailsBinding>(R.layout.fragment_note_details) {

    override val viewModel: NoteDetailsViewModel by viewModels()
    override val binding: FragmentNoteDetailsBinding by viewBinding()

    private val args: NoteDetailsFragmentArgs by navArgs()

    override fun onBind() = with(binding) {
        toolbar.setActionIcon(R.drawable.ic_edit)
        root.setBackgroundColor(requireContext().getColorFromAttr(args.note.color))
        collectWithLifecycle(viewModel.note) { note ->
            note?.let {
                etUpdateTitle.setText(it.title)
                etUpdateContent.setText(it.content)
            }
            toolbar.setActionListener {
                viewModel.onActionClicked(
                    title = etUpdateTitle.text.toString(),
                    content = etUpdateContent.text.toString()
                )
            }
        }
    }
}