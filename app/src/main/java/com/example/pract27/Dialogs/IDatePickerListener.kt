package com.example.pract27.Dialogs

import java.time.LocalDate

interface IDatePickerListener {
    fun onDateSelected(selectedDate: LocalDate)
}