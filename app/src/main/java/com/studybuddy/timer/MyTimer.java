package com.studybuddy.timer;

import android.os.Handler;
import android.os.Message;

import java.util.Locale;
import java.util.concurrent.CountDownLatch;

/**
 * This class is the timer used in the timer activity.
 * @author Yanghe
 */
public class MyTimer {
    private static MyTimer instance;

    private TimeUp studyActivityWhenTimeUp;
    private final int initialMinutes;
    private double remainingMinutes;
    private CountDownLatch pauseLatch = new CountDownLatch(0);

    private Handler handler;

    // This interface is used to deal with time-up.
    public interface TimeUp {
        void timeUp();
    }
    // Get the instance of MyTimer.
    public static MyTimer getInstance(int initialMinutes, TimeUp studyActivityWhenTimeUp) {
        if (instance == null) {
            instance = new MyTimer(initialMinutes, studyActivityWhenTimeUp);
        }
        return instance;
    }
    private MyTimer(int initialMinutes, TimeUp studyActivityWhenTimeUp) {
        this.initialMinutes = initialMinutes;
        this.remainingMinutes = initialMinutes;
        this.studyActivityWhenTimeUp = studyActivityWhenTimeUp;
    }

    // Start the timer
    public void start(Handler handler) {
        this.handler = handler;

        Thread timerThread = new Thread(new Runnable() {
            @Override
            public void run() {
                countDown();
            }
        });
        timerThread.start();
    }

    // Count down and send message to handler so that the UI can be updated
    public void countDown() {
        int totalSeconds = this.initialMinutes * 60;

        for (int remainingSeconds = totalSeconds; remainingSeconds > 0; remainingSeconds--) {
            int currentMinutes = remainingSeconds / 60;
            int currentSeconds = remainingSeconds % 60;

            this.remainingMinutes = currentMinutes + currentSeconds / 60.0;
            String timeRemaining = String.format(Locale.ENGLISH,"%02d:%02d\n", currentMinutes, currentSeconds);
            Message message = Message.obtain();
            message.obj = timeRemaining;
            handler.sendMessage(message);
            try {
                pauseLatch.await();
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        // time up
        studyActivityWhenTimeUp.timeUp();
    }

    public void pause() {
        pauseLatch = new CountDownLatch(1);
    }

    public void resume() {
        pauseLatch.countDown();
    }

    // Get the whole time of the timer, which is used to record study time when time up
    public int getInitialMinutes() {
        return this.initialMinutes;
    }

    // Get the study time of this study session, which is used to record study time when the user manually stops.
    public double getStudyTime() {
        return (double) this.initialMinutes - this.remainingMinutes;
    }
}