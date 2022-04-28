package com.example.hw4.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.room.Room
import com.example.hw4.data.Note
import com.example.hw4.databinding.FragmentAddNoteBinding
import com.example.hw4.room.NoteDatabase
import com.example.hw4.validator.DataValidator
import com.example.hw4.validator.Validator

class AddNoteFragment : Fragment() {

    private var _binding: FragmentAddNoteBinding? = null
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

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return FragmentAddNoteBinding.inflate(inflater, container, false)
            .also { _binding = it }
            .root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        with(binding) {
            val dataValidator: Validator = DataValidator()
            buttonAdd.setOnClickListener {
                val title = editTitle.text.toString()
                val description = editDescription.text.toString()
                val date = editDate.text.toString()
                if (dataValidator.isValid(date)) {
                    database.noteDao()
                        .insertAll(Note(title = title, description = description, date = date))
                    editTitle.text = null
                    editDescription.text = null
                    editDate.text = null
                    val successToast = Toast.makeText(
                        requireContext(),
                        "Successfully",
                        Toast.LENGTH_SHORT
                    )
                    successToast.show()
                } else {
                    editDate.text = null
                    val dataToast = Toast.makeText(
                        requireContext(),
                        "Incorrect data, try dd.mm.yyyy",
                        Toast.LENGTH_SHORT
                    )
                    dataToast.show()
                }
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}