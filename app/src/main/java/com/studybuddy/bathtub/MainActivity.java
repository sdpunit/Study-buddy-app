package com.studybuddy.bathtub;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.GridLayout;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.studybuddy.LeaderboardActivity;
import com.studybuddy.R;
import com.studybuddy.notification.NotificationFactory;
import com.studybuddy.notification.StudyNotification;
import com.studybuddy.timer.UserTimeState;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * The main activity of the app. All the other activities end up here.
 * @author Punit
 */
public class MainActivity extends AppCompatActivity {
    private User user;
    private UserTimeState userTimeState;
    private DatabaseReference userRef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        user = getIntent().getSerializableExtra("user", User.class);
        userTimeState = getIntent().getSerializableExtra("userTimeState", UserTimeState.class);

        // get the leaderboard from the timer activity
        ArrayList<User> leaderboard = getIntent().getSerializableExtra("leaderboard", ArrayList.class);
        // The only case that it isn't null is when the user comes from the timer activity
        // And this is also when we want to update the leaderboard
        if (leaderboard != null) {
            checkAndUpdateLeaderboardFirebase(leaderboard);
        }

        Button btn_add_courses = (Button) findViewById(R.id.btn_add_courses);
        TextView txt_hello_user = findViewById(R.id.txt_hello_user);
        Button btn_graphical_data = findViewById(R.id.btn_graphical_data);
        GridLayout grid_courses = findViewById(R.id.grid_courses);

        //Sets the text to Hello, user.
        if (user != null) {
            txt_hello_user.setText("Hi, " + user.getName());
        } else {
            txt_hello_user.setText("Hi, guest");
        }

