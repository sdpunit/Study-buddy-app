package com.studybuddy;

import android.content.Context;
import android.widget.Toast;

import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

public class StudyTimeNotification implements StudyNotification{

    @Override
    public void notifyUser(Context context, User user) {
        NotificationCompat.Builder builder = new NotificationCompat.Builder(context, Login.CHANNEL_ID)
                .setSmallIcon(R.drawable.ic_launcher_foreground)
                .setContentTitle("Congratulations!")
                .setContentText("You have studied for " + (int)user.getStudyMinutes() / 60 + " hours!");
        NotificationManagerCompat notificationManager = NotificationManagerCompat.from(context);
        try {
            notificationManager.notify(1, builder.build());
        }
        catch (SecurityException e) {
            Toast.makeText(context, "Security Exception", Toast.LENGTH_SHORT).show();
        }
    }
}
