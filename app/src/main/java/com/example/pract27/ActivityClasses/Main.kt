package com.example.pract27.ActivityClasses

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import androidx.activity.result.contract.ActivityResultContracts
import com.example.pract27.ListAdapter
import com.example.pract27.Note
import com.example.pract27.R
import com.example.pract27.databinding.MainActBinding

class Main : MyBaseActivity() {
    private lateinit var adapter: ListAdapter
    private lateinit var mainAct: MainActBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        mainAct = MainActBinding.inflate(layoutInflater)
        super.onCreate(savedInstanceState)
        setContentView(mainAct.root)

        mainAct.btnAdd.setOnClickListener {
            callSecondForm(-1, CREATE_ACTION)
        }

        spinnerSetup()

        adapter = ListAdapter(baseContext)
        mainAct.lv.adapter = adapter
        mainAct.lv.setOnItemClickListener { _, _, position, _ ->
            callSecondForm(position, EDIT_ACTION)
        }
    }

    fun callSecondForm(position: Int, action: Int) {
        val intent = Intent(this, Second::class.java)
        if (action == EDIT_ACTION) {
                intent.putExtra(EXTRA_NOTE, adapter.getItem(position))
                intent.putExtra(EXTRA_ID, position)
            }
        intent.putExtra(EXTRA_ACTION_CODE, action)
        secondActResultLauncher.launch(intent)
    }

    private val secondActResultLauncher =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            if (result.resultCode == Activity.RESULT_OK) {
                val data: Intent? = result.data
                if (data != null) {
                    val id: Int = data.getIntExtra(EXTRA_ID, 1)
                    val note = data.getParcelableExtra<Note>(EXTRA_NOTE) ?: Note.empty()
                    val actionCode = data.getIntExtra(EXTRA_ACTION_CODE, CREATE_ACTION)
                    when (actionCode){
                        CREATE_ACTION -> adapter.addItem(note)
                        EDIT_ACTION -> adapter.setItem(id, note)
                    }
                }
            }
        }

    private fun spinnerSetup(){
        ArrayAdapter.createFromResource(
            this,
            R.array.sort_array,
            R.layout.sp_item
        ).also {
            it.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            mainAct.spSort.adapter = it
        }

        mainAct.spSort.onItemSelectedListener = object : AdapterView.OnItemSelectedListener{
            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {
                when(id)  {
                    0L -> adapter.sortByAplphabet()
                    1L -> adapter.sortByAplphabetDesc()
                    2L -> adapter.sortByTime()
                    3L -> adapter.sortByTimeDesc()
                }

            }

            override fun onNothingSelected(parent: AdapterView<*>?) {
                TODO("Not yet implemented")
            }

        }

    }
}

