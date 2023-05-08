package com.studybuddy;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

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
        int minutes = Integer.parseInt(studyTime.getText().toString());
        Intent intent = new Intent(getApplicationContext(), StudyActivity.class);
        intent.putExtra("user", user);
        intent.putExtra("minutes", minutes);
        startActivity(intent);
    }
}