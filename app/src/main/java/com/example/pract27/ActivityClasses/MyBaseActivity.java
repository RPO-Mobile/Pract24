package com.example.pract27.ActivityClasses;

import androidx.appcompat.app.AppCompatActivity;

public class MyBaseActivity extends AppCompatActivity {
    protected static final int CREATE_ACTION = 0x000312;
    protected static final int EDIT_ACTION = 0x000313;
    protected static final String EXTRA_ID = "id";
    protected static final String EXTRA_ACTION_CODE = "actionCode";
    protected static final String EXTRA_NOTE = "note";
    protected static final String EXTRA_TIME = "time";
    protected static final String DATE_PATTERN = "dd.MM.yyyy HH:mm";
}
