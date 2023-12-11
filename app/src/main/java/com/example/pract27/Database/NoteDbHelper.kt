package com.example.pract27.Database

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

class NoteDbHelper(context: Context) : SQLiteOpenHelper(context, NoteContract.DB_NAME,
    null, NoteContract.DB_VERSION) {
    override fun onCreate(db: SQLiteDatabase?) {
        db?.execSQL(NoteContract.CREATE_NOTES)
    }
    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        db?.execSQL(NoteContract.DELETE_NOTES)
        onCreate(db)
    }
}