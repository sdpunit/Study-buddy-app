package com.studybuddy.notification;

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
