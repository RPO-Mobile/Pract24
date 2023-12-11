package com.example.pract27

import android.os.Build
import android.os.Parcel
import android.os.Parcelable
import androidx.annotation.RequiresApi
import com.google.gson.Gson
import java.time.LocalDateTime

class Note (
    var title: String,
    var text : String,
    val id: Int = -1,
    val creationTime: LocalDateTime = LocalDateTime.now()) : Parcelable {
    @RequiresApi(Build.VERSION_CODES.O)

   private constructor(parcel: Parcel) : this(
        title = parcel.readString() ?: "none",
        text = parcel.readString() ?: "none",
        id = parcel.readInt() ?: -1,
        creationTime = LocalDateTime.parse(parcel.readString()))

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(title)
        parcel.writeString(text)
        parcel.writeInt(id)
        parcel.writeString(creationTime.toString())
    }
    override fun describeContents(): Int = 0
    companion object CREATOR : Parcelable.Creator<Note> {
        override fun createFromParcel(parcel: Parcel): Note {
            return Note(parcel)
        }

        override fun newArray(size: Int): Array<Note?> {
            return arrayOfNulls(size)
        }
        fun empty() = Note("None", "None")
    }
}