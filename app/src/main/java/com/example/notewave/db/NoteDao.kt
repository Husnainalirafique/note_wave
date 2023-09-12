package com.example.notewave.db

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface NoteDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(note: Note)

    @Query("select * from notes_table order by id ASC")
    suspend fun getAllNotes(): List<Note>

    @Query("DELETE FROM NOTES_TABLE")
    suspend fun deleteNote()
}