package com.studybuddy.search;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.EditText;

import com.studybuddy.Course;
import com.studybuddy.R;

import java.util.List;

public class CourseAdapter extends ArrayAdapter<Course> {

    public CourseAdapter(Context context, int resource, List<Course> userList){
        super(context, resource, userList);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Course course = getItem(position);

        if(convertView == null){
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.user_cell, parent, false);
        }
        EditText name = (EditText) convertView.findViewById(R.id.SearchInput);
        name.setText(course.getCourseName());


        return super.getDropDownView(position, convertView, parent);
    }
}
