package com.olehvynnytskyi.notes.presentation.notes

import androidx.lifecycle.viewModelScope
import com.olehvynnytskyi.notes.core.navigation.AppNavDirections
import com.olehvynnytskyi.notes.domain.model.Note
import com.olehvynnytskyi.notes.domain.use_case.NoteUseCases
import com.olehvynnytskyi.notes.domain.util.NoteOrder
import com.olehvynnytskyi.notes.domain.util.OrderType
import com.olehvynnytskyi.notes.presentation.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

@HiltViewModel
class NotesViewModel @Inject constructor(
    private val appNavDirections: AppNavDirections,
    private val noteUseCases: NoteUseCases
) : BaseViewModel() {

    private val _state = MutableStateFlow(NotesState())
    val state = _state.asStateFlow()

    private var job: Job? = null

    init {
        getNotes(NoteOrder.Date(OrderType.Descending))
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
        getNotes(order)
    }

    private fun getNotes(noteOrder: NoteOrder) {
        job?.cancel()
        job = noteUseCases.getNotes(noteOrder).onEach { notes ->
            _state.value = state.value.copy(
                notes = notes,
                noteOrder = noteOrder
            )
        }.launchIn(viewModelScope)
    }
}