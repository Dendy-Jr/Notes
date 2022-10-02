package com.olehvynnytskyi.notes.presentation.screens

import com.olehvynnytskyi.notes.R
import com.olehvynnytskyi.notes.domain.repository.NoteRepository
import com.olehvynnytskyi.notes.presentation.MockNotesList
import com.olehvynnytskyi.notes.presentation.base.BaseTest
import com.olehvynnytskyi.notes.presentation.base.extensions.assertFragmentIs
import com.olehvynnytskyi.notes.presentation.base.extensions.clickOn
import com.olehvynnytskyi.notes.presentation.base.extensions.replaceText
import com.olehvynnytskyi.notes.presentation.base.extensions.startScreen
import com.olehvynnytskyi.notes.presentation.note_details.NoteDetailsFragment
import com.olehvynnytskyi.notes.presentation.note_details.NoteDetailsFragmentArgs
import com.olehvynnytskyi.notes.presentation.notes.NotesFragment
import com.olehvynnytskyi.notes.presentation.update_note.UpdateNoteFragment
import dagger.hilt.android.testing.HiltAndroidTest
import io.mockk.coEvery
import io.mockk.every
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.runBlocking
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4
import javax.inject.Inject

@HiltAndroidTest
@RunWith(JUnit4::class)
class NoteDetailsFragmentTest : BaseTest() {

    @Inject
    lateinit var repository: NoteRepository

    @Inject
    lateinit var mockNotesList: MockNotesList

    @Test
    fun discardUpdatedNote() = runBlocking {
        launchScreen()

        stage("Update the title, content and don`t save it") {
            updateNote()
            clickOn(viewId = R.id.imageAction, parentId = R.id.toolbar)
            assertFragmentIs(UpdateNoteFragment::class)
            clickOn(R.id.btnDiscard)
            assertFragmentIs(NoteDetailsFragment::class)
        }
    }

    @Test
    fun saveUpdatedNote() = runBlocking {
        launchScreen()

        stage("Update the title, content and save it") {
            updateNote()
            clickOn(viewId = R.id.imageAction, parentId = R.id.toolbar)
            assertFragmentIs(UpdateNoteFragment::class)
            clickOn(R.id.btnUpdate)
            assertFragmentIs(NotesFragment::class)
        }
    }

    private fun updateNote() {
        replaceText(viewId = R.id.etUpdateTitle, text = "Updated title")
        replaceText(viewId = R.id.etUpdateContent, text = "Update text in EditText")
    }

    private suspend fun launchScreen() {
        coEvery { repository.getNoteById(any()) } returns mockNotesList.notes[0]
        every { repository.getNotes() } returns flowOf(listOf(mockNotesList.notes[0])
            .map { note ->
                note.copy(
                    title = "Updated title",
                    content = "Update text in EditText"
                )
            })

        startScreen(
            startDestination = R.id.noteDetailsFragment,
            args = NoteDetailsFragmentArgs(
                repository.getNoteById(1)
            ).toBundle()
        )
        assertFragmentIs(NoteDetailsFragment::class)
    }
}