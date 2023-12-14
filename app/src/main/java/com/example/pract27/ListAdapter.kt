package com.example.pract27

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import com.example.pract27.Database.Repositories.INoteRepository
import com.example.pract27.Database.Repositories.SqLiteNoteRepository
import com.example.pract27.databinding.LeElementBinding
import java.time.format.DateTimeFormatter

class ListAdapter(context: Context): BaseAdapter() {
    private var list : MutableList<Note>

    private val noteDb : INoteRepository = SqLiteNoteRepository(context)
    init {
        list = noteDb.getALlNotes()
    }


    override fun getCount(): Int = list.count()
    override fun getItem(index: Int) : Note = list[index]
    override fun getItemId(index: Int) = index.toLong()

    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
        val inflater = LayoutInflater.from(parent?.context)
        val listElement = LeElementBinding.inflate(inflater)

        val note = list[position]
        listElement.tvTitle.text = note.title.toString()

        val text = note.text
        listElement.tvPreview.text =
            if (text.length < 50)
                text
            else
                text.substring(0, 50) + "..."
        listElement.tvTime.text = note.creationTime.format(DateTimeFormatter.ISO_DATE)
        return listElement.root
    }

    fun addItem(newNote : Note){
        newNote.id = noteDb.addNote(newNote)?.toInt() ?: -1
        list = noteDb.getALlNotes()
        notifyDataSetChanged()
    }

    fun setItem(id: Int, newNote: Note){
        noteDb.save(newNote)
        list = noteDb.getALlNotes()
        notifyDataSetChanged()
    }
    fun deleteItem(id: Int){
        noteDb.delete(list[id].id)
        list.removeAt(id)
        notifyDataSetChanged()
    }
    fun sortByAlphabet(){
        list.sortBy { note -> note.title }
        notifyDataSetChanged()
    }
    fun sortByAlphabetDesc(){
        list.sortByDescending { note -> note.title }
        notifyDataSetChanged()
    }
    fun sortByTime(){
        list.sortBy { note -> note.creationTime }
        notifyDataSetChanged()
    }
    fun sortByTimeDesc(){
        list.sortByDescending { note -> note.creationTime }
        notifyDataSetChanged()
    }
}