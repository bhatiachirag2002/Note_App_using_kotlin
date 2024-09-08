package com.bhatia.notesapp.ui.fragments
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.view.MenuHost
import androidx.core.view.MenuProvider
import androidx.fragment.app.Fragment
import androidx.lifecycle.Lifecycle
import androidx.navigation.findNavController
import com.bhatia.notesapp.MainActivity
import com.bhatia.notesapp.R
import com.bhatia.notesapp.databinding.FragmentAddNotesBinding
import com.bhatia.notesapp.model.Note
import com.bhatia.notesapp.viewmodel.NoteViewModel
import android.text.format.DateFormat
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import java.util.Date

class AddNotesFragment : Fragment(R.layout.fragment_add_notes), MenuProvider {

    private var addNotesBinding: FragmentAddNotesBinding? = null
    private val binding get() = addNotesBinding!!

    private lateinit var notesViewModel: NoteViewModel
    private lateinit var addNoteView: View

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        addNotesBinding = FragmentAddNotesBinding.inflate(inflater, container, false)
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
        addNoteView = view

    }

    private fun saveNote(view: View) {
        val title = binding.addNoteTitle.text.toString().trim()
        val description = binding.addNoteDesc.text.toString().trim()
        val d = Date()
        val noteTiming: CharSequence = DateFormat.format("hh:mm a MMMM d, yyyy", d.time).toString().replace("am", "AM").replace("pm", "PM")
        val date: CharSequence = DateFormat.format("hh:mm:ss a MMMM d, yyyy", d.time)

        if (description.isNotEmpty() || title.isNotEmpty()) {
            val note = Note(0, title, description, noteTiming.toString(), date.toString())
            notesViewModel.addNote(note)

            Toast.makeText(addNoteView.context, "Note Added Successfully", Toast.LENGTH_SHORT).show()
            view.findNavController().popBackStack(R.id.homeFragment, false)
        } else {
            view.findNavController().popBackStack(R.id.homeFragment, false)
        }
    }

    override fun onCreateMenu(menu: Menu, menuInflater: MenuInflater) {
        menu.clear()
        menuInflater.inflate(R.menu.save_menu, menu)
    }

    override fun onMenuItemSelected(menuItem: MenuItem): Boolean {
        return when (menuItem.itemId) {
            R.id.saveMenu -> {
                saveNote(addNoteView)
                true
            }
            else -> false
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        addNotesBinding = null
    }
}
