package com.example.notewave.dataBase

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface NoteDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(note: Note)

    @Delete
    suspend fun delete(note: Note)

    @Query("select * from notes_table order by id ASC")
    fun getAllNotes(): List<Note>

    @Query("update notes_table set title = :title, note = :note where id = :id")
    suspend fun update(id: Int?, title: String?, note: String?)
}