package com.olehvynnytskyi.notes.presentation.di

import com.olehvynnytskyi.notes.data.repository.NoteRepositoryImpl
import com.olehvynnytskyi.notes.data.repository.NoteRepositoryModule
import com.olehvynnytskyi.notes.domain.repository.NoteRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.components.SingletonComponent
import dagger.hilt.testing.TestInstallIn
import io.mockk.spyk
import javax.inject.Singleton

@Module
@TestInstallIn(
    components = [SingletonComponent::class],
    replaces = [NoteRepositoryModule::class]
)
class TestNoteRepositoryModule {

    @Provides
    @Singleton
    fun provide(impl: NoteRepositoryImpl): NoteRepository = spyk(impl)
}