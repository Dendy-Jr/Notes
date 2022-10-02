package com.olehvynnytskyi.notes.presentation.delete_note

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.viewModelScope
import com.olehvynnytskyi.notes.core.navigation.AppNavDirections
import com.olehvynnytskyi.notes.domain.use_case.NoteUseCases
import com.olehvynnytskyi.notes.presentation.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DeleteNoteViewModel @Inject constructor(
    private val appNavDirections: AppNavDirections,
    private val useCases: NoteUseCases,
    private val savedStateHandle: SavedStateHandle
) : BaseViewModel() {

    private val args = DeleteNoteFragmentArgs.fromSavedStateHandle(savedStateHandle)

    fun onDeleteClicked() {
        viewModelScope.launch {
            useCases.deleteNote(args.note)
            navigateTo(appNavDirections.notesScreen)
        }
    }

    fun onDiscardClicked() {
        navigateBack()
    }
}