        userRef = FirebaseDatabase.getInstance().getReference("users").child(String.valueOf(user.getUid()));
        userRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                // Get User object and use the values to update the UI
                User updatedUser = dataSnapshot.getValue(User.class);
                if (updatedUser != null) {
                    user = updatedUser;
                    updateCourseGrid(user);
                    displayStudyMinutes(user);
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Log.w("Firebase", "loadUser:onCancelled", databaseError.toException());
            }
        });

        //Go to SearchActivity if clicked
        btn_add_courses.setOnClickListener(view -> {
            Intent intent = new Intent(MainActivity.this, SearchActivity.class);
            intent.putExtra("user", user);
            startActivity(intent);
        });
        // go to the leaderboard
        btn_graphical_data.setOnClickListener(view -> {
            Intent intent = new Intent(MainActivity.this, LeaderboardActivity.class);
            intent.putExtra("user", user);
            startActivity(intent);
        });

        // check cases and send notifications
        sendNotification();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.logout_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_logout:
                // Perform logout here
                Intent intent = new Intent(MainActivity.this, LoginActivity.class);
                startActivity(intent);
                finish();
                return true;
            case R.id.action_reset_study_time:
                // Reset the study time here
                user.setStudyMinutes(0);
                userRef.setValue(user);
                displayStudyMinutes(user);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }



    private void updateCourseGrid(User user) {

        // Get the GridLayout from the layout
        ArrayList<Course> courses = user.getCoursesEnrolled();
        if (courses == null) {
            return;
        }
        GridLayout gridCourses = findViewById(R.id.grid_courses);
        gridCourses.removeAllViews();
        // Create a random number generator
        Random random = new Random();

        for (Course course : courses) {
            // Linear layout for adding course button and name vertically
            LinearLayout courseLayout = new LinearLayout(this);
            courseLayout.setOrientation(LinearLayout.VERTICAL);

            // Create an ImageButton for the course
            ImageButton courseButton = new ImageButton(this);

            // Choose a random pattern image
            int patternNumber = random.nextInt(12) + 1;
            int randomImage = getResources().getIdentifier("pattern" + patternNumber, "drawable", getPackageName());
            courseButton.setImageResource(randomImage);

            courseButton.setScaleType(ImageView.ScaleType.FIT_CENTER);
            courseButton.setAdjustViewBounds(true);

            // image_size is in dimensions
            int imageSize = getResources().getDimensionPixelSize(R.dimen.image_size);
            LinearLayout.LayoutParams imageParams = new LinearLayout.LayoutParams(imageSize, imageSize);
            courseButton.setLayoutParams(imageParams);

            courseButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(MainActivity.this, AssessmentsActivity.class);
                    intent.putExtra("course", course);
                    intent.putExtra("user", user);
                    startActivity(intent);
                }
            });

            // Text View for the course name
            TextView txt_courseName = new TextView(this);
            txt_courseName.setText(course.getCourseCode());
            txt_courseName.setGravity(Gravity.CENTER);
            txt_courseName.setTypeface(null, Typeface.BOLD);

            TextView txt_timeStudied = new TextView(this);
            Double timeStudied = user.getCourseTime().get(course.getCourseCode());
            txt_timeStudied.setGravity(Gravity.CENTER);
            if (timeStudied == null || timeStudied == 0.0) {
                txt_timeStudied.setText("Not started");
            } else {

                txt_timeStudied.setText("Studied "+ Math.round(timeStudied)+ " minutes");
            }

            courseLayout.addView(courseButton);
            courseLayout.addView(txt_courseName);
            courseLayout.addView(txt_timeStudied);
            gridCourses.addView(courseLayout);

        }
    }

    public void displayStudyMinutes(User user) {
        TextView studyMinutes = (TextView) findViewById(R.id.studyMinutes);
        if(user != null) {
            studyMinutes.setText("You have studied for " + Math.round(user.getStudyMinutes()) + " minutes!");
        } else {
            studyMinutes.setText("No study time recorded.");
        }
    }

    /**
     * Send notifications to the user based on different cases.
     * @author Yanghe
     */
    public void sendNotification() {
        // notificationTypes
        List<String> notificationTypes = new ArrayList<>();
        // every two study sessions
        int studyNumberNow = user.getStudyNumber();
        int studyNumberBefore = getIntent().getIntExtra("studyNumberBefore", studyNumberNow);
        if (user.getStudyNumber() % 2 == 0 && user.getStudyNumber() != 0 && studyNumberNow > studyNumberBefore) {
            notificationTypes.add("StudyNumber");
        }
        // every two hours of study
        if (((int)user.getStudyMinutes() / 60) % 2 == 0 && user.getStudyMinutes() > 119) {
            notificationTypes.add("StudyTime");
        }
        // every two unique courses
        int courseStudiedNow = user.getCoursesStudied().size();
        int courseStudiedBefore = getIntent().getIntExtra("courseStudiedBefore", courseStudiedNow);
        if (courseStudiedNow % 2 == 0 && courseStudiedNow != 0 && courseStudiedNow > courseStudiedBefore) {
            notificationTypes.add("StudyCourse");
        }

        NotificationFactory notificationFactory = new NotificationFactory();
        for (String notificationType : notificationTypes) {
            StudyNotification notification = notificationFactory.createNotification(notificationType);
            notification.notifyUser(MainActivity.this, user);
        }
    }

    /**
     * Update the leaderboard in Firebase.
     * @author Yanghe
     */
    public void checkAndUpdateLeaderboardFirebase(ArrayList<User> leaderboard) {
        // if this user is already in the leaderboard, update the study minutes
        for (User u : leaderboard) {
            if (u.equals(user)) {
                if (u.getStudyMinutes() < user.getStudyMinutes()) {
                    u.setStudyMinutes(user.getStudyMinutes());
                    leaderboard.sort((u1, u2) -> Double.compare(u2.getStudyMinutes(), u1.getStudyMinutes()));
                    DatabaseReference leaderboardRef = FirebaseDatabase.getInstance().getReference("leaderboard");
                    leaderboardRef.setValue(leaderboard);
                }
                // if the study time doesn't change
                return;
            }
        }
        // if this user is not in the leaderboard and there are less than 5, add it
        if (leaderboard.size() < 5) {
            addUserToLeaderboardFirebase(leaderboard);
        }
        // if this user is not in the leaderboard, check if the user's study minutes is greater than the last user's study minutes
        else {
            double lowestStudyMinutes = leaderboard.get(leaderboard.size() - 1).getStudyMinutes();
            if (user.getStudyMinutes() > lowestStudyMinutes) {
                // remove the user with the lowest study minutes
                leaderboard.remove(4);
                addUserToLeaderboardFirebase(leaderboard);
            }
        }
    }

    /**
     * Helper method for checkAndUpdateLeaderboardFirebase
     * @author Yanghe
     */
    public void addUserToLeaderboardFirebase(ArrayList<User> leaderboard) {
        // add the new user
        leaderboard.add(user);
        // the leaderboard is always sorted in descending order
        leaderboard.sort((u1, u2) -> Double.compare(u2.getStudyMinutes(), u1.getStudyMinutes()));
        // update the leaderboard in the firebase
        DatabaseReference leaderboardRef = FirebaseDatabase.getInstance().getReference("leaderboard");
        leaderboardRef.setValue(leaderboard);
    }
}