package com.olehvynnytskyi.notes.presentation.add_note

import androidx.lifecycle.viewModelScope
import com.olehvynnytskyi.notes.R
import com.olehvynnytskyi.notes.core.ResourceProvider
import com.olehvynnytskyi.notes.core.navigation.AppNavDirections
import com.olehvynnytskyi.notes.domain.model.Note
import com.olehvynnytskyi.notes.domain.use_case.NoteUseCases
import com.olehvynnytskyi.notes.presentation.base.BaseViewModel
import com.olehvynnytskyi.notes.presentation.util.DateFormatter
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.BufferOverflow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.shareIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AddNoteViewModel @Inject constructor(
    private val appNavDirections: AppNavDirections,
    private val dateFormatter: DateFormatter,
    private val noteUseCases: NoteUseCases,
    private val resourceProvider: ResourceProvider
) : BaseViewModel() {

    private val _message = MutableSharedFlow<String>(
        replay = 1,
        onBufferOverflow = BufferOverflow.DROP_OLDEST
    )
    val message = _message.shareIn(viewModelScope, SharingStarted.Lazily)

    fun addNote(title: String, content: String, color: Int) {
        viewModelScope.launch {
            if (title.isBlank()) {
                _message.emit(resourceProvider.getString(R.string.title_note_empty))
                return@launch
            }
            noteUseCases.addNote(
                Note(
                    id = 0,
                    title = title.trim(),
                    content = content.trim(),
                    date = dateFormatter.format(System.currentTimeMillis()),
                    color = color
                )
            )
            navigateTo(appNavDirections.notesScreen)
        }
    }
}