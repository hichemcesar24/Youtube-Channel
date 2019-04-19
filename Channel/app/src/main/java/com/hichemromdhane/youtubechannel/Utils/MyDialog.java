package com.hichemromdhane.youtubechannel.Utils;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;

public class MyDialog {
    private ProgressDialog dialog;
    private Context context;

    public MyDialog(Context context) {
        this.context = context;
    }

    public void show() {
        show("Please wait...");
    }

    public void show(String message) {
        if (dialog == null || !dialog.isShowing()) {
            dialog = new ProgressDialog(context);
            dialog.setMessage(message);
            dialog.setCancelable(false);
            dialog.show();
        }
    }

    public void setMessage(final String message) {
        if (dialog != null && dialog.isShowing()) {
            ((Activity) context).runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    dialog.setMessage(message);
                }
            });
        }
    }

    public void close() {
        if (dialog != null && dialog.isShowing()) {
            dialog.dismiss();
        }
    }
}
