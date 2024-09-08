package com.bhatia.notesapp.ui.fragments

import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.text.format.DateFormat
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.core.view.MenuHost
import androidx.core.view.MenuProvider
import androidx.lifecycle.Lifecycle
import androidx.navigation.findNavController
import androidx.navigation.fragment.navArgs
import com.bhatia.notesapp.MainActivity
import com.bhatia.notesapp.R
import com.bhatia.notesapp.databinding.FragmentEditNotesBinding
import com.bhatia.notesapp.model.Note
import com.bhatia.notesapp.viewmodel.NoteViewModel
import java.util.Date


class EditNotesFragment : Fragment(R.layout.fragment_edit_notes),MenuProvider {

    private var editNotesBinding: FragmentEditNotesBinding? =null
    private val binding get() = editNotesBinding!!

    private lateinit var notesViewModel: NoteViewModel
    private lateinit var currentNote: Note

    private val args: EditNotesFragmentArgs by navArgs()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        editNotesBinding = FragmentEditNotesBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        (activity as AppCompatActivity).supportActionBar?.setBackgroundDrawable(
            ColorDrawable(ContextCompat.getColor(requireContext(), R.color.yellow))
        )
        val menuHost: MenuHost = requireActivity()
        menuHost.addMenuProvider(this, viewLifecycleOwner, Lifecycle.State.RESUMED)
        notesViewModel = (activity as MainActivity).noteViewModel
        currentNote = args.note!!

        binding.editNoteTitle.setText(currentNote.title)
        binding.editNoteDesc.setText(currentNote.description)

        binding.editNoteFab.setOnClickListener{
            val title = binding.editNoteTitle.text.toString().trim()
            val description = binding.editNoteDesc.text.toString().trim()
            val d = Date()
            val noteTiming: CharSequence = DateFormat.format("hh:mm a MMMM d, yyyy", d.time).toString().replace("am", "AM").replace("pm", "PM")
            val date: CharSequence = DateFormat.format("hh:mm:ss a MMMM d, yyyy", d.time)

            if (description.isNotEmpty() || title.isNotEmpty()) {
                val note = Note(currentNote.id, title, description,noteTiming.toString(), date.toString())
                notesViewModel.editNote(note)
                Toast.makeText(context, "Note Added Successfully", Toast.LENGTH_SHORT).show()
                view.findNavController().popBackStack(R.id.homeFragment, false)
            } else {
                view.findNavController().popBackStack(R.id.homeFragment, false)
            }
        }
    }

    private fun removeNote(){
        AlertDialog.Builder(requireContext()).apply {
            setTitle("Delete Note")
            setMessage("Do you want to delete this note?")
            setPositiveButton("Delete"){ _, _ ->
                notesViewModel.removeNote(currentNote)
                Toast.makeText(context, "Note Deleted Successfully", Toast.LENGTH_SHORT).show()
                view?.findNavController()?.popBackStack(R.id.homeFragment, false)
            }
            setNegativeButton("Cancel", null)
        }.create().show()
    }


    override fun onCreateMenu(menu: Menu, menuInflater: MenuInflater) {
        menu.clear()
        menuInflater.inflate(R.menu.edit_menu, menu)
    }

    override fun onMenuItemSelected(menuItem: MenuItem): Boolean {
        return when(menuItem.itemId){
            R.id.deleteMenu->{
                removeNote()
                true
            }else -> false
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        editNotesBinding = null
    }

}