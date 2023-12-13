package com.example.pract27.Dialogs

import android.app.DatePickerDialog
import android.app.DatePickerDialog.OnDateSetListener
import android.app.Dialog
import android.os.Bundle
import android.widget.DatePicker
import androidx.fragment.app.DialogFragment
import java.time.LocalDate
import java.util.Calendar

class DatePickerFragment : DialogFragment(), OnDateSetListener {
    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val calendar = Calendar.getInstance()
        val year = calendar.get(Calendar.YEAR)
        val month = calendar.get(Calendar.MONTH)
        val day = calendar.get(Calendar.DAY_OF_MONTH)

        return DatePickerDialog(
            requireContext(),
            this,
            year,
            month,
            day
        )
    }
    override fun onDateSet(view: DatePicker?, year: Int, month: Int, dayOfMonth: Int) {
        val selectedDate = LocalDate.now()
            .withYear(year)
            .withMonth(month)
            .withDayOfMonth(dayOfMonth)
        (requireActivity() as IDatePickerListener).onDateSelected(selectedDate)
    }
}