package com.studybuddy.notification;

/**
 * Factory class for creating notifications. There are three types of notifications.
 * @author Yanghe (u7533843)
 * @feature [Interact-Noti]
 */
public class NotificationFactory {
    public StudyNotification createNotification(String notificationType) {
        switch (notificationType) {
            case "StudyNumber":
                return new StudyNumberNotification();
            case "StudyTime":
                return new StudyTimeNotification();
            case "StudyCourse":
                return new StudyCourseNotification();
            default:
                return null;
        }
    }
}
