package com.example.hw4.room

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.hw4.data.Note

@Database(entities = [Note::class], version = 1)
abstract class NoteDatabase : RoomDatabase() {

    abstract fun noteDao(): NoteDao

}