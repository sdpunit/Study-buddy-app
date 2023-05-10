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

import java.util.ArrayList;
import java.util.Random;

public class MainActivity extends AppCompatActivity {
    private User user;
    private UserTimeState userTimeState;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

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
            studyMinutes.setText("You have studied for " + Math.round(userTimeState.getStudyMinutes()) + " minutes!");
        } else {
            studyMinutes.setText("No study time recorded.");
        }
    }
}