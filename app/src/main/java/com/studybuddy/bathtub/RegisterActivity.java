package com.studybuddy.bathtub;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.RadioGroup;
import androidx.appcompat.app.AppCompatActivity;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.studybuddy.R;

/**
 * Register Page where the user can register for a new account.
 * @author Punit (u7432723), Lana (u7103031), Ahmed (u7490701)
 */
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
        final EditText checkPassword = findViewById(R.id.et_repassword);
        final EditText password = findViewById(R.id.et_password);

        radioGroup.setOnCheckedChangeListener((radioGroup1, i) -> {
            if (i == R.id.radio_undergrad) {
                isUndergrad = true;
            } else if (i == R.id.radio_postgrad) {
                isUndergrad = false;
            }
        });

        registerButton2.setOnClickListener(v -> {
            String username = ((EditText) findViewById(R.id.et_username)).getText().toString();
            String password1 = ((EditText) findViewById(R.id.et_password)).getText().toString();
            String password2 = ((EditText) findViewById(R.id.et_repassword)).getText().toString();
            String uidString = ((EditText) findViewById(R.id.et_uid)).getText().toString();

            // Check if any text box is left blank
            if (username.isEmpty()) {
                Toaster.showToast(RegisterActivity.this,"Please fill out username");
            } else if (password1.isEmpty()) {
                Toaster.showToast(RegisterActivity.this,"Please fill out password");
            } else if (password2.isEmpty()) {
                Toaster.showToast(RegisterActivity.this,"Please fill out re-enter password");
            } else if (uidString.isEmpty()) {
                Toaster.showToast(RegisterActivity.this,"Please fill out UID");
            } else if (uidString.length() != 7) {
                    Toaster.showToast(RegisterActivity.this,"UID must be 7 digits");
            } else if (radioGroup.getCheckedRadioButtonId() == -1) {
                Toaster.showToast(RegisterActivity.this,"Please select student type");
            } else {
                // All text boxes are filled, proceed with registration
                Integer uid = Integer.parseInt(uidString);
                if (!password1.equals(password2)) {
                    checkPassword.setError("Passwords do not match");
                } if (password1.length() < 5) {
                    password.setError("Passwords should be 6 characters or greater");
                }
                else {
                    addUserToDatabase(uid, username, password1);
                    user = new User(uid, username);

                    Intent intent = new Intent(RegisterActivity.this, MainActivity.class);
                    intent.putExtra("user", user);
                    startActivity(intent);
                    finish();
                }
            }
        });

        // what happens when the arrow is clicked
        arrow.setOnClickListener(v -> {
            finish();
            //returns to login page
            Intent intent2 = new Intent(RegisterActivity.this, LoginActivity.class);
            startActivity(intent2);
        });
    }

    /**
     * Adds the user to the database, setting other user attributes as null by default
     * @param uid of the user
     * @param username
     * @param password
     * @author Punit (u7432723)
     */
    private void addUserToDatabase(int uid, String username, String password) {
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference usersRef = database.getReference("users");

        User user = new User(uid, username, password, isUndergrad);

        // Write the user to the database
        usersRef.child(String.valueOf(uid)).setValue(user)
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        // User was successfully added to the database
                        Toaster.showToast(RegisterActivity.this, "User registered successfully!");
                    } else {
                        // Something went wrong
                        Toaster.showToast(RegisterActivity.this, "Failed to register user.");
                    }
                });
    }
}
