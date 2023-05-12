package com.studybuddy;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

public class DetailActivity extends AppCompatActivity
{
    Course selectedCourse;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        getSelectedShape();
        setValues();

    }

    private void getSelectedShape()
    {
        Intent previousIntent = getIntent();
        String parsedStringID = previousIntent.getStringExtra("id");
        selectedCourse = SearchActivity.courseList.get(Integer.valueOf(parsedStringID));
    }

    private void setValues()
    {
        TextView name = (TextView) findViewById(R.id.CellName);
        TextView code = (TextView) findViewById(R.id.CellCode);
        TextView convener = (TextView) findViewById(R.id.CellConvener);
//        ImageView iv = (ImageView) findViewById(R.id.shapeImage);

        name.setText(selectedCourse.getCourseName());
        code.setText(selectedCourse.getCourseCode());
        convener.setText(selectedCourse.getConvener());
//        iv.setImageResource(selectedCourse.getImage());
    }
}