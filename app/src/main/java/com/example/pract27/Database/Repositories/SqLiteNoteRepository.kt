package com.example.pract27.Database.Repositories

import android.annotation.SuppressLint
import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.provider.BaseColumns
import android.widget.BaseAdapter
import com.example.pract27.Database.NoteContract
import com.example.pract27.Database.NoteDbHelper
import com.example.pract27.Note
import java.time.LocalDateTime

class SqLiteNoteRepository (val context: Context) : INoteRepository {
    private val noteDbHelper = NoteDbHelper(context)
    private var db: SQLiteDatabase? = null

    private fun openWritableDb(){
        db = noteDbHelper.writableDatabase
    }
    private fun openReadableDb(){
        db = noteDbHelper.readableDatabase
    }
    override fun addNote(note: Note) : Long?{
        if (db == null || !db?.isOpen!!){
            openWritableDb()
        }

        val values = ContentValues().apply {
            put(NoteContract.Notes.COLUMN_TITLE, note.title)
            put(NoteContract.Notes.COLUMN_TEXT, note.text)
            put(NoteContract.Notes.COLUMN_TIME, note.creationTime.toString())
        }

        return db?.insert(NoteContract.Notes.TABLE_NAME, null, values)
    }

    override fun save(note: Note) {
        if (db == null || !db?.isOpen!!){
            openWritableDb()
        }

        val values = ContentValues().apply {
            put(NoteContract.Notes.COLUMN_TITLE, note.title)
            put(NoteContract.Notes.COLUMN_TEXT, note.text)
            put(NoteContract.Notes.COLUMN_TIME, note.toString())
        }

        val selection = "${BaseColumns._ID } LIKE ?"
        val selectionArgs = arrayOf("${note.id}")
        db?.update(NoteContract.Notes.TABLE_NAME, values, selection, selectionArgs)
    }

    override fun getById(id: Int): Note {
        if (db == null || !db?.isOpen!!){
            openReadableDb()
        }

        val projection = arrayOf(
            BaseColumns._ID,
            NoteContract.Notes.COLUMN_TITLE,
            NoteContract.Notes.COLUMN_TEXT,
            NoteContract.Notes.COLUMN_TIME)

        val selection = "${BaseColumns._ID} = ?"
        val selectionArgs = arrayOf("$id")
        val sortOrder = "${BaseColumns._ID} DESC"

        val cursor = db?.query(
            NoteContract.Notes.TABLE_NAME,
            projection,
            selection,
            selectionArgs,
            null,
            null,
            sortOrder
        )

        cursor?.use {
            if (it.moveToFirst()){
                return convertToNote(it)
            }
        }

        return Note.empty()
    }

    override fun getALlNotes(): MutableList<Note> {
        if (db == null || !db?.isOpen!!){
            openReadableDb()
        }

        val projection = arrayOf(
            BaseColumns._ID,
            NoteContract.Notes.COLUMN_TITLE,
            NoteContract.Notes.COLUMN_TEXT,
            NoteContract.Notes.COLUMN_TIME
        )
        val sortOrder = "${BaseColumns._ID} DESC"

        val cursor = db?.query(
            NoteContract.Notes.TABLE_NAME,
            projection,
            null,
            null,
            null,
            null,
            sortOrder
        )

        val noteList = mutableListOf<Note>()
        cursor?.use {
            while (it.moveToNext()){
                val newNote = convertToNote(it)
                noteList.add(newNote)
            }
        }

        return noteList
    }

    @SuppressLint("Range")
    private fun convertToNote(cursor: Cursor) : Note{
        val text = cursor.getString(cursor.getColumnIndex(NoteContract.Notes.COLUMN_TITLE))
        val title = cursor.getString(cursor.getColumnIndex(NoteContract.Notes.COLUMN_TITLE))
        val creationTime = cursor.getString(cursor.getColumnIndex(NoteContract.Notes.COLUMN_TIME))
        val id = cursor.getInt(cursor.getColumnIndex(BaseColumns._ID))

        return Note(
            text = text,
            title = title,
            creationTime = LocalDateTime.parse(creationTime),
            id = id
        )
    }
}