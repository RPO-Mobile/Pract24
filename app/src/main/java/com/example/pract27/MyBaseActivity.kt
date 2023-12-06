package com.example.pract27

import androidx.appcompat.app.AppCompatActivity

open class MyBaseActivity : AppCompatActivity() {
    companion object {
        protected val list = mutableListOf<String>()
        protected const val CREATE_ACTION = 0x000312
        protected const val EDIT_ACTION = 0x000313
        protected const val EXTRA_TEXT = "text"
        protected const val EXTRA_ID = "id"
        protected const val EXTRA_ACTION_CODE = "actionCode"
    }
}