package com.example.pract27.Database

import android.content.Context
import android.database.sqlite.SQLiteOpenHelper
import android.provider.BaseColumns

object NoteContract {
    object Notes : BaseColumns{
        const val TABLE_NAME = "notes"
        const val COLUMN_TITLE = "title"
        const val COLUMN_TEXT = "text"
        const val COLUMN_TIME = "creationTime"
        const val COLUMN_IMPORTANCE = "importance"
    }

    const val DB_NAME = "notess.db"
    const val DB_VERSION = 3

    const val CREATE_NOTES =
        "CREATE TABLE ${Notes.TABLE_NAME} (" +
                "${BaseColumns._ID} INTEGER PRIMARY KEY," +
                "${Notes.COLUMN_TITLE} TEXT," +
                "${Notes.COLUMN_TEXT} TEXT," +
                "${Notes.COLUMN_TIME} TEXT," +
                "${Notes.COLUMN_IMPORTANCE} INTEGER)"

    const val DELETE_NOTES = "DROP TABLE IF EXISTS ${Notes.TABLE_NAME}"
    const val DELETE_DATABASE = "DROP DATABASE IF EXISTS $DB_NAME"
}