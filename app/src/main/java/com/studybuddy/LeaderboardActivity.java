package com.studybuddy;


import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TableLayout;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

import android.widget.ListView;

public class LeaderboardActivity extends AppCompatActivity {
    private User user;
    private ArrayList<User> leaderboard;
    private UserListAdapter userListAdapter;
    private DatabaseReference leaderboardRef;
    private ValueEventListener valueEventListener;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_leaderboard);
        user = getIntent().getSerializableExtra("user", User.class);

        ListView leaderboardListView = findViewById(R.id.leaderboardListView);

        leaderboard = new ArrayList<>();
        userListAdapter = new UserListAdapter(this, R.layout.list_item_user, leaderboard);
        leaderboardListView.setAdapter(userListAdapter);

        leaderboardRef = FirebaseDatabase.getInstance().getReference("leaderboard");
        valueEventListener = new ValueEventListener(){
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                leaderboard.clear();
                for (DataSnapshot userSnapshot: dataSnapshot.getChildren()) {
                    User user = userSnapshot.getValue(User.class);
                    leaderboard.add(user);
                }
                userListAdapter.notifyDataSetChanged();
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
            }
        };
        leaderboardRef.addValueEventListener(valueEventListener);
    }

    public void clickBack (View view) {
        Intent intent = new Intent(LeaderboardActivity.this, MainActivity.class);
        intent.putExtra("user", user);
        startActivity(intent);
        finish();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (leaderboardRef != null && valueEventListener != null) {
            leaderboardRef.removeEventListener(valueEventListener);
        }
    }
}

