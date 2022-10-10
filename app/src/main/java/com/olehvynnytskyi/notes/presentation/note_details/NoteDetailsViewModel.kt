package com.olehvynnytskyi.notes.presentation.note_details

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.viewModelScope
import com.olehvynnytskyi.notes.core.navigation.AppNavDirections
import com.olehvynnytskyi.notes.domain.model.Note
import com.olehvynnytskyi.notes.domain.use_case.NoteUseCases
import com.olehvynnytskyi.notes.presentation.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class NoteDetailsViewModel @Inject constructor(
    private val appNavDirections: AppNavDirections,
    private val savedStateHandle: SavedStateHandle,
    private val useCases: NoteUseCases
) : BaseViewModel() {

    private val args = NoteDetailsFragmentArgs.fromSavedStateHandle(savedStateHandle)
    var note = MutableStateFlow<Note?>(null)
        private set

    init {
        getNoteById()
    }

    fun onActionClicked(title: String, content: String) {
        note.value?.copy(
            title = title.trim(),
            content = content.trim()
        )?.let {
            navigateTo(appNavDirections.updateNoteScreen(it))
        }
    }

    private fun getNoteById() {
        viewModelScope.launch {
            note.value = useCases.getNote(args.note.id)
        }
    }
}