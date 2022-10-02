package com.olehvynnytskyi.notes.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.olehvynnytskyi.notes.data.model.NoteEntity.Companion.TABLE_NAME

@Entity(tableName = TABLE_NAME)
data class NoteEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Int,
    val title: String,
    val content: String,
    val date: String,
    val color: Int
) {
    companion object {
        const val TABLE_NAME = "note_table"
    }
}