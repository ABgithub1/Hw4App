package com.example.hw4.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.room.Room
import com.example.hw4.adapter.NoteListAdapter
import com.example.hw4.data.Note
import com.example.hw4.databinding.FragmentNoteBinding
import com.example.hw4.extentions.SwipeToDelCallback
import com.example.hw4.extentions.addSpaceDecoration
import com.example.hw4.room.NoteDatabase

class NoteFragment : Fragment() {

    private var _binding: FragmentNoteBinding? = null
    private val binding get() = requireNotNull(_binding)

    private val database: NoteDatabase by lazy {
        Room.databaseBuilder(
            requireContext().applicationContext,
            NoteDatabase::class.java,
            "notes-db"
        )
            .allowMainThreadQueries()
            .build()
    }

    private val adapter by lazy {
        NoteListAdapter() {
            findNavController().navigate(
                NoteFragmentDirections.actionNoteFragmentToDetailsFragment(
                    it.title.toString(),
                    it.description.toString(),
                    it.date.toString(),
                    it.id
                )
            )
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return FragmentNoteBinding.inflate(inflater, container, false)
            .also { _binding = it }
            .root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        with(binding) {

            val swipeToDelCallback = object : SwipeToDelCallback() {
                override fun onMove(
                    recyclerView: RecyclerView,
                    viewHolder: RecyclerView.ViewHolder,
                    target: RecyclerView.ViewHolder
                ): Boolean {
                    return false
                }

                override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                    val noteToDel = getListFromDatabase()[viewHolder.adapterPosition]
                    database.noteDao().delete(noteToDel)
                    adapter.submitList(getListFromDatabase())
                }
            }
            ItemTouchHelper(swipeToDelCallback).attachToRecyclerView(recyclerview)
            adapter.submitList(getListFromDatabase())
            recyclerview.layoutManager = LinearLayoutManager(requireContext())
            recyclerview.addSpaceDecoration(2)
            recyclerview.adapter = adapter

        }
    }

    private fun getListFromDatabase(): List<Note> {
        return database.noteDao().getAll()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
