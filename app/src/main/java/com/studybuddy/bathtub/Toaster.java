package com.studybuddy.bathtub;

import android.content.Context;
import android.widget.Toast;

/**
 * To display a toast message.
 * @author Steven (u7108792)
 */
public class Toaster {
    static Toast toast = null;
    public static void showToast(Context mContext, String message){
        if (toast!=null)  {toast.cancel();}
        Toast t = Toast.makeText(mContext, message, Toast.LENGTH_SHORT);
        toast = t;
        t.show();
    }
}
