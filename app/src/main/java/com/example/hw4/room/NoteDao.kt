package com.example.hw4.room

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import com.example.hw4.data.Note

@Dao
interface NoteDao {

    @Query("SELECT * FROM note")
    fun getAll(): List<Note>

    @Query("SELECT * FROM note WHERE id IN (:noteId)")
    fun loadAllByIds(noteId: IntArray): List<Note>

    @Query("SELECT * FROM note WHERE id LIKE :id") ///
    fun getNoteById(id: Long): Note

    @Query("SELECT * FROM note WHERE title LIKE :title")
    fun findByTitle(title: String): Note

    @Insert
    fun insertAll(note: Note)

    @Delete
    fun delete(note: Note)

}