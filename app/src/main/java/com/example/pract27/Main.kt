package com.example.pract27

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import androidx.activity.result.contract.ActivityResultContracts
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

        adapter = ListAdapter()

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

}

