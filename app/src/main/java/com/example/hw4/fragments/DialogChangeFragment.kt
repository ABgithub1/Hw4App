package com.example.hw4.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.DialogFragment
import androidx.navigation.fragment.findNavController
import androidx.room.Room
import com.example.hw4.data.Note

import com.example.hw4.databinding.FragmentDialogChangeBinding
import com.example.hw4.room.NoteDatabase
import com.example.hw4.validator.DataValidator
import com.example.hw4.validator.Validator

class DialogChangeFragment : DialogFragment() {

    private var _binding: FragmentDialogChangeBinding? = null
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
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return FragmentDialogChangeBinding.inflate(inflater, container, false)
            .also { _binding = it }
            .root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        with(binding) {
            val noteId = arguments?.get("idKey")
            val dataValidator: Validator = DataValidator()

            dialogButtonSave.setOnClickListener {
                val title = dialogEditTitle.text.toString()
                val description = dialogEditDescription.text.toString()
                val date = dialogEditDate.text.toString()

                if (dataValidator.isValid(date)) {
                    val noteToDel = database.noteDao().getNoteById(noteId as Long)
                    database.noteDao().delete(noteToDel)
                    database.noteDao()
                        .insertAll(Note(title = title, description = description, date = date))
                    dialogEditTitle.text = null
                    dialogEditDescription.text = null
                    dialogEditDate.text = null
                    val successToast = Toast.makeText(
                        requireContext(),
                        "Successfully",
                        Toast.LENGTH_SHORT
                    )
                    successToast.show()
                    dialog?.dismiss()
                } else {
                    dialogEditDate.text = null
                    val dataToast = Toast.makeText(
                        requireContext(),
                        "Incorrect data, try dd.mm.yyyy",
                        Toast.LENGTH_SHORT
                    )
                    dataToast.show()
                }
            }

            dialogButtonCancel.setOnClickListener {
                dialog?.dismiss()
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}