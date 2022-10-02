package com.olehvynnytskyi.notes.core.navigation

import androidx.navigation.NavDirections
import com.olehvynnytskyi.notes.AppNavGraphDirections
import com.olehvynnytskyi.notes.domain.model.Note
import com.olehvynnytskyi.notes.presentation.delete_note.DeleteNoteFragmentArgs
import com.olehvynnytskyi.notes.presentation.note_details.NoteDetailsFragmentArgs
import com.olehvynnytskyi.notes.presentation.update_note.UpdateNoteFragmentArgs
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class AppNavDirectionsImpl @Inject constructor() : AppNavDirections {

    override val notesScreen: NavDirections
        get() = AppNavGraphDirections.navigateToNotesScreen()

    override val addNoteScreen: NavDirections
        get() = AppNavGraphDirections.navigateToAddNoteScreen()

    override fun noteDetailsScreen(note: Note): NavDirections =
        AppNavGraphDirections.navigateToNoteDetailsScreen(note)
            .extendWith(NoteDetailsFragmentArgs(note).toBundle())

    override fun deleteNoteScreen(note: Note): NavDirections =
        AppNavGraphDirections.navigateToDeleteNoteScreen(note)
            .extendWith(DeleteNoteFragmentArgs(note).toBundle())

    override fun updateNoteScreen(note: Note): NavDirections =
        AppNavGraphDirections.navigateToUpdateNoteScreen(note)
            .extendWith(UpdateNoteFragmentArgs(note).toBundle())
}

@InstallIn(SingletonComponent::class)
@Module
interface AppNavDirectionsModule {

    @Binds
    fun bindNavDirections(impl: AppNavDirectionsImpl): AppNavDirections
}