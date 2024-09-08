package com.bhatia.notesapp.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.navigation.findNavController
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bhatia.notesapp.databinding.NotesLayoutBinding
import com.bhatia.notesapp.model.Note
import com.bhatia.notesapp.ui.fragments.HomeFragmentDirections.actionHomeFragmentToEditNotesFragment

class NoteAdapter : RecyclerView.Adapter<NoteAdapter.NoteViewHolder>() {
    class NoteViewHolder(val itemBinding: NotesLayoutBinding) : RecyclerView.ViewHolder(itemBinding.root)

    private val differCallback = object : DiffUtil.ItemCallback<Note>() {
        override fun areItemsTheSame(oldItem: Note, newItem: Note): Boolean {
            return oldItem.id == newItem.id &&
                    oldItem.title == newItem.title &&
                    oldItem.description == newItem.description &&
                    oldItem.noteTiming == newItem.noteTiming
        }

        override fun areContentsTheSame(oldItem: Note, newItem: Note): Boolean {
            return oldItem == newItem
        }

    }
    val differ = AsyncListDiffer(this, differCallback)


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NoteViewHolder {
      return NoteViewHolder(
          NotesLayoutBinding.inflate(LayoutInflater.from(parent.context), parent, false)
      )
    }

    override fun getItemCount(): Int {
      return differ.currentList.size
    }

    override fun onBindViewHolder(holder: NoteViewHolder, position: Int) {
      val currentNote = differ.currentList[position]
        holder.itemBinding.noteTitle.text = currentNote.title
        holder.itemBinding.noteDesc.text = currentNote.description
        holder.itemBinding.noteTiming.text = currentNote.noteTiming
        holder.itemView.setOnClickListener{
            val direction = actionHomeFragmentToEditNotesFragment(currentNote)
            it.findNavController().navigate(direction)
        }
    }
}