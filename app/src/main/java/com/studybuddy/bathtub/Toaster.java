package com.studybuddy.bathtub;

import android.content.Context;
import android.widget.Toast;

public class Toaster {
    public static void showToast(Context mContext, String message){
        Toast.makeText(mContext, message, Toast.LENGTH_SHORT).show();
    }
}
