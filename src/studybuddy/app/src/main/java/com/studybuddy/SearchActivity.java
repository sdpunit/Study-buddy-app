package com.studybuddy;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

// have an xml setup with a collections of names which updates whenever a new user is added
// auto update to display names when input is typed in. once 
public class SearchActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
    }

}