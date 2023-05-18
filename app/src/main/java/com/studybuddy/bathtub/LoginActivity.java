package com.studybuddy.bathtub;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.studybuddy.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

/**
 * The first activity that the user sees when they open the app, which allows them to login or register.
 * @author Lana (u7103031), Punit (u7432723)
 * @feature Login, [FB-Auth]
 */
public class LoginActivity extends AppCompatActivity {
    private Handler handler;
    private JSONArray jsonArray;
    private int currentIndex; // used to keep track of new dummy users
    boolean validUser;
    EditText et_username;
    EditText et_password;
    ArrayList<User> leaderboard;
    DatabaseReference leaderboardRef = FirebaseDatabase.getInstance().getReference("leaderboard");


    public static final String CHANNEL_ID = "StudyBuddy";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        // if there the leaderboard is cleared, uncomment this line to add a dummy user
        //leaderboardRef.child("0").setValue(new User(1,"dummy","1", true, 1));

        leaderboard = new ArrayList<>();
        // put all the users in the leaderboard into the local leaderboard list
        leaderboardRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot userSnapshot: dataSnapshot.getChildren()) {
                    User user = userSnapshot.getValue(User.class);
                    leaderboard.add(user);
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
            }
        });

        // execute this to create a notification channel as soon as the app runs
        createNotificationChannel();

        final Button loginButton = findViewById(R.id.btn_login);
        final Button registerButton = findViewById(R.id.btn_login2);

        et_username = findViewById(R.id.et_username);
        et_password = findViewById(R.id.et_password);

        validUser = false;

        // what happens when the LOGIN button is pressed
        loginButton.setOnClickListener(v -> {
            // sets email and password to the information entered by the user
            String username = et_username.getText().toString();
            String password = et_password.getText().toString();

            //checks user details against the database
            authenticateUser(username,password);

            //resets boolean
            validUser=false;
        });

        // what happens when the REGISTER button is pressed
        registerButton.setOnClickListener(v -> {
            Intent intent = new Intent(LoginActivity.this, RegisterActivity.class);
            intent.putExtra("leaderboard", leaderboard);
            startActivity(intent);
        });

        // read the current index from the firebase
        DatabaseReference currentIndexRef = FirebaseDatabase.getInstance().getReference("currentIndex");
        currentIndexRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    currentIndex = dataSnapshot.getValue(Integer.class);
                }
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {
        }});

        // read the user data from the json file
        try {
            InputStream inputStream = getAssets().open("user_data.json");
            int size = inputStream.available();
            byte[] buffer = new byte[size];
            inputStream.read(buffer);
            inputStream.close();
            String json = new String(buffer, "UTF-8");
            jsonArray = new JSONArray(json);
        } catch (IOException | JSONException e) {
            e.printStackTrace();
        }
        handler = new Handler();
        // data stream and update leaderboard in the meantime
        uploadDataPeriodically(leaderboard);
    }
    /**
     * The Edit texts are cleared everytime we come back to this activity
     * @author Punit (u7432723)
     */
    @Override
    protected void onResume() {
        super.onResume();

        // Clear your EditText fields
        et_username.setText("");
        et_password.setText("");
    }

    /**
     * checks that the information entered by the user matches an instance in the firebase
     * @author Punit (u7432723)
     * @feature User Authentication
     */
    private void authenticateUser(String username, String password) {
        // Get a reference to the database
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference usersRef = database.getReference("users");

        // Fetch users from the database
        usersRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot userSnapshot : dataSnapshot.getChildren()) {
                    User user = userSnapshot.getValue(User.class);

                    if (user != null && user.getName().equals(username) && user.getPassword().equals(password)) {
                        validUser = true;

                        Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                        intent.putExtra("user", user);
                        intent.putExtra("leaderboard", leaderboard);
                        startActivity(intent);

                        break;
                    }
                }
                // Show login message after checking all users
                showLoginMessage(username);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Log.w("Firebase", "Failed to read users", databaseError.toException());
            }
        });
    }

    // message to be displayed after a user attempts login
    private void showLoginMessage(String username) {
        String text;
        if (validUser) {text = "Welcome "+username;}
        else {text = "Incorrect login details";}
        Toaster.showToast(getApplicationContext(), text);
    }

    /**
     * Create a notification channel
     * @author Yanghe (u7533843)
     */
    private void createNotificationChannel() {
        CharSequence name = getString(R.string.channel_name);
        String description = getString(R.string.channel_description);
        int importance = NotificationManager.IMPORTANCE_DEFAULT;
        NotificationChannel channel = new NotificationChannel(CHANNEL_ID, name, importance);
        channel.setDescription(description);
        NotificationManager notificationManager = getSystemService(NotificationManager.class);
        notificationManager.createNotificationChannel(channel);
    }

    /**
     * Data stream simulation
     * Read a user from the json file and upload it to the firebase every 15 seconds
     * Check if it can be added to the leaderboard in the meantime
     * @param leaderboard the leaderboard to be updated
     * @author Ahmed (u7490701), Yanghe (u7533843)
     * @feature Data stream
     */
    private void uploadDataPeriodically(List<User> leaderboard) {
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                if (currentIndex < jsonArray.length()) {
                    try {
                        JSONObject jsonObject = jsonArray.getJSONObject(currentIndex);

                        int uid = jsonObject.getInt("uid");
                        String name = jsonObject.getString("name");
                        String password = jsonObject.getString("password");
                        boolean isUndergrad = jsonObject.getBoolean("isUndergrad");
                        int studyMinutes = jsonObject.getInt("studyMinutes");

                        User user = new User(uid,name,password,isUndergrad,studyMinutes);

                        DatabaseReference userref = FirebaseDatabase.getInstance().getReference("users");
                        String key = userref.push().getKey();
                        userref.child(key).setValue(user);
                        // increment the current index
                        currentIndex++;
                        // Save the updated current index to firebase
                        DatabaseReference indexRef = FirebaseDatabase.getInstance().getReference("currentIndex");
                        indexRef.setValue(currentIndex);

                        // about leaderboard
                        // if there are less than 5 users in the leaderboard, add this new user
                        if (leaderboard.size() < 5 && user.getStudyMinutes() > 0) {
                            addUserToLeaderboardFirebase(user, leaderboard);
                        }
                        else {
                            // compare the new user's study minutes with the user with the lowest study minutes
                            double lowestStudyMinutes = leaderboard.get(leaderboard.size() - 1).getStudyMinutes();
                            if (user.getStudyMinutes() > lowestStudyMinutes) {
                                // remove the user with the lowest study minutes
                                leaderboard.remove(4);
                                addUserToLeaderboardFirebase(user, leaderboard);
                            }
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
                // upload the next user after 15 seconds
                handler.postDelayed(this, 15000);
            }
        }, 20000); // initial delay of 20 seconds
    }

    /**
     * Add a user to the leaderboard in firebase.
     * @param user the user to be added
     * @param leaderboard the leaderboard to be updated
     * @author Yanghe (u7533843)
     */
    public void addUserToLeaderboardFirebase(User user, List<User> leaderboard) {
        // add the new user
        leaderboard.add(user);
        // sort the leaderboard in descending order
        leaderboard.sort((u1, u2) -> Double.compare(u2.getStudyMinutes(), u1.getStudyMinutes()));
        // update the leaderboard in firebase
        leaderboardRef = FirebaseDatabase.getInstance().getReference("leaderboard");
        leaderboardRef.setValue(leaderboard);
    }
}


