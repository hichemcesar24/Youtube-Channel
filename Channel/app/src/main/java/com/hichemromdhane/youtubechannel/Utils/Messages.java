package com.hichemromdhane.youtubechannel.Utils;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import com.hichemromdhane.youtubechannel.BuildConfig;

public class Messages {
    public static void log(String message) {
        if (BuildConfig.DEBUG)
            Log.d("Hichem Romdhane", message);
    }

    public static void toast(Context context, String message) {
        Toast.makeText(context, message, Toast.LENGTH_SHORT).show();
    }
}
