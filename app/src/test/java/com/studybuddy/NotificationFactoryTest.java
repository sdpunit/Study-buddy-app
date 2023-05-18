package com.studybuddy;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

import com.studybuddy.notification.NotificationFactory;
import com.studybuddy.notification.StudyCourseNotification;
import com.studybuddy.notification.StudyNumberNotification;
import com.studybuddy.notification.StudyTimeNotification;
import org.junit.Test;

public class NotificationFactoryTest {
    /**
     * Tests if notification factory is producing the correct notification types
     * @author Lana (u7103031)
     */
    @Test(timeout=1000)
    public void notificationFactoryTest() {
        NotificationFactory nf = new NotificationFactory();
        assertEquals(nf.createNotification("StudyNumber"), new StudyNumberNotification());
        assertEquals(nf.createNotification("StudyTime"), new StudyTimeNotification());
        assertEquals(nf.createNotification("StudyCourse"), new StudyCourseNotification());
        assertNull(nf.createNotification("Studynumbers"));
    }
}
