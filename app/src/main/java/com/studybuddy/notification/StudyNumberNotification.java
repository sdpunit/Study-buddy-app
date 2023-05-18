package com.studybuddy.notification;

import android.content.Context;
import android.widget.Toast;

import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import com.studybuddy.bathtub.LoginActivity;
import com.studybuddy.R;
import com.studybuddy.bathtub.User;
/**
 * This class is to create a notification when the user has studied two more times.
 * A process including start study and stop study is considered as study once.
 * @author Yanghe (u7533843)
 * @feature [Interact-Noti]
 */
public class StudyNumberNotification implements StudyNotification {
    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true; // The objects are the same reference
        }
        return obj != null && getClass() == obj.getClass(); // The objects are considered equal
    }
    @Override
    public void notifyUser(Context context, User user) {
        NotificationCompat.Builder builder = new NotificationCompat.Builder(context, LoginActivity.CHANNEL_ID)
                .setSmallIcon(R.drawable.ic_launcher_foreground)
                .setContentTitle("Good Job!")
                .setContentText("You have completed " + user.getStudyNumber() + " study sessions! Keep up the good work!");
        NotificationManagerCompat notificationManager = NotificationManagerCompat.from(context);
        try {
            notificationManager.notify(0, builder.build());
        }
        catch (SecurityException e) {
            Toast.makeText(context, "Security Exception", Toast.LENGTH_SHORT).show();
        }
    }
}
