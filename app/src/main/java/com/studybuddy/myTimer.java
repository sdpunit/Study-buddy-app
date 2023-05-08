package com.studybuddy;

import android.os.Handler;
import android.os.Message;

import java.util.Locale;
import java.util.concurrent.CountDownLatch;

public class myTimer {
    private final int minutes;
    private CountDownLatch pauseLatch = new CountDownLatch(0);

    private Handler handler;

    public myTimer(int minutes) {
        this.minutes = minutes;
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
        int totalSeconds = this.minutes * 60;

        for (int remainingSeconds = totalSeconds; remainingSeconds > 0; remainingSeconds--) {
            int currentMinutes = remainingSeconds / 60;
            int currentSeconds = remainingSeconds % 60;

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
    }

    public void pause() {
        pauseLatch = new CountDownLatch(1);
    }

    public void resume() {
        pauseLatch.countDown();
    }

}
