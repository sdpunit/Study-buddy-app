package com.studybuddy;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ListView;

import java.net.URL;
import java.util.ArrayList;

public class SearchActivity extends AppCompatActivity {

    public static ArrayList<Person> personList = new ArrayList<Person>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        setupData();
        setupList();
        setupOnClickListener();
    }

    private void setupData(){
        //get singleton data structure
        //get data from database

    }

    private void setupList(){
        PersonAdaptor adaptor = new PersonAdaptor(getApplicationContext(), R.layout.person_cell, personList);
        ListView.setAdapter(adaptor);

    }

    private void setupOnClickListener(){

    }

}