package com.olehvynnytskyi.notes.domain.use_case

import com.olehvynnytskyi.notes.domain.model.Note
import com.olehvynnytskyi.notes.domain.repository.NoteRepository
import kotlinx.coroutines.flow.Flow

class SearchNotesUseCase(
    private val repository: NoteRepository
) {

    operator fun invoke(search: String): Flow<List<Note>> = repository.searchNotes(search)
}