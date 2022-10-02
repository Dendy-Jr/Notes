package com.olehvynnytskyi.notes.presentation.notes

import com.olehvynnytskyi.notes.domain.model.Note
import com.olehvynnytskyi.notes.domain.util.NoteOrder
import com.olehvynnytskyi.notes.domain.util.OrderType

data class NotesState(
    val notes: List<Note> = emptyList(),
    val noteOrder: NoteOrder = NoteOrder.Date(OrderType.Descending),
)