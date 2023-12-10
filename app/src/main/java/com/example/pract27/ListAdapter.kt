package com.example.pract27

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.TextView
import com.example.pract27.databinding.LeElementBinding

class ListAdapter(): BaseAdapter() {

    protected val list = mutableListOf<Note>()
    override fun getCount(): Int = list.count()
    override fun getItem(index: Int) = list[index]
    override fun getItemId(index: Int) = index.toLong()

    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
        val inflater = LayoutInflater.from(parent?.context)
        val listElement = LeElementBinding.inflate(inflater)

        if (list[position].text.length > 18)
            listElement.tvPreview.text = list[position].text.substring(0, 15) + "..."
        else
            listElement.tvPreview.text = list[position].text

        listElement.tvTitle.text = list[position].title
        listElement.tvTime.text = list[position].creationTime.toString()
        return listElement.root
    }

    fun addItem(newNote : Note){
        list.add(newNote)
        notifyDataSetChanged()
    }

    fun setItem(id: Int, newNote: Note){
        list[id] = newNote
        notifyDataSetChanged()
    }
}