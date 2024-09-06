package com.bhatia.notesapp.ui.fragments
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SearchView
import androidx.core.content.ContextCompat
import androidx.core.view.MenuHost
import androidx.core.view.MenuProvider
import androidx.lifecycle.Lifecycle
import androidx.navigation.findNavController
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.bhatia.notesapp.MainActivity
import com.bhatia.notesapp.R
import com.bhatia.notesapp.databinding.FragmentHomeBinding
import com.bhatia.notesapp.ui.adapter.NoteAdapter
import com.bhatia.notesapp.viewmodel.NoteViewModel

class HomeFragment : Fragment(R.layout.fragment_home), SearchView.OnQueryTextListener, MenuProvider {

    private var homeBinding: FragmentHomeBinding? =null
    private  val binding get() = homeBinding!!

     private lateinit var notesViewModel: NoteViewModel
    private lateinit var notesAdapter: NoteAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        homeBinding = FragmentHomeBinding.inflate(inflater, container, false)
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
        setUpHomeRecyclerView()

        binding.addNoteFab.setOnClickListener{
            it.findNavController().navigate(R.id.action_homeFragment_to_addNotesFragment)
        }
    }

    private  fun updateUI(note: List<com.bhatia.notesapp.model.Note>){
        if(note.isEmpty()){
            binding.emptyNotes.visibility = View.VISIBLE
            binding.homeRecyclerView.visibility = View.GONE
        }
        else {
            binding.emptyNotes.visibility = View.GONE
            binding.homeRecyclerView.visibility = View.VISIBLE
        }
    }

    private fun setUpHomeRecyclerView(){
        notesAdapter = NoteAdapter()
        binding.homeRecyclerView.apply {
            layoutManager = StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL)
            setHasFixedSize(true)
            adapter = notesAdapter
        }
        activity?.let {
            notesViewModel.getAllNotes().observe(viewLifecycleOwner){ note ->
                notesAdapter.differ.submitList(note)
                updateUI(note)
            }
        }
    }

    private fun searchNote(query: String?){
        val searchQuery = "%$query"
        notesViewModel.searchNote(searchQuery).observe(this){list ->
            notesAdapter.differ.submitList(list)
        }
    }

    override fun onQueryTextSubmit(query: String?): Boolean {
       return false
    }

    override fun onQueryTextChange(newText: String?): Boolean {
      if(newText != null){
          searchNote(newText)
      }
        return true
    }

    override fun onDestroy() {
        super.onDestroy()
        homeBinding =null
    }
    override fun onCreateMenu(menu: Menu, menuInflater: MenuInflater) {
        menu.clear()
        menuInflater.inflate(R.menu.home_menu, menu)

        val menuSearch = menu.findItem(R.id.searchMenu).actionView as SearchView
        menuSearch.isSubmitButtonEnabled = false
        menuSearch.setOnQueryTextListener(this)
    }

    override fun onMenuItemSelected(menuItem: MenuItem): Boolean {
        TODO("Not yet implemented")
    }
}