package com.studybuddy;

import androidx.appcompat.app.AppCompatActivity;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;

public class Login extends AppCompatActivity {
    boolean validUser;

    public static final String CHANNEL_ID = "StudyBuddy";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        // execute this as soon as the app is opened
        createNotificationChannel();

        final Button loginButton = findViewById(R.id.btn_login);
        final Button registerButton = findViewById(R.id.btn_login2);

        validUser = false;

        // what happens when the LOGIN button is pressed
        loginButton.setOnClickListener(v -> {
            // sets email and password to the information entered by the user
            String username = ((EditText) findViewById(R.id.et_username)).getText().toString();
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

    private void createNotificationChannel() {
        CharSequence name = getString(R.string.channel_name);
        String description = getString(R.string.channel_description);
        int importance = NotificationManager.IMPORTANCE_DEFAULT;
        NotificationChannel channel = new NotificationChannel(CHANNEL_ID, name, importance);
        channel.setDescription(description);
        NotificationManager notificationManager = getSystemService(NotificationManager.class);
        notificationManager.createNotificationChannel(channel);
    }
}
