package com.olehvynnytskyi.notes.domain.use_case

data class NoteUseCases(
    val addNote: AddNoteUseCase,
    val deleteNote: DeleteNoteUseCase,
    val getNotes: GetNotesUseCase,
    val getNote: GetNoteUseCase,
    val updateNote: UpdateNoteUseCase
)