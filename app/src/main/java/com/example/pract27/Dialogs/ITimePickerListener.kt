package com.example.pract27.Dialogs

import java.time.LocalDateTime
import java.time.LocalTime

interface ITimePickerListener {
    fun onTimeSelected(selectedTime: LocalTime)
}