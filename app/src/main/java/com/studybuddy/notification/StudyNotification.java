package com.studybuddy.notification;

import android.content.Context;

import com.studybuddy.User;
/**
 * This interface is for sending notifications.
 * @author Yanghe Dong
 */
public interface StudyNotification {
    void notifyUser(Context context, User user);
}
