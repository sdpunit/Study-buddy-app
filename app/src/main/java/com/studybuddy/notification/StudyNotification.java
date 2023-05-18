package com.studybuddy.notification;

import android.content.Context;

import com.studybuddy.bathtub.User;
/**
 * This interface is for sending notifications.
 * @author Yanghe (u7533843)
 */
public interface StudyNotification {
    void notifyUser(Context context, User user);
}
