package com.studybuddy;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.GridLayout;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.studybuddy.notification.NotificationFactory;
import com.studybuddy.notification.StudyNotification;
import com.studybuddy.timer.UserTimeState;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.google.firebase.FirebaseApp;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;


import java.io.IOException;
import java.io.InputStream;


import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class MainActivity extends AppCompatActivity {
    private User user;
    private UserTimeState userTimeState;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        FirebaseApp.initializeApp(this);


        user = getIntent().getSerializableExtra("user", User.class);
        userTimeState = getIntent().getSerializableExtra("userTimeState", UserTimeState.class);
        // display the study minutes
        displayStudyMinutes();

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

        //Go to SearchActivity if clicked
        btn_add_courses.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, SearchActivity.class);
                startActivity(intent);
            }
        });
       //Using this button for testing at the moment
        btn_graphical_data.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ArrayList<Course> myCourses = new ArrayList<>();
                myCourses.add(new Course(
                        "MEAS8127",
                        "'Sectarianism' in the Middle East: Theology, Politics and Identity",
                        "Dr. Liz"));
                myCourses.add(new Course("COMP1720", "Authoritarianism, Democratisation and Protest in the Muslim Middle East", "Dr. Albert"));
                myCourses.add(new Course("COMP2100", "Software Construction", "Bernardo"));

                updateCourseGrid(myCourses);
            }
        });

        // check cases and send notifications
        sendNotification();

        try {
            InputStream inputStream = getAssets().open("user_data.json");
            int size = inputStream.available();
            byte[] buffer = new byte[size];
            inputStream.read(buffer);
            inputStream.close();
            String json = new String(buffer, "UTF-8");

            JSONArray jsonArray = new JSONArray(json);
            DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("users");

            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject jsonObject = jsonArray.getJSONObject(i);

                // Extract the required values from the JSON object
                int uid = jsonObject.getInt("uid");
                String username = jsonObject.getString("username");
                String password = jsonObject.getString("password");
                boolean isUndergrad = jsonObject.getBoolean("isUndergrad");
                int studyMinutes = jsonObject.getInt("studyMinutes");

                // Create an instance of your object and set its properties
                Person user = new Person(uid,username,password,isUndergrad,studyMinutes);

                // Store the object under a unique ID in the "users" node
                String userRef = databaseReference.push().getKey();
                databaseReference.child(userRef).setValue(user);


            }
        } catch (IOException | JSONException e) {
            e.printStackTrace();
        }



    }
    private void updateCourseGrid(ArrayList<Course> courses) {
        // Get the GridLayout from the layout
        GridLayout gridCourses = findViewById(R.id.grid_courses);

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
            int imageResId = getResources().getIdentifier("pattern" + patternNumber, "drawable", getPackageName());
            courseButton.setImageResource(imageResId);

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
            TextView txt_view_courseName = new TextView(this);
            txt_view_courseName.setText(course.getCourseCode());
            txt_view_courseName.setGravity(Gravity.CENTER);
            txt_view_courseName.setTypeface(null, Typeface.BOLD);

            courseLayout.addView(courseButton);
            courseLayout.addView(txt_view_courseName);
            gridCourses.addView(courseLayout);

        }
    }

    public void displayStudyMinutes() {
        TextView studyMinutes = (TextView) findViewById(R.id.studyMinutes);
        if(userTimeState != null) {
            //studyMinutes.setText("You have studied for " + Math.round(userTimeState.getStudyMinutes()) + " minutes!");
            studyMinutes.setText("You have studied for " + Math.round(user.getStudyMinutes()) + " minutes!");
        } else {
            studyMinutes.setText("No study time recorded.");
        }
    }

    public void sendNotification() {
        // notificationTypes
        List<String> notificationTypes = new ArrayList<>();
        // every two study sessions
        if (user.getStudyNumber() % 2 == 0 && user.getStudyNumber() != 0) {
            notificationTypes.add("StudyNumber");
        }
        // every two hours of study
        if (((int)user.getStudyMinutes() / 60) % 2 == 0 && user.getStudyMinutes() > 119) {
            notificationTypes.add("StudyTime");
        }
        // every two unique courses
        if (user.getCourseStudied().size() % 2 == 0 && user.getCourseStudied().size() != 0) {
            notificationTypes.add("StudyCourse");
        }

        NotificationFactory notificationFactory = new NotificationFactory();
        for (String notificationType : notificationTypes) {
            StudyNotification notification = notificationFactory.createNotification(notificationType);
            notification.notifyUser(MainActivity.this, user);
        }
    }
}