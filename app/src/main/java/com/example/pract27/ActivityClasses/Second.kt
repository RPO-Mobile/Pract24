package com.example.pract27.ActivityClasses

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import com.example.pract27.Note
import com.example.pract27.databinding.SecondActBinding

class Second : MyBaseActivity() {
    lateinit var secondAct: SecondActBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        secondAct = SecondActBinding.inflate(layoutInflater)
        super.onCreate(savedInstanceState)
        setContentView(secondAct.root)

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
        val note = intent.getParcelableExtra<Note>(EXTRA_NOTE) ?: Note.empty()
        secondAct.etTitle.setText(note.title)
        secondAct.etText.setText(note.text)
    }

    private fun closeActivity(resultCode: Int){
        val data = intent
        val etTitle = secondAct.etTitle
        val etText = secondAct.etText
        val intent = Intent()
        intent.putExtra(EXTRA_ACTION_CODE, data.getIntExtra(EXTRA_ACTION_CODE, CREATE_ACTION))
        intent.putExtra(EXTRA_NOTE, Note(etTitle.text.toString(), etText.text.toString()))
        intent.putExtra(EXTRA_ID, data.getIntExtra(EXTRA_ID, 0))
        setResult(resultCode, intent)
        finish()
    }
}