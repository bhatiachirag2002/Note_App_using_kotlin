package com.bhatia.notesapp.repo;


import com.bhatia.notesapp.model.Note
import com.bhatia.notesapp.db.NoteDatabase

class NoteRepo(private val db: NoteDatabase) {
    suspend fun addNote(note: Note) = db.getNoteDao().addNote(note)
    suspend fun editNote(note: Note) = db.getNoteDao().editNote(note)
    suspend fun removeNote(note: Note) = db.getNoteDao().removeNote(note)
    fun getAllNotes() = db.getNoteDao().getAllNotes()
    fun searchNote(query: String?) = db.getNoteDao().searchNotes(query)
}
