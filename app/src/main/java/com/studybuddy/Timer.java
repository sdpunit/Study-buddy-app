package com.studybuddy;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.os.SystemClock;
import android.view.KeyEvent;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Chronometer;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.util.concurrent.atomic.AtomicInteger;

public class Timer extends AppCompatActivity {

    private Chronometer chronometer;
    private Button chronometerBtn;
    private TextView setTime;
    private Spinner dropdown;
    boolean isRunning = false;
    int THRESHOLD=0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_timer);

            chronometer = findViewById(R.id.timer);
            chronometerBtn = findViewById(R.id.BtnChronometer);
            setTime = findViewById(R.id.et_Number);
            dropdown = findViewById(R.id.spinner1);
            Circle circle = (Circle) findViewById(R.id.circle);
            Animate animation = new Animate(circle, 0,360);

        //create a list of items for the spinner.
        //TODO: change subjects to ones relevant to the user
        String[] items = new String[]{"MATH1013", "COMP1110", "COMP2100","MATH1600"};
        //create an adapter for spinner
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, items);
        dropdown.setAdapter(adapter);

        // what happens when a time is input and the key ENTER is pressed
        setTime.setOnKeyListener((v, keyCode, event) -> {
            if(KeyEvent.KEYCODE_ENTER == keyCode) {
                int num = 0;
                if(setTime.getText().toString()!="")
                    num = Integer.parseInt(setTime.getText().toString());
                THRESHOLD=num;
                chronometer.setBase(SystemClock.elapsedRealtime());
                chronometer.start();
                chronometerBtn.setText("Stop Chronometer");
                isRunning = true;

                animation.setDuration(THRESHOLD*1000);
                circle.startAnimation(animation);
                return true;
            }
            return false;
        });

        AtomicInteger n = new AtomicInteger();
        //what happens when the START CHRONOMETER button is pressed
        chronometerBtn.setOnClickListener(v -> {

//            Circle circle2 = (Circle) findViewById(R.id.circle);
//            Animate animation2 = new Animate(circle, 0,360);

            if (isRunning) {
                chronometerBtn.setText("Resume Session");
                chronometer.stop();
                animation.cancel();
                //animation2.cancel();
                n.set(getSecondsFromDurationString(chronometer.getText().toString()));
                Toast toast = Toast.makeText(this, ""+n, Toast.LENGTH_SHORT);
                toast.show();
                isRunning = false;

            } else {
//                Toast toast = Toast.makeText(this, ""+n, Toast.LENGTH_SHORT);
//                toast.show();
                chronometerBtn.setText("Pause Session");
                chronometer.setBase(SystemClock.elapsedRealtime() - n.get()*1000);
                chronometer.start();

//                int j = n.intValue()/THRESHOLD;
//                Toast toast3 = Toast.makeText(this, "n is"+n.get()+"threshold is"+THRESHOLD+"old angle is"+(j), Toast.LENGTH_SHORT);
//                toast3.show();
//                circle2= (Circle) findViewById(R.id.circle);
//                animation2=new Animate(circle2,n.get()/THRESHOLD*360,360);
//                animation.setDuration(THRESHOLD*1000);
//                circle2.startAnimation(animation2);
                //circle.setAngle(n.get()/THRESHOLD*360);
                //animation.start();
                //circle.startAnimation(animation);
                isRunning = true;
            }
        });

        // stops timer when the threshold time is met
        chronometer.setOnChronometerTickListener(chronometer -> {
            if(getSecondsFromDurationString(chronometer.getText().toString()) >=THRESHOLD){
                chronometerBtn.setText("Start Chronometer");
                isRunning=false;
                chronometer.stop();
                Toast toast = Toast.makeText(this, "Study session completed", Toast.LENGTH_SHORT);
                toast.show();
            }
        });
    }

    //Method from: https://stackoverflow.com/questions/526524/android-get-time-of-chronometer-widget
    // converts time presented in chronometer to an int
    // Expects a string in the form MM:SS or HH:MM:SS
    public static int getSecondsFromDurationString(String value){

        String [] parts = value.split(":");

        // Wrong format, no value for you.
        if(parts.length < 2 || parts.length > 3)
            return 0;

        int seconds = 0, minutes = 0, hours = 0;

        if(parts.length == 2){
            seconds = Integer.parseInt(parts[1]);
            minutes = Integer.parseInt(parts[0]);
        }
        else if(parts.length == 3){
            seconds = Integer.parseInt(parts[2]);
            minutes = Integer.parseInt(parts[1]);
            hours = Integer.parseInt(parts[0]);
        }
        return seconds + (minutes*60) + (hours*3600);
    }

}