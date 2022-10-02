package com.olehvynnytskyi.notes.domain.use_case.util

import com.olehvynnytskyi.notes.domain.model.Note
import com.olehvynnytskyi.notes.domain.repository.NoteRepository
import io.mockk.mockk

open class NoteRepoMockTest {

    val mockNoteRepository = mockk<NoteRepository>()
    val note = Note(
        id = 1, title = "title", content = "content", date = "09/28/2022", color = 9082196
    )
}