package com.olehvynnytskyi.notes.domain.use_case

import com.olehvynnytskyi.notes.domain.model.Note
import com.olehvynnytskyi.notes.domain.repository.NoteRepository

class GetNoteUseCase(
    private val repository: NoteRepository
) {

    suspend operator fun invoke(id: Int): Note? {
        return repository.getNoteById(id)
    }
}