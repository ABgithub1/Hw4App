package com.example.hw4.adapter

import androidx.recyclerview.widget.RecyclerView
import com.example.hw4.data.Note
import com.example.hw4.databinding.ItemBinding

class NoteViewHolder(private val binding: ItemBinding, private val itemClick: (Note) -> Unit) :
    RecyclerView.ViewHolder(binding.root) {

    fun bind(item: Note) {
        with(binding) {
            root.setOnClickListener {
                itemClick(item)
            }
            title.text = item.title
            date.text = item.date
        }
    }

}