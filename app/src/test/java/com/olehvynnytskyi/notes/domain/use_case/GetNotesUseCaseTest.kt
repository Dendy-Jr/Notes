@file:OptIn(ExperimentalCoroutinesApi::class)

package com.olehvynnytskyi.notes.domain.use_case

import com.olehvynnytskyi.notes.domain.model.Note
import com.olehvynnytskyi.notes.domain.use_case.util.NoteRepoMockTest
import com.olehvynnytskyi.notes.domain.util.NoteOrder
import com.olehvynnytskyi.notes.domain.util.OrderType
import io.mockk.coEvery
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Test

class GetNotesUseCaseTest : NoteRepoMockTest() {

    private val getNotesUseCase = GetNotesUseCase(mockNoteRepository)

    @Test
    fun `test get Notes repository return items`() = runTest {
        val notes = listOf(
            Note(
                id = 1,
                title = "title1",
                content = "content2",
                date = "09/28/2022",
                color = 9082196
            ),
            Note(
                id = 2,
                title = "title2",
                content = "content2",
                date = "10/28/2022",
                color = 85448525
            )
        )

        coEvery { mockNoteRepository.getNotes() } returns flowOf(notes)
        val actual = getNotesUseCase.invoke(NoteOrder.Date(OrderType.Ascending)).first()
        assertEquals(mockNoteRepository.getNotes().first(), actual)
    }

    @Test
    fun `test get Notes repository return empty list`() = runTest {
        coEvery { mockNoteRepository.getNotes() } returns flowOf(listOf())
        val actual = getNotesUseCase.invoke(NoteOrder.Date(OrderType.Ascending)).first()
        assertEquals(listOf<Note>(), actual)
    }
}