package com.olehvynnytskyi.notes.presentation.notes

import androidx.lifecycle.viewModelScope
import com.olehvynnytskyi.notes.core.navigation.AppNavDirections
import com.olehvynnytskyi.notes.data.storage.NoteOrderStorage
import com.olehvynnytskyi.notes.domain.model.Note
import com.olehvynnytskyi.notes.domain.use_case.NoteUseCases
import com.olehvynnytskyi.notes.domain.util.DefaultOrder
import com.olehvynnytskyi.notes.domain.util.DefaultType
import com.olehvynnytskyi.notes.domain.util.NoteOrder
import com.olehvynnytskyi.notes.presentation.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class NotesViewModel @Inject constructor(
    private val appNavDirections: AppNavDirections,
    private val noteUseCases: NoteUseCases,
    private val storage: NoteOrderStorage
) : BaseViewModel() {

    private val _state = MutableStateFlow(NotesState())
    val state = _state.asStateFlow()

    private var job: Job? = null
    private var jobSearch: Job? = null

    init {
        viewModelScope.launch {
            getNotes(storage.getOrder() ?: DefaultOrder(DefaultType))
        }
    }

    fun searchNotes(search: String) {
        jobSearch?.cancel()
        jobSearch = viewModelScope.launch {
            if (search.isNotBlank()) {
                noteUseCases.searchNotes(search).collect { notes ->
                    _state.value = state.value.copy(
                        notes = notes,
                    )
                }
            } else {
                getNotes(storage.getOrder() ?: DefaultOrder(DefaultType))
            }
        }
    }

    fun onAddClicked() {
        navigateTo(appNavDirections.addNoteScreen)
    }

    fun onItemClicked(note: Note) {
        navigateTo(appNavDirections.noteDetailsScreen(note))
    }

    fun onDeleteNoteClicked(note: Note) {
        navigateTo(appNavDirections.deleteNoteScreen(note))
    }

    fun onOrder(order: NoteOrder) {
        if (state.value.noteOrder::class == order::class &&
            state.value.noteOrder.orderType::class == order.orderType::class
        ) {
            return
        }
        viewModelScope.launch {
            storage.updateOrder(order)
            getNotes(storage.getOrder() ?: DefaultOrder(DefaultType))
        }
    }

    private fun getNotes(order: NoteOrder) {
        job?.cancel()
        job = viewModelScope.launch {
            noteUseCases.getNotes(order).collect { notes ->
                _state.value = state.value.copy(
                    notes = notes, noteOrder = order
                )
            }
        }
    }
}