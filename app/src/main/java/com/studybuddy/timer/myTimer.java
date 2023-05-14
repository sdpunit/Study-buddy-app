package com.studybuddy.timer;

import android.os.Handler;
import android.os.Message;

import java.util.Locale;
import java.util.concurrent.CountDownLatch;

public class myTimer {

    private TimeUp studyActivityWhenTimeUp;
    private final int initialMinutes;
    private double remainingMinutes;
    private CountDownLatch pauseLatch = new CountDownLatch(0);

    private Handler handler;

    /**
     * This interface is used to deal with time-up
     */
    public interface TimeUp {
        void timeUp();
    }

    public myTimer(int initialMinutes, TimeUp studyActivityWhenTimeUp) {
        this.initialMinutes = initialMinutes;
        this.remainingMinutes = initialMinutes;
        this.studyActivityWhenTimeUp = studyActivityWhenTimeUp;
    }

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

    public int getInitialMinutes() {
        return this.initialMinutes;
    }
    public double getStudyTime() {
        return (double) this.initialMinutes - this.remainingMinutes;
    }

}
