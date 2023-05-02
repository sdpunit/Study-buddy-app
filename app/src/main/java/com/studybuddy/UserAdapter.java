package com.studybuddy;

import android.content.Context;
import android.os.TestLooperManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import org.w3c.dom.Text;

import java.util.List;

public class UserAdapter extends ArrayAdapter<User> {

    public UserAdapter(Context context, int resource, List<User> userList){
        super(context, resource, userList);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        User user = getItem(position);

        if(convertView == null){
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.user_cell, parent, false);
        }
        EditText name = (EditText) convertView.findViewById(R.id.SearchName);
        name.setText(user.getName());


        return super.getDropDownView(position, convertView, parent);
    }
}
