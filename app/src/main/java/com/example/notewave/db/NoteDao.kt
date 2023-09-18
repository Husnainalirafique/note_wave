package com.example.notewave.db

import android.accounts.AuthenticatorDescription
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface NoteDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(note: Note)

    @Query("SELECT * FROM notes_table ORDER BY id DESC")
    suspend fun getAllNotes(): List<Note>

    @Query("DELETE FROM NOTES_TABLE")
    suspend fun deleteAllNote()

    @Query("DELETE FROM notes_table WHERE id = :noteId")
    suspend fun deleteById(noteId: Int)

    @Query("UPDATE notes_table SET title = :newTitle, noteDescription = :newDescription WHERE id = :noteId")
    suspend fun updateNote(noteId: Int, newTitle: String, newDescription: String)

}