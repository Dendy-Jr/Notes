@file:OptIn(ExperimentalCoroutinesApi::class)

package com.olehvynnytskyi.notes.domain.use_case

import com.olehvynnytskyi.notes.domain.use_case.util.NoteRepoMockTest
import io.mockk.coEvery
import io.mockk.just
import io.mockk.runs
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.Test

class UpdateNoteTest: NoteRepoMockTest() {

    @Test
    fun `test repository update note`() = runTest {
        val updateNoteUseCase = UpdateNoteUseCase(mockNoteRepository)
        coEvery { mockNoteRepository.updateNote(note) } just runs
        updateNoteUseCase.invoke(note)
    }
}