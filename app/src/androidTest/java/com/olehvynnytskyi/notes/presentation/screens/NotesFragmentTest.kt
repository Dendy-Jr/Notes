package com.olehvynnytskyi.notes.presentation.screens

import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.withText
import com.olehvynnytskyi.notes.domain.repository.NoteRepository
import com.olehvynnytskyi.notes.presentation.base.BaseTest
import dagger.hilt.android.testing.HiltAndroidTest
import org.junit.Test
import com.olehvynnytskyi.notes.R
import com.olehvynnytskyi.notes.presentation.MockNotesList
import com.olehvynnytskyi.notes.presentation.add_note.AddNoteFragment
import com.olehvynnytskyi.notes.presentation.base.extensions.*
import com.olehvynnytskyi.notes.presentation.delete_note.DeleteNoteFragment
import com.olehvynnytskyi.notes.presentation.note_details.NoteDetailsFragment
import com.olehvynnytskyi.notes.presentation.notes.NotesFragment
import io.mockk.*
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.runBlocking
import org.junit.runner.RunWith
import org.junit.runners.JUnit4
import javax.inject.Inject

@HiltAndroidTest
@RunWith(JUnit4::class)
class NotesFragmentTest : BaseTest() {

    @Inject
    lateinit var repository: NoteRepository

    @Inject
    lateinit var mockNotesList: MockNotesList

    @Test
    fun verifyEverything() = runBlocking {
        launchScreen()

        val notes = repository.getNotes().first()
        val note = notes.random()
        val position = notes.indexOf(note)

        stage("Check header name") {
            assertHasText(viewId = R.id.tvHeader, text = getString(R.string.app_name))
        }

        stage("Click the button, open the sort box and click the sort button") {
            clickOn(R.id.ivFilter)
            assertIsVisible(viewId = R.id.rgNoteOrder, isVisible = true)
            assertIsVisible(viewId = R.id.rgOrderType, isVisible = true)

            clickOn(viewId = R.id.rbTitle)
            clickOn(viewId = R.id.rbAscending)
            clickOn(viewId = R.id.rbDescending)

            clickOn(viewId = R.id.rbDate)
            clickOn(viewId = R.id.rbAscending)
            clickOn(viewId = R.id.rbDescending)

            clickOn(viewId = R.id.rbColor)
            clickOn(viewId = R.id.rbAscending)
            clickOn(viewId = R.id.rbDescending)

            clickOn(R.id.ivFilter)
            assertIsVisible(viewId = R.id.rgNoteOrder, isVisible = false)
            assertIsVisible(viewId = R.id.rgOrderType, isVisible = false)
        }

        stage("Verify item count in recyclerView") {
            assertRecyclerViewItemCount(
                recyclerViewId = R.id.recyclerView,
                itemCount = notes.size
            )
        }

        stage("Tap position and launch screen") {
            coEvery { repository.getNoteById(any()) } returns mockNotesList.notes[position]
            performOnItemAtPosition(
                recyclerViewId = R.id.recyclerView, position = position, action = click()
            )
            assertFragmentIs(NoteDetailsFragment::class)
        }
    }

    @Test
    fun launchAddNoteScreen() = runBlocking {
        launchScreen()
        stage("Click button and launch screen") {
            clickOn(R.id.btnAddNote)
            assertFragmentIs(AddNoteFragment::class)
        }
    }

    @Test
    fun launchDeleteNoteDialog() = runBlocking {
        launchScreen()

        val notes = repository.getNotes().first()
        val note = notes[0]

        stage("Verify items in position") {
            assertOnItemAtPosition(
                recyclerViewId = R.id.recyclerView,
                childId = R.id.tvTitle,
                position = 0,
                assertion = matches(withText(note.title))
            )
            assertOnItemAtPosition(
                recyclerViewId = R.id.recyclerView,
                childId = R.id.tvDate,
                position = 0,
                assertion = matches(withText(note.date))
            )
        }

        stage("Click button, launch dialog and delete note") {
            performOnItemAtPosition(
                recyclerViewId = R.id.recyclerView,
                childId = R.id.ivDelete,
                position = 0,
                action = click()
            )
            assertFragmentIs(DeleteNoteFragment::class)
        }
    }

    private suspend fun launchScreen() {
        every { repository.getNotes() } returns flowOf(mockNotesList.notes)
        startScreen(R.id.notesFragment)
        assertFragmentIs(NotesFragment::class)
    }
}