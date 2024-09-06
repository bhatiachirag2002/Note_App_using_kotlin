package com.bhatia.notesapp.db

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.bhatia.notesapp.model.Note

@Dao
interface NoteDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addNote(note: Note)

    @Update
    suspend fun editNote(note: Note)

    @Delete
    suspend fun removeNote(note: Note)

    @Query("SELECT * FROM NOTES ORDER BY date DESC")
    fun getAllNotes(): LiveData<List<Note>>

    @Query("SELECT * FROM NOTES WHERE Title LIKE :query OR Description LIKE :query")
    fun searchNotes(query: String?): LiveData<List<Note>>
}