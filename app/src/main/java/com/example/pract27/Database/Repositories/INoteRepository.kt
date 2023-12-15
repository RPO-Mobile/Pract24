package com.example.pract27.Database.Repositories

import com.example.pract27.Note

interface INoteRepository {
    fun addNote(note: Note) : Long?
    fun save(newNote: Note)
    fun getById(id: Int) : Note
    fun getALlNotes() : MutableList<Note>
    fun delete(noteId: Int)
}
