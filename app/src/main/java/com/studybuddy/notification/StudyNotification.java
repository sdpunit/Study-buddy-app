package com.studybuddy.notification;

import android.content.Context;

import com.studybuddy.User;

public interface StudyNotification {
    void notifyUser(Context context, User user);
}
