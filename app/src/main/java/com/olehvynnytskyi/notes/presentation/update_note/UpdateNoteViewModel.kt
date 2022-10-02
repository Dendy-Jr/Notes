package com.olehvynnytskyi.notes.presentation.update_note

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.viewModelScope
import com.olehvynnytskyi.notes.core.navigation.AppNavDirections
import com.olehvynnytskyi.notes.domain.use_case.NoteUseCases
import com.olehvynnytskyi.notes.presentation.base.BaseViewModel
import com.olehvynnytskyi.notes.presentation.util.DateFormatter
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class UpdateNoteViewModel @Inject constructor(
    private val appNavDirections: AppNavDirections,
    private val dateFormatter: DateFormatter,
    private val savedStateHandle: SavedStateHandle,
    private val useCases: NoteUseCases
) : BaseViewModel() {

    private val args = UpdateNoteFragmentArgs.fromSavedStateHandle(savedStateHandle)

    fun onUpdateClicked() {
        viewModelScope.launch {
            useCases.updateNote(
                args.note.copy(
                    title = args.note.title,
                    content = args.note.content,
                    date = dateFormatter.format(System.currentTimeMillis())
                )
            )
            navigateTo(appNavDirections.notesScreen)
        }
    }

    fun onDiscardClicked() {
        navigateBack()
    }
}