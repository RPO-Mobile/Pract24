package com.example.pract27

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.TextView
import com.example.pract27.Database.Repositories.INoteRepository
import com.example.pract27.Database.Repositories.SqLiteNoteRepository
import com.example.pract27.databinding.LeElementBinding
import java.time.format.DateTimeFormatter

class ListAdapter(context: Context): BaseAdapter() {
    private val list : MutableList<Note>

    private val noteDb : INoteRepository = SqLiteNoteRepository(context)
    init {
        list = noteDb.getALlNotes()
    }
    override fun getCount(): Int = list.count()
    override fun getItem(index: Int) = list[index]
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
        noteDb.addNote(newNote)
        list.add(newNote)
        notifyDataSetChanged()
    }

    fun setItem(id: Int, newNote: Note){
        noteDb.save(newNote)
        list[id] = newNote
        notifyDataSetChanged()
    }
}