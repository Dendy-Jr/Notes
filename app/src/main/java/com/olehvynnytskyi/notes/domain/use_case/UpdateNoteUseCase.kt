package com.olehvynnytskyi.notes.domain.use_case

import com.olehvynnytskyi.notes.domain.model.Note
import com.olehvynnytskyi.notes.domain.repository.NoteRepository

class UpdateNoteUseCase(
    private val repository: NoteRepository
) {
    suspend operator fun invoke(note: Note) {
        repository.updateNote(note)
    }
}