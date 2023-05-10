package com.studybuddy;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class AssessmentsActivity extends AppCompatActivity {
    private Course course;
    private User user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_assessments);

        TextView txt_courseName = findViewById(R.id.txt_courseName);
        TextView txt_courseCode = findViewById(R.id.txt_courseCode);
        TextView txt_courseType = findViewById(R.id.txt_courseConvenor);

        course = getIntent().getSerializableExtra("course", Course.class);
        user = getIntent().getSerializableExtra("user", User.class);

        txt_courseCode.setText(course.getCourseCode());
        txt_courseName.setText(course.getCourseName());
        txt_courseType.setText(course.getConvener());
    }
    public void clickStartStudy(View view) {
        Intent intent = new Intent(AssessmentsActivity.this, SetTimeActivity.class);
        UserTimeState userTimeState = new UserTimeState(user.getStudyMinutes());
        intent.putExtra("userTimeState", userTimeState);
        intent.putExtra("user", user);
        startActivity(intent);
    }
}