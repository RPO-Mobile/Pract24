package com.example.pract27.ActivityClasses

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import com.example.pract27.Dialogs.DatePickerFragment
import com.example.pract27.Dialogs.IDatePickerListener
import com.example.pract27.Dialogs.ITimePickerListener
import com.example.pract27.Dialogs.TimePickerFragment
import com.example.pract27.Note
import com.example.pract27.databinding.SecondActBinding
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.LocalTime
import java.time.format.DateTimeFormatter

class Second : MyBaseActivity(), ITimePickerListener, IDatePickerListener {
    lateinit var secondAct: SecondActBinding
    private lateinit var note : Note

    override fun onCreate(savedInstanceState: Bundle?) {
        secondAct = SecondActBinding.inflate(layoutInflater)
        super.onCreate(savedInstanceState)
        setContentView(secondAct.root)
        note = intent.getParcelableExtra<Note>(EXTRA_NOTE) ?: Note.empty()

        secondAct.setTime.setOnClickListener {
            val timePickerFragment = TimePickerFragment()
            val datePickerFragment = DatePickerFragment()
            timePickerFragment.show(supportFragmentManager, "timePicker")
            datePickerFragment.show(supportFragmentManager, "datePicker")
        }
        secondAct.btnOk.setOnClickListener {
            closeActivity(Activity.RESULT_OK)
        }
        secondAct.btnCancel.setOnClickListener {
            closeActivity(Activity.RESULT_CANCELED)
        }

        val textWatcher = object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {
                val hasText = !secondAct.etText.text.isNullOrBlank()
                val hasTitle = !secondAct.etTitle.text.isNullOrBlank();
                secondAct.btnOk.isEnabled = hasTitle && hasText
            }
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}
        }
        secondAct.etTitle.addTextChangedListener(textWatcher)
        secondAct.etText.addTextChangedListener(textWatcher)

        val action = intent.getIntExtra(EXTRA_ACTION_CODE, CREATE_ACTION)
        secondAct.etTitle.setText(note.title)
        secondAct.etText.setText(note.text)
        secondAct.tvTime.text = note.creationTime.format(DateTimeFormatter.ofPattern(DATE_PATTERN))
    }

    private fun closeActivity(resultCode: Int){
        val data = intent
        val title = secondAct.etTitle.text.toString()
        val text = secondAct.etText.text.toString()
        val dateTime = LocalDateTime.parse(
            secondAct.tvTime.text,
            DateTimeFormatter.ofPattern(DATE_PATTERN))
        val intent = Intent()
        intent.putExtra(EXTRA_ACTION_CODE, data.getIntExtra(EXTRA_ACTION_CODE, CREATE_ACTION))
        intent.putExtra(EXTRA_NOTE, Note(title, text, note.id, dateTime))
        intent.putExtra(EXTRA_ID, data.getIntExtra(EXTRA_ID, 0))
        setResult(resultCode, intent)
        finish()
    }
    override fun onTimeSelected(selectedTime: LocalTime) {
        val dateTime = LocalDateTime.parse(
            secondAct.tvTime.text,
            DateTimeFormatter.ofPattern(DATE_PATTERN))
        val newDateTime = dateTime.toLocalDate().atTime(selectedTime)
        secondAct.tvTime.text = newDateTime.format(DateTimeFormatter.ofPattern(DATE_PATTERN))
        note.creationTime = newDateTime
    }
    override fun onDateSelected(selectedDate: LocalDate) {
        val dateTime = LocalDateTime.parse(
            secondAct.tvTime.text,
            DateTimeFormatter.ofPattern(DATE_PATTERN))
        val newDateTime = dateTime.toLocalTime().atDate(selectedDate)
        secondAct.tvTime.text = newDateTime.format(DateTimeFormatter.ofPattern(DATE_PATTERN))
        note.creationTime = newDateTime
    }
}