package com.studybuddy;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ListView;

import java.net.URL;
import java.util.ArrayList;

public class SearchActivity extends AppCompatActivity {

    public static ArrayList<User> userList = new ArrayList<User>();

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
        userList.add(new User("John"));

    }

    private void setupList(){
        ListView searchList = (ListView) findViewById(R.id.SearchList);
        UserAdaptor adaptor = new UserAdaptor(getApplicationContext(), R.layout.user_cell, userList);
        searchList.setAdapter(adaptor);


    }

    private void setupOnClickListener(){
        //set up on click listener for each cell
        // get key and search through database for that users info
        // display info in new activity


    }

}