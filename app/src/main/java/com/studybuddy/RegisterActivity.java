package com.studybuddy;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;


public class RegisterActivity extends AppCompatActivity {

    private User user;
    private boolean isUndergrad;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        final Button registerButton2 = findViewById(R.id.btn_register);
        final ImageButton arrow = findViewById(R.id.arrow);
        RadioGroup radioGroup = findViewById(R.id.radio_group);

        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                if (i == R.id.radio_undergrad) {
                    isUndergrad = true;
                } else if (i == R.id.radio_postgrad) {
                    isUndergrad = false;
                }
            }
        });

        registerButton2.setOnClickListener(v -> {
            String username = ((EditText) findViewById(R.id.et_username)).getText().toString();
            String password = ((EditText) findViewById(R.id.et_password)).getText().toString();
            Integer uid = Integer.parseInt(((EditText) findViewById(R.id.et_uid)).getText().toString());

            addUserToDatabase(uid, username, password);
            user = new User(uid, username);

            Intent intent = new Intent(RegisterActivity.this, MainActivity.class);
            intent.putExtra("user", user);
            startActivity(intent);
            finish();
        });

        // what happens when the arrow is clicked
        arrow.setOnClickListener(v -> {
            finish();
            //returns to login page
            Intent intent2 = new Intent(RegisterActivity.this, LoginActivity.class);
            startActivity(intent2);
        });
    }

    private void addUserToDatabase(int uid, String username, String password) {
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference usersRef = database.getReference("users");
        //String userId = usersRef.push().getKey();

        User user = new User(uid, username, password, isUndergrad);

        // Write the user to the database
        usersRef.child(String.valueOf(uid)).setValue(user)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()) {
                            // User was successfully added to the database
                            Toast.makeText(RegisterActivity.this, "User registered successfully!", Toast.LENGTH_SHORT).show();
                        } else {
                            // Something went wrong
                            Toast.makeText(RegisterActivity.this, "Failed to register user.", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }
}
