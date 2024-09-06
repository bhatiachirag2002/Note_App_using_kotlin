package com.bhatia.notesapp.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import androidx.room.Query
import com.bhatia.notesapp.model.Note
import com.bhatia.notesapp.repo.NoteRepo
import kotlinx.coroutines.launch

class NoteViewModel(app: Application, private val noteRepo: NoteRepo): AndroidViewModel(app) {

    fun addNote(note: Note)=
        viewModelScope.launch {
            noteRepo.addNote(note)
        }

    fun editNote(note: Note)=
        viewModelScope.launch {
            noteRepo.editNote(note)
        }

    fun removeNote(note: Note)=
        viewModelScope.launch {
            noteRepo.removeNote(note)
        }
    fun getAllNotes() = noteRepo.getAllNotes()

    fun searchNote(query: String?) =
        noteRepo.searchNote(query)
}