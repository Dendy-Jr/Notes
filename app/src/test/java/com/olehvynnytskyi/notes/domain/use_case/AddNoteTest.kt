@file:OptIn(ExperimentalCoroutinesApi::class)

package com.olehvynnytskyi.notes.domain.use_case

import com.olehvynnytskyi.notes.domain.use_case.util.NoteRepoMockTest
import io.mockk.*
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.Test

class AddNoteTest : NoteRepoMockTest() {

    @Test
    fun `test note repo insert`() = runTest {
        val addNoteUseCase = AddNoteUseCase(mockNoteRepository)
        coEvery { mockNoteRepository.insertNote(note) } just runs
        addNoteUseCase.invoke(note)
    }
}