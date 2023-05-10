package com.studybuddy;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class StudyActivity extends AppCompatActivity {
    private User user;
    private myTimer timer;
    private TextView timeTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_study);

        user = (User) getIntent().getSerializableExtra("user", User.class);

        timeTextView = (TextView) findViewById(R.id.timeTextView);
        int minutes = getIntent().getIntExtra("minutes", 0);

        Handler handler = new Handler(Looper.getMainLooper()) {
            @Override
            public void handleMessage(Message msg) {
                timeTextView.setText((String) msg.obj);
            }
        };
        user.startStudy();
        timer = new myTimer(minutes);
        timer.start(handler);
    }

    public void clickPauseOrResume(View view) {
        Button pauseOrResumeButton = (Button) view;
        // if the user is in study state, pause the timer
        if (this.user.getState() instanceof studyState) {
            pauseOrResumeButton.setBackgroundResource(R.drawable.ic_resume);
            timer.pause();
            this.user.pause();
        }
        else {
            timer.resume();
            pauseOrResumeButton.setBackgroundResource(R.drawable.ic_pause);
            this.user.resume();
        }
    }

    public void clickStop(View view) {
        Button pauseOrResumeButton = (Button) findViewById(R.id.pauseOrResumeButton);
        this.clickPauseOrResume(pauseOrResumeButton);

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Confirmation");
        builder.setMessage("Are you sure you want to stop?");
        builder.setPositiveButton("Yes", (dialog, which) -> {
            user.stopStudy();
            // add study time to user
            user.addStudyMinutes(timer.getStudyTime());
            timer = null;

            Intent intent = new Intent(StudyActivity.this, MainActivity.class);
            intent.putExtra("user", user);
            startActivity(intent);
        });
        builder.setNegativeButton("No", (dialog, which) -> {
            dialog.dismiss();
            this.clickPauseOrResume(pauseOrResumeButton);
        });
        AlertDialog dialog = builder.create();
        dialog.show();
    }
}