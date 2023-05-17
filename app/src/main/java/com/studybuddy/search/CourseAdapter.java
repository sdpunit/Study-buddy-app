package com.studybuddy.search;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.studybuddy.bathtub.Course;
import com.studybuddy.R;

import java.util.List;

public class CourseAdapter extends ArrayAdapter<Course> {

    public CourseAdapter(Context context, int resource, List<Course> coursesList){
        super(context, resource, coursesList);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Course course = getItem(position);

        if(convertView == null){
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.course_cell, parent, false);
        }
        TextView tv = (TextView) convertView.findViewById(R.id.CellName);
        TextView tv2 = (TextView) convertView.findViewById(R.id.CellCode);
        TextView tv3 = (TextView) convertView.findViewById(R.id.CellConvener);
//        ImageView iv = (ImageView) convertView.findViewById(R.id.CellImage);

        tv.setText(course.getCourseName()); // course adapter breaks here even though the method called acts on the query not the course
        tv2.setText(course.getCourseCode());
        tv3.setText(course.getConvener());
//        iv.setImageResource(R.drawable.ic_launcher_foreground);




        return convertView;
    }
}
