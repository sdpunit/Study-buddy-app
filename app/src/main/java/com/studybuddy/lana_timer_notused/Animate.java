package com.studybuddy.lana_timer_notused;

import android.view.animation.Animation;
import android.view.animation.Transformation;

// Whole class from: https://stackoverflow.com/questions/29381474/how-to-draw-a-circle-with-animation-in-android-with-circle-size-based-on-a-value
public class Animate extends Animation {

    private Circle circle;

    private float oldAngle;
    private float newAngle;

    public Animate(Circle circle,int oldAngle, int newAngle) {
        this.oldAngle = oldAngle;
        this.newAngle = newAngle;
        this.circle = circle;
    }

    @Override
    protected void applyTransformation(float interpolatedTime, Transformation transformation) {
        float angle = oldAngle + ((newAngle - oldAngle) * interpolatedTime);

        circle.setAngle(angle);
        circle.requestLayout();
    }
}
