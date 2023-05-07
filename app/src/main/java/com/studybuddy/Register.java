package com.studybuddy;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;

import androidx.appcompat.app.AppCompatActivity;

import java.io.FileOutputStream;
import java.io.IOException;

public class Register extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        final Button registerButton2 = findViewById(R.id.btn_register);
        final ImageButton arrow = findViewById(R.id.arrow);

        registerButton2.setOnClickListener(v -> {
            //TODO: add user to database
//          String name = ((EditText) findViewById(R.id.et_name)).getText().toString();
//          String username = ((EditText) findViewById(R.id.et_email)).getText().toString();
//          String password = ((EditText) findViewById(R.id.et_password)).getText().toString();
            finish();
            Intent intent2 = new Intent(Register.this, MainActivity.class);
            startActivity(intent2);
        });

        // what happens when the arrow is clicked
        arrow.setOnClickListener(v -> {
            finish();
            //returns to login page
            Intent intent2 = new Intent(Register.this, Login.class);
            startActivity(intent2);
        });
    }
}