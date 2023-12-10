package com.example.pract27

import android.os.Build
import android.os.Parcel
import android.os.Parcelable
import androidx.annotation.RequiresApi
import com.google.gson.Gson
import java.time.LocalDateTime

class Note (var title: String, var text : String) : Parcelable {
    @RequiresApi(Build.VERSION_CODES.O)
    var creationTime: LocalDateTime = LocalDateTime.now()
        private set

    private constructor(parcel: Parcel) : this(
        title = parcel.readString() ?: "none",
        text = parcel.readString() ?: "none"
    ) {
        creationTime = Gson().fromJson<LocalDateTime>(parcel.readString(), LocalDateTime::class.java)
    }
    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(title)
        parcel.writeString(text)
        parcel.writeString(Gson().toJson(creationTime))
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