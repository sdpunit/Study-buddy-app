package com.studybuddy;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;

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
            String username = ((EditText) findViewById(R.id.et_username)).getText().toString();
            String password = ((EditText) findViewById(R.id.et_password)).getText().toString();

            //checks user details against the database (for now loginDetails.csv)
            authenticateUser(username,password);

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
        // Get a reference to the database
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference usersRef = database.getReference("users");

        // Fetch users from the database
        usersRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot userSnapshot : dataSnapshot.getChildren()) {
                    User user = userSnapshot.getValue(User.class);

                    if (user != null && user.getName().equals(username) && user.getPassword().equals(password)) {
                        validUser = true;

                        Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                        intent.putExtra("user", user);
                        startActivity(intent);

                        break;
                    }
                }

                // Show login message after checking all users
                showLoginMessage(username);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Log.w("Firebase", "Failed to read users", databaseError.toException());
            }
        });
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
