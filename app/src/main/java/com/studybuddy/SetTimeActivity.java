package com.studybuddy;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class SetTimeActivity extends AppCompatActivity {
    private User user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_set_time);

        user = (User) getIntent().getSerializableExtra("user", User.class);
    }

    public void clickStart(View view) {
        EditText studyTime = (EditText) findViewById(R.id.studyTimeText);
        String studyTimeString = studyTime.getText().toString();
        // deal with invalid input
        if (!isInteger(studyTimeString)) {
            Toast.makeText(getApplicationContext(), "Please enter a valid integer minute", Toast.LENGTH_SHORT).show();
        }
        else {
            int minutes = Integer.parseInt(studyTimeString);
            Intent intent = new Intent(getApplicationContext(), StudyActivity.class);
            intent.putExtra("user", user);
            intent.putExtra("minutes", minutes);
            startActivity(intent);
        }
    }

    public boolean isInteger(String s) {
        try {
            Integer.parseInt(s);
        } catch(NumberFormatException e) {
            return false;
        }
        return true;
    }
}