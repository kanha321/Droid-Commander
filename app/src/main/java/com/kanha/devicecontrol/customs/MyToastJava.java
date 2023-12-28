package com.kanha.devicecontrol.customs;

import android.content.Context;
import android.widget.Toast;

public class MyToastJava {

    static Toast toast;

    public static void showToast(Context context, String message, int duration) {

        if (MyToastJava.toast != null) {
            MyToastJava.toast.cancel();
        }

        MyToastJava.toast = Toast.makeText(context, String.valueOf(message), duration);
        MyToastJava.toast.show();
    }

    public static void showToast(Context context, String message) {

        if (MyToastJava.toast != null) {
            MyToastJava.toast.cancel();
        }

        MyToastJava.toast = Toast.makeText(context, String.valueOf(message), Toast.LENGTH_SHORT);
        MyToastJava.toast.show();
    }
}
