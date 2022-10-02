@file:OptIn(ExperimentalCoroutinesApi::class)

package com.olehvynnytskyi.notes.domain.use_case

import com.olehvynnytskyi.notes.domain.use_case.util.NoteRepoMockTest
import io.mockk.coEvery
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Test

class GetNoteUseCaseTest : NoteRepoMockTest() {

    @Test
    fun `test repository get note `() = runTest {
        val getNoteUseCase = GetNoteUseCase(mockNoteRepository)
        coEvery { mockNoteRepository.getNoteById(any()) } returns note

        val expected = mockNoteRepository.getNoteById(1)
        val actual = getNoteUseCase.invoke(1)
        assertEquals(expected, actual)
    }
}