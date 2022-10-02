@file:OptIn(ExperimentalCoroutinesApi::class)

package com.olehvynnytskyi.notes.domain.use_case

import com.olehvynnytskyi.notes.domain.use_case.util.NoteRepoMockTest
import io.mockk.coEvery
import io.mockk.just
import io.mockk.runs
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.Test

class DeleteNoteUseCaseTest : NoteRepoMockTest() {

    @Test
    fun `test repository delete note`() = runTest {
        val deleteNoteUseCase = DeleteNoteUseCase(mockNoteRepository)
        coEvery { mockNoteRepository.deleteNote(note) } just runs
        deleteNoteUseCase.invoke(note)
    }
}