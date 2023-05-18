package com.studybuddy.bathtub;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.TypedValue;
import android.view.View;
import android.widget.TextView;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.studybuddy.R;
import com.studybuddy.timer.UserTimeState;

import java.util.ArrayList;
import java.util.List;

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
        TextView txt_assessments = findViewById(R.id.txt_courseAssessments);

        course = getIntent().getSerializableExtra("course", Course.class);
        user = getIntent().getSerializableExtra("user", User.class);

        txt_courseCode.setText(course.getCourseCode());
        txt_courseName.setText(course.getCourseName());
        txt_courseType.setText(course.getConvener());

        List<String> assessments = course.getAssessment();
        if (assessments !=null && !assessments.isEmpty()){
            StringBuilder stringBuilder = new StringBuilder();
            for (String assessment : assessments){
                stringBuilder.append("\u2022 ");
                stringBuilder.append(assessment);
                stringBuilder.append("\n\n");
            }
            txt_assessments.setText(stringBuilder);
        } else {
            txt_assessments.setText("No assessments information available yet, please contact your convenor.");
        }
    }
    public void clickStartStudy(View view) {
        Intent intent = new Intent(AssessmentsActivity.this, SetTimeActivity.class);
        UserTimeState userTimeState = new UserTimeState(user.getStudyMinutes());
        intent.putExtra("userTimeState", userTimeState);
        intent.putExtra("user", user);
        intent.putExtra("course", course);
        startActivity(intent);
        finish();
    }
    public void clickResetTime(View view){
        AlertDialog.Builder builder = new AlertDialog.Builder(AssessmentsActivity.this);
        builder.setTitle("Reset Time");
        builder.setMessage("Are you sure you want to reset the time for this course?")
                .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        // Continue with reset
                        // Set the course time to 0
                        user.getCourseTime().put(course.getCourseCode(), 0.0);

                        DatabaseReference userRef = FirebaseDatabase.getInstance().getReference("users").child(String.valueOf(user.getUid()));
                        // Update the user data on Firebase
                        userRef.setValue(user);

                        Intent intent = new Intent(AssessmentsActivity.this, MainActivity.class);
                        intent.putExtra("user", user);
                        startActivity(intent);
                        finish();
                    }
                })
                .setNegativeButton(android.R.string.no, null)
                .show();
    }
}