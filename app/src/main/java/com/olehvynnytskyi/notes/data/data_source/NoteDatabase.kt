package com.olehvynnytskyi.notes.data.data_source

import androidx.room.Database
import androidx.room.RoomDatabase
import com.olehvynnytskyi.notes.data.model.NoteEntity

@Database(
    entities = [NoteEntity::class],
    version = 1,
    exportSchema = false
)
abstract class NoteDatabase : RoomDatabase() {

    abstract val noteDao: NoteDao

    companion object {
        const val DATABASE_NAME = "notes_db"
    }
}