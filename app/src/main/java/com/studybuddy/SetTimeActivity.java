package com.studybuddy;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.studybuddy.timer.UserTimeState;

public class SetTimeActivity extends AppCompatActivity {
    private UserTimeState userTimeState;
    private User user;

    private Course course;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_set_time);

        user = getIntent().getSerializableExtra("user", User.class);
        userTimeState = getIntent().getSerializableExtra("userTimeState", UserTimeState.class);
        course = getIntent().getSerializableExtra("course", Course.class);
    }

    /**
     * This method is called when the user clicks the start button to start a countdown.
     */
    public void clickStart(View view) {
        EditText studyTime = (EditText) findViewById(R.id.studyTimeText);
        String studyTimeString = studyTime.getText().toString();
        // deal with invalid input
        if (!isInteger(studyTimeString)) {
            Toast.makeText(getApplicationContext(), "Please enter a valid integer minute", Toast.LENGTH_SHORT).show();
        }
        else {
            int minutes = Integer.parseInt(studyTimeString);
            Intent intent = new Intent(getApplicationContext(), TimerActivity.class);
            intent.putExtra("userTimeState", userTimeState);
            intent.putExtra("user", user);
            intent.putExtra("minutes", minutes);
            intent.putExtra("course", course);

            startActivity(intent);
            finish();
        }
    }

    /**
     * Check if the input string is an integer. This is used to handle invalid study time input.
     */
    public boolean isInteger(String s) {
        try {
            Integer.parseInt(s);
        } catch(NumberFormatException e) {
            return false;
        }
        return true;
    }
}