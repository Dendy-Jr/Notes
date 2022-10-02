package com.olehvynnytskyi.notes.data.repository

import com.olehvynnytskyi.notes.data.data_source.NoteDao
import com.olehvynnytskyi.notes.data.model.NoteEntity
import com.olehvynnytskyi.notes.domain.model.Note
import com.olehvynnytskyi.notes.domain.repository.NoteRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class NoteRepositoryImpl @Inject constructor(
    private val dao: NoteDao
) : NoteRepository {

    override fun getNotes(): Flow<List<Note>> {
        return dao.getNotes().map {
            it.toDomain()
        }
    }

    override suspend fun getNoteById(id: Int): Note {
        return dao.getNoteById(id).toDomain()
    }

    override suspend fun insertNote(note: Note) {
        dao.insetNote(note.toCache())
    }

    override suspend fun updateNote(note: Note) {
        dao.updateNote(note.toCache())
    }

    override suspend fun deleteNote(note: Note) {
        dao.deleteNote(note.toCache())
    }

    private fun Note.toCache(): NoteEntity {
        return NoteEntity(
            id = id,
            title = title,
            content = content,
            date = date,
            color = color
        )
    }

    private fun NoteEntity.toDomain(): Note {
        return Note(
            id = id,
            title = title,
            content = content,
            date = date,
            color = color
        )
    }

    private fun List<NoteEntity>.toDomain(): List<Note> {
        return map { it.toDomain() }
    }
}

@Module
@InstallIn(SingletonComponent::class)
interface NoteRepositoryModule {

    @Binds
    @Singleton
    fun binds(impl: NoteRepositoryImpl): NoteRepository
}