package com.example.hw4.data

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Note(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    val title: String?, // заголовок заметки
    val description: String?,  // описание заметки
    val date: String? // до какого сделать
)
