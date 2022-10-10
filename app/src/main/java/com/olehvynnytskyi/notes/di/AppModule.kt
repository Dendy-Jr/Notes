package com.olehvynnytskyi.notes.di

import android.app.Application
import androidx.room.Room
import com.olehvynnytskyi.notes.data.data_source.NoteDao
import com.olehvynnytskyi.notes.data.data_source.NoteDatabase
import com.olehvynnytskyi.notes.domain.repository.NoteRepository
import com.olehvynnytskyi.notes.domain.use_case.*
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.serialization.json.Json
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class AppModule {

    @Provides
    @Singleton
    fun json(): Json = Json {
        encodeDefaults = true
        ignoreUnknownKeys = true
        isLenient = true
    }

    @Provides
    @Singleton
    fun provideNoteDatabase(app: Application): NoteDatabase {
        return Room.databaseBuilder(
            app, NoteDatabase::class.java, NoteDatabase.DATABASE_NAME
        ).allowMainThreadQueries().build()
    }

    @Provides
    fun provideNoteDao(db: NoteDatabase): NoteDao = db.noteDao

    @Provides
    @Singleton
    fun provideNoteUseCases(repository: NoteRepository): NoteUseCases {
        return NoteUseCases(
            getNotes = GetNotesUseCase(repository),
            getNote = GetNoteUseCase(repository),
            addNote = AddNoteUseCase(repository),
            updateNote = UpdateNoteUseCase(repository),
            searchNotes = SearchNotesUseCase(repository),
            deleteNote = DeleteNoteUseCase(repository)
        )
    }
}