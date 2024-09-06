package com.bhatia.notesapp.viewmodel

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.bhatia.notesapp.repo.NoteRepo

class NoteViewModelFactory(val app: Application, private val noteRepo: NoteRepo): ViewModelProvider.Factory {
    override fun <T: ViewModel> create(modeleClass: Class<T>): T{
        return NoteViewModel(app, noteRepo)as T
    }
}