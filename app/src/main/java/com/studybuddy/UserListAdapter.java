package com.studybuddy;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.studybuddy.bathtub.User;

import java.util.List;

/**
 * This class is used to display the user's name and study minutes in the leaderboard.
 * @author Ahmed
 */
public class UserListAdapter extends ArrayAdapter<User> {
    private Context context;
    private int resource;

    public UserListAdapter(Context context, int resource, List<User> users) {
        super(context, resource, users);
        this.context = context;
        this.resource = resource;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        if (convertView == null) {
            LayoutInflater inflater = LayoutInflater.from(context);
            convertView = inflater.inflate(resource, parent, false);
        }
        User user = getItem(position);
        TextView nameTextView = convertView.findViewById(R.id.nameTextView);
        TextView studyMinutesTextView = convertView.findViewById(R.id.studyMinutesTextView);
        nameTextView.setText(user.getName());
        studyMinutesTextView.setText(String.valueOf(Math.round(user.getStudyMinutes())));
        return convertView;
    }
}

