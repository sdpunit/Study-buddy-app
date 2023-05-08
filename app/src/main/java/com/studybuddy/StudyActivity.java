package com.studybuddy;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
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
    private Handler handler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_study);

        user = (User) getIntent().getSerializableExtra("user", User.class);

        timeTextView = (TextView) findViewById(R.id.timeTextView);
        int minutes = getIntent().getIntExtra("minutes", 0);

        handler = new Handler(Looper.getMainLooper()) {
            @Override
            public void handleMessage(Message msg) {
                timeTextView.setText((String) msg.obj);
            }
        };
        user.startStudy(this);
        timer = new myTimer(minutes);
        timer.start(handler);
    }

    public void clickPause(View view) {
        // if the user is in study state, pause the timer
        if (this.user.getState() instanceof studyState) {
            timer.pause();
        }
        this.user.pause(StudyActivity.this);
    }

    public void clickResume(View view) {
        // if the user is in pause state, resume the timer
        if (this.user.getState() instanceof pauseState) {
            timer.resume();
        }
        this.user.resume(StudyActivity.this);
    }

    public void clickStop(View view) {
        this.clickPause(view);

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Confirmation");
        builder.setMessage("Are you sure you want to stop?");
        builder.setPositiveButton("Yes", (dialog, which) -> {
            user.stopStudy();
            timer = null;

            Intent intent = new Intent(StudyActivity.this, SetTimeActivity.class);
            startActivity(intent);
            finish();
        });
        builder.setNegativeButton("No", (dialog, which) -> {
            dialog.dismiss();
            this.clickResume(view);
        });
        AlertDialog dialog = builder.create();
        dialog.show();
    }
}