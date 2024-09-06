package com.bhatia.notesapp

import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.lifecycle.ViewModelProvider
import com.bhatia.notesapp.db.NoteDatabase
import com.bhatia.notesapp.repo.NoteRepo
import com.bhatia.notesapp.viewmodel.NoteViewModel
import com.bhatia.notesapp.viewmodel.NoteViewModelFactory

class MainActivity : AppCompatActivity() {

    lateinit var noteViewModel: NoteViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        setupVM()

    }
    private fun setupVM(){
        val noteRepo = NoteRepo(NoteDatabase(this))
        val viewModelProviderFactory = NoteViewModelFactory(application, noteRepo)
        noteViewModel = ViewModelProvider(this, viewModelProviderFactory)[NoteViewModel::class.java]
    }
}