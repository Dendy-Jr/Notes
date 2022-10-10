package com.olehvynnytskyi.notes.domain.repository

import com.olehvynnytskyi.notes.domain.model.Note
import kotlinx.coroutines.flow.Flow

interface NoteRepository {

    fun getNotes(): Flow<List<Note>>

    fun searchNotes(search: String): Flow<List<Note>>

    suspend fun getNoteById(id: Int): Note

    suspend fun insertNote(note: Note)

    suspend fun updateNote(note: Note)

    suspend fun deleteNote(note: Note)
}