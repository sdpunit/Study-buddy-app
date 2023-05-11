package com.studybuddy;

import android.content.Context;
import android.widget.Toast;

import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

public class StudyCourseNotification implements StudyNotification{
    @Override
    public void notifyUser(Context context, User user) {
        NotificationCompat.Builder builder = new NotificationCompat.Builder(context, Login.CHANNEL_ID)
                .setSmallIcon(R.drawable.ic_launcher_foreground)
                .setContentTitle("Good Job!")
                .setContentText("You have studied " + user.getCourseStudied().size() + " different courses!");
        NotificationManagerCompat notificationManager = NotificationManagerCompat.from(context);
        try {
            notificationManager.notify(2, builder.build());
        }
        catch (SecurityException e) {
            Toast.makeText(context, "Security Exception", Toast.LENGTH_SHORT).show();
        }
    }
}
