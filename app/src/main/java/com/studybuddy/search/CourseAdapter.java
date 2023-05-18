package com.studybuddy.search;

import android.annotation.SuppressLint;
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

    /**
     * This method is to set the view of the course cell.
     * @param position the position of the course in the list
     * @param convertView the view of the course cell
     * @param parent the parent view
     * @return the view of the course cell
     * @author Steven (u7108792)
     */
    @SuppressLint("SetTextI18n")
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Course course = getItem(position);

        if(convertView == null){
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.course_cell, parent, false);
        }
        TextView tv = convertView.findViewById(R.id.CellName);
        TextView tv2 = convertView.findViewById(R.id.CellCode);
        TextView tv3 = convertView.findViewById(R.id.CellConvener);

        tv.setText(course.getCourseName());
        tv2.setText(course.getCourseCode());
        if(course.getConvener() == null){
            tv3.setText("TBD");
        } else{
            tv3.setText(course.getConvener());
        }
        return convertView;
    }
}
