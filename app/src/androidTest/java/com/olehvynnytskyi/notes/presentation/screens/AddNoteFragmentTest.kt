package com.olehvynnytskyi.notes.presentation.screens

import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.typeText
import androidx.test.espresso.matcher.ViewMatchers.withId
import com.olehvynnytskyi.notes.presentation.base.BaseTest
import dagger.hilt.android.testing.HiltAndroidTest
import org.junit.Test
import com.olehvynnytskyi.notes.R
import com.olehvynnytskyi.notes.presentation.add_note.AddNoteFragment
import com.olehvynnytskyi.notes.presentation.base.extensions.assertFragmentIs
import com.olehvynnytskyi.notes.presentation.base.extensions.clickOn
import com.olehvynnytskyi.notes.presentation.base.extensions.startScreen
import com.olehvynnytskyi.notes.presentation.base.extensions.typeText
import com.olehvynnytskyi.notes.presentation.notes.NotesFragment
import kotlinx.coroutines.runBlocking
import org.junit.runner.RunWith
import org.junit.runners.JUnit4

@HiltAndroidTest
@RunWith(JUnit4::class)
class AddNoteFragmentTest : BaseTest() {

    @Test
    fun verifyEverything() = runBlocking {
        //launchFragmentInHiltContainer<AddNoteFragment>()
        startScreen(R.id.addNoteFragment)
        assertFragmentIs(AddNoteFragment::class)

        stage("Click by image and change background color") {
            clickOn(viewId = R.id.ivRedShape)
            clickOn(viewId = R.id.ivBlueShape)
            clickOn(viewId = R.id.ivGreenShape)
            clickOn(viewId = R.id.ivPinkShape)
            clickOn(viewId = R.id.ivYellowShape)
            clickOn(viewId = R.id.ivOrangeShape)
            clickOn(viewId = R.id.ivWhiteShape)
        }

        stage("Fill in the fields and save the note") {
            typeText(viewId = R.id.etAddTitle, text = "Hello World")

            //typeText not work in this case
            onView(withId(R.id.etAddContent)).perform(typeText("Write anything for test"))

            clickOn(viewId = R.id.imageAction, parentId = R.id.toolbar)
            assertFragmentIs(NotesFragment::class)
        }
    }
}