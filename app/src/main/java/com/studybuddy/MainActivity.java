package com.studybuddy;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.GridLayout;
import android.widget.TextView;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private User user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        user = getIntent().getSerializableExtra("user", User.class);

        Button btn_add_courses = (Button) findViewById(R.id.btn_add_courses);
        TextView txt_hello_user = findViewById(R.id.txt_hello_user);
        Button btn_graphical_data = findViewById(R.id.btn_graphical_data);
        GridLayout grid_courses = findViewById(R.id.grid_courses);

        User currentUser = (User) getIntent().getSerializableExtra("user");

        //Sets the text to Hello, user.
        if (currentUser != null) {
            txt_hello_user.setText("Hi, " + currentUser.getName());
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
                myCourses.add(new Course(1234, "Comp1100"));
                myCourses.add(new Course(122, "Comp2100"));

                UpdateCourseGrid(myCourses);
            }
        });

    }
    private void UpdateCourseGrid(ArrayList<Course> selectedCourses){
        GridLayout grid_courses = findViewById(R.id.grid_courses);
        for (Course course:selectedCourses){
            //Create a button for this course in mainActivity
            Button btn_newCourse = new Button(this);
            btn_newCourse.setText(course.getName());


            //Add the button to the layout
            grid_courses.addView(btn_newCourse);

            btn_newCourse.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(MainActivity.this, AssessmentsActivity.class);
                    //intent.putExtra("course", course);
                    startActivity(intent);
                }
            });
        }
    }

    public void clickStartStudy(View view) {
        Intent intent = new Intent(this, SetTimeActivity.class);
        intent.putExtra("user", user);
        startActivity(intent);
    }
}