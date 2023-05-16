package com.studybuddy;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.Intent;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.VideoView;

import com.studybuddy.timer.UserTimeState;
import com.studybuddy.timer.myTimer;
import com.studybuddy.timer.studyState;

public class TimerActivity extends AppCompatActivity implements myTimer.TimeUp {
    private UserTimeState userTimeState;
    private User user;
    private myTimer timer;
    private Course course;
    private TextView timeTextView;

    private int courseStudiedBefore;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_study);

        user = getIntent().getSerializableExtra("user", User.class);
        userTimeState = getIntent().getSerializableExtra("userTimeState", UserTimeState.class);
        course = getIntent().getSerializableExtra("course", Course.class);
        courseStudiedBefore = user.getCoursesStudied().size();

        VideoView videoView = (VideoView) findViewById(R.id.videoView);
        Uri video = Uri.parse("android.resource://" + getPackageName() + "/" + R.raw.background);

        videoView.setVideoURI(video);
        videoView.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
            @Override
            public void onPrepared(MediaPlayer mp) {
                mp.setLooping(true);
                videoView.start();
            }
        });

        timeTextView = (TextView) findViewById(R.id.timeTextView);
        int minutes = getIntent().getIntExtra("minutes", 0);

        Handler handler = new Handler(Looper.getMainLooper()) {
            @Override
            public void handleMessage(Message msg) {
                timeTextView.setText((String) msg.obj);
            }
        };
        userTimeState.startStudy();
        timer = new myTimer(minutes, this);
        timer.start(handler);
    }

    public void clickPauseOrResume(View view) {
        Button pauseOrResumeButton = (Button) view;
        // if the user is in study state, pause the timer
        if (this.userTimeState.getState() instanceof studyState) {
            pauseOrResumeButton.setBackgroundResource(R.drawable.ic_resume);
            timer.pause();
            this.userTimeState.pause();
        }
        else {
            timer.resume();
            pauseOrResumeButton.setBackgroundResource(R.drawable.ic_pause);
            this.userTimeState.resume();
        }
    }

    public void clickStop(View view) {
        Button pauseOrResumeButton = (Button) findViewById(R.id.pauseOrResumeButton);
        this.clickPauseOrResume(pauseOrResumeButton);

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Confirmation");
        builder.setMessage("Are you sure you want to stop?");
        builder.setPositiveButton("Yes", (dialog, which) -> {
            userTimeState.stopStudy();

            //userTimeState.addStudyMinutes(timer.getStudyTime());

            user.addStudyMinutes(timer.getStudyTime());

            user.setStudyNumber(user.getStudyNumber() + 1);
            if (!user.getCoursesStudied().contains(course)) {
                user.addCourseStudied(course);
            }
            //user.addCourseTime(course, timer.getStudyTime());
            timer = null;

            Intent intent = new Intent(TimerActivity.this, MainActivity.class);
            intent.putExtra("userTimeState", userTimeState);
            intent.putExtra("user", user);
            intent.putExtra("courseStudiedBefore", courseStudiedBefore);
            startActivity(intent);
            finish();
        });
        builder.setNegativeButton("No", (dialog, which) -> {
            dialog.dismiss();
            this.clickPauseOrResume(pauseOrResumeButton);
        });
        AlertDialog dialog = builder.create();
        dialog.show();
    }

    /**
     * This method is called when time up
     */
    @Override
    public void timeUp() {
        runOnUiThread(() -> {
            AlertDialog.Builder builder = new AlertDialog.Builder(TimerActivity.this);
            builder.setTitle("TIME UP");
            builder.setMessage("Have a Rest !");
            builder.setNeutralButton("OK", (dialog, which) -> {
                dialog.dismiss();
                userTimeState.stopStudy();

                user.addStudyMinutes(timer.getInitialMinutes());
                user.setStudyNumber(user.getStudyNumber() + 1);
                if (!user.getCoursesStudied().contains(course)) {
                    user.addCourseStudied(course);
                }
                //user.addCourseTime(course, (double)timer.getInitialMinutes());
                timer = null;

                Intent intent = new Intent(TimerActivity.this, MainActivity.class);
                intent.putExtra("userTimeState", userTimeState);
                intent.putExtra("user", user);
                startActivity(intent);
                finish();
            });
            AlertDialog dialog = builder.create();
            dialog.show();
            // center the message
            TextView messageView = (TextView) dialog.findViewById(android.R.id.message);
            messageView.setGravity(Gravity.CENTER);
            messageView.setTextSize(18);
        });
    }
}