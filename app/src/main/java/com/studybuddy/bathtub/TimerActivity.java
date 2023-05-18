package com.studybuddy.bathtub;

import androidx.annotation.NonNull;
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
import android.widget.Toast;
import android.widget.VideoView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.studybuddy.R;
import com.studybuddy.timer.UserTimeState;
import com.studybuddy.timer.MyTimer;
import com.studybuddy.timer.studyState;

import java.util.ArrayList;

/**
 * Users study in this activity.
 * @auther Yanghe, Punit
 */
public class TimerActivity extends AppCompatActivity implements MyTimer.TimeUp {
    private UserTimeState userTimeState;
    private User user;
    private MyTimer timer;
    private Course course;
    private TextView timeTextView;
    private int courseStudiedBefore;
    private int studyNumberBefore;
    private ArrayList<User> leaderboard;
    private DatabaseReference leaderboardRef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_study);

        user = getIntent().getSerializableExtra("user", User.class);
        userTimeState = getIntent().getSerializableExtra("userTimeState", UserTimeState.class);
        course = getIntent().getSerializableExtra("course", Course.class);
        courseStudiedBefore = user.getCoursesStudied().size();
        studyNumberBefore = user.getStudyNumber();

        VideoView videoView = (VideoView) findViewById(R.id.videoView);
        Uri video = Uri.parse("android.resource://" + getPackageName() + "/" + R.raw.background);

        videoView.setVideoURI(video);
        videoView.setOnPreparedListener(mp -> {
            mp.setLooping(true);
            videoView.start();
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
        timer = MyTimer.getInstance(minutes, this);
        timer.start(handler);
    }

    /**
     * This method is called when the user clicks pause or resume button.
     * @auther Yanghe
     */
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

    /**
     * This method is called when the user clicks stop button.
     * @auther Yanghe, Punit
     */
    public void clickStop(View view) {
        Button pauseOrResumeButton = (Button) findViewById(R.id.pauseOrResumeButton);
        this.clickPauseOrResume(pauseOrResumeButton);

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Confirmation");
        builder.setMessage("Are you sure you want to stop?");
        builder.setPositiveButton("Yes", (dialog, which) -> {
            // update user information
            userTimeState.stopStudy();
            user.addStudyMinutes(timer.getStudyTime());
            user.setStudyNumber(user.getStudyNumber() + 1);
            if (!user.getCoursesStudied().contains(course)) {
                user.addCourseStudied(course);
            }
            user.addCourseTime(course, timer.getStudyTime());
            timer = null;

            // leaderboard
            leaderboardRef = FirebaseDatabase.getInstance().getReference("leaderboard");
            leaderboard = getLeaderboard();
            // update leaderboard
            DatabaseReference myRef = FirebaseDatabase.getInstance().getReference("users");
            myRef.child(String.valueOf(user.getUid())).setValue(user, new DatabaseReference.CompletionListener() {
                @Override
                public void onComplete(DatabaseError databaseError, DatabaseReference databaseReference) {
                    if (databaseError != null) {
                        Toaster.showToast(TimerActivity.this, "Data could not be updated");
                    } else {
                        Intent intent = new Intent(TimerActivity.this, MainActivity.class);
                        intent.putExtra("userTimeState", userTimeState);
                        intent.putExtra("user", user);
                        intent.putExtra("courseStudiedBefore", courseStudiedBefore);
                        intent.putExtra("studyNumberBefore", studyNumberBefore);
                        intent.putExtra("leaderboard", leaderboard);
                        startActivity(intent);
                        finish();
                    }
                }
            });
        });
        builder.setNegativeButton("No", (dialog, which) -> {
            dialog.dismiss();
            this.clickPauseOrResume(pauseOrResumeButton);
        });
        AlertDialog dialog = builder.create();
        dialog.show();
    }

    /**
     * This method is called when time up.
     * @auther: Yanghe Punit
     */
    @Override
    public void timeUp() {
        runOnUiThread(() -> {
            AlertDialog.Builder builder = new AlertDialog.Builder(TimerActivity.this);
            builder.setTitle("TIME UP");
            builder.setMessage("Have a Rest !");
            builder.setNeutralButton("OK", (dialog, which) -> {
                dialog.dismiss();
                // update user information
                userTimeState.stopStudy();
                user.addStudyMinutes(timer.getInitialMinutes());
                user.setStudyNumber(user.getStudyNumber() + 1);
                if (!user.getCoursesStudied().contains(course)) {
                    user.addCourseStudied(course);
                }
                user.addCourseTime(course, (double)timer.getInitialMinutes());
                timer = null;
                // leaderboard
                leaderboardRef = FirebaseDatabase.getInstance().getReference("leaderboard");
                leaderboard = getLeaderboard();
                // update the firebase
                DatabaseReference myRef = FirebaseDatabase.getInstance().getReference("users");
                myRef.child(String.valueOf(user.getUid())).setValue(user, new DatabaseReference.CompletionListener() {
                    @Override
                    public void onComplete(DatabaseError databaseError, DatabaseReference databaseReference) {
                        if (databaseError != null) {
                            Toaster.showToast(TimerActivity.this, "Data could not be updated");
                        } else {
                            Intent intent = new Intent(TimerActivity.this, MainActivity.class);
                            intent.putExtra("userTimeState", userTimeState);
                            intent.putExtra("user", user);
                            intent.putExtra("courseStudiedBefore", courseStudiedBefore);
                            intent.putExtra("studyNumberBefore", studyNumberBefore);
                            intent.putExtra("leaderboard", leaderboard);
                            startActivity(intent);
                            finish();
                        }
                    }
                });
            });
            AlertDialog dialog = builder.create();
            dialog.show();
            // center the message
            TextView messageView = (TextView) dialog.findViewById(android.R.id.message);
            messageView.setGravity(Gravity.CENTER);
            messageView.setTextSize(18);
        });
    }

    /**
     * Get the leaderboard from firebase.
     * @return the leaderboard as a list of users
     * @auther: Yanghe
     */
    public ArrayList<User> getLeaderboard() {
        ArrayList<User> leaderboard = new ArrayList<>();
        leaderboardRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot userSnapshot : dataSnapshot.getChildren()) {
                    User user = userSnapshot.getValue(User.class);
                    leaderboard.add(user);
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
            }
        });
        return leaderboard;
    }
}