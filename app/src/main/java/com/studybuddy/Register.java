package com.studybuddy;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;

import androidx.appcompat.app.AppCompatActivity;

import java.io.BufferedWriter;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.nio.charset.StandardCharsets;

public class Register extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        final Button registerButton2 = findViewById(R.id.btn_register);
        final ImageButton arrow = findViewById(R.id.arrow);

        registerButton2.setOnClickListener(v -> {

            String username = ((EditText) findViewById(R.id.et_username)).getText().toString();
            String password = ((EditText) findViewById(R.id.et_password)).getText().toString();

            addUserToDatabase(username, password);

            User user = new User(1,username);
            Intent intent = new Intent(getApplicationContext(), MainActivity.class);
            intent.putExtra("user", user);
            startActivity(intent);
        });

        // what happens when the arrow is clicked
        arrow.setOnClickListener(v -> {
            finish();
            //returns to login page
            Intent intent2 = new Intent(Register.this, Login.class);
            startActivity(intent2);
        });
    }

    private void addUserToDatabase(String username, String password) {
        String fileName = "loginDetails.csv";
        int constantId = 1; // Just for the moment it is constant; needs to be changed

        try (FileOutputStream fos = openFileOutput(fileName, Context.MODE_APPEND);
             OutputStreamWriter osw = new OutputStreamWriter(fos, StandardCharsets.UTF_8);
             BufferedWriter bw = new BufferedWriter(osw)) {
            String csvLine = constantId + "," + username + "," + password + "\n";
            bw.write(csvLine);
            bw.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}