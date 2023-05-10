package com.studybuddy;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class Login extends AppCompatActivity {
    boolean validUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        final Button loginButton = findViewById(R.id.btn_login);
        final Button registerButton = findViewById(R.id.btn_login2);

        validUser = false;

        // what happens when the LOGIN button is pressed
        loginButton.setOnClickListener(v -> {
            // sets email and password to the information entered by the user
            String username = ((EditText) findViewById(R.id.et_email)).getText().toString();
            String password = ((EditText) findViewById(R.id.et_password)).getText().toString();

            //checks user details against the database (for now loginDetails.csv)
            authenticateUser(username,password);

            //displays login message
            showLoginMessage(username);

            //resets boolean
            validUser=false;
        });

        // what happens when the REGISTER button is pressed
        registerButton.setOnClickListener(v -> {
            Intent intent = new Intent(Login.this, Register.class);
            startActivity(intent);
        });
    }

    // checks that the information entered by the user matches an instance in the database (loginDetails.csv)
    private void authenticateUser(String username, String password) {
        int id=-1;
        //read assets
        try {
            InputStreamReader r = new InputStreamReader(getAssets().open("loginDetails.csv"),StandardCharsets.UTF_8);
            BufferedReader br = new java.io.BufferedReader(r);
            String line;
            while ((line = br.readLine()) != null) {
                String[] tokens = line.split(",");
                // checks against the username and password being validated, and that these belong to the same user
                if (tokens[1].equals(username) && tokens[2].equals(password)) {
                    // validates uer if true
                    validUser=true;
                    id= Integer.parseInt(tokens[0]);
                }
            }
            br.close();
        }
        catch (IOException e) {
            Log.e("error",e.toString());

        } finally {
            // if user is authenticated, starts a new intent and transfers user data
            if(validUser) {
                User user = new User(id,username);
                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                intent.putExtra("user", user);
                startActivity(intent);
            }
        }
    }

    // message to be displayed after a user attempts login
    private void showLoginMessage(String username) {
        Context context = getApplicationContext();
        String text;
        if (validUser) {text = "Welcome "+username;}
        else {text = "Incorrect login details";}
        int duration = Toast.LENGTH_SHORT;
        Toast toast = Toast.makeText(context, text, duration);
        toast.show();

    }
}
