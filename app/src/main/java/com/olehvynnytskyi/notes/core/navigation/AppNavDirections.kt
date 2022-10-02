package com.olehvynnytskyi.notes.core.navigation

import android.os.Bundle
import androidx.navigation.NavDirections
import com.olehvynnytskyi.notes.domain.model.Note

interface AppNavDirections {

    val notesScreen: NavDirections

    val addNoteScreen: NavDirections

    fun noteDetailsScreen(note: Note): NavDirections

    fun deleteNoteScreen(note: Note): NavDirections

    fun updateNoteScreen(note: Note): NavDirections
}

fun NavDirections.extendWith(args: Bundle): NavDirections = object : NavDirections {
    override val actionId: Int = this@extendWith.actionId

    override val arguments: Bundle = args
}