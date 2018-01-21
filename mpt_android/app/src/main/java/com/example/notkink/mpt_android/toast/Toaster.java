package com.example.notkink.mpt_android.toast;

import android.content.Context;
import android.os.Handler;
import android.os.Looper;
import android.widget.Toast;

import com.example.notkink.mpt_android.App;

public class Toaster {

    private Toast toast;

    private Handler handler;

    private Context context;

    public Toaster() {
        handler = new Handler(Looper.getMainLooper());
    }


    public void showToast(final String message) {

        if (isOnMainThread()) {
            showToastInUi(message);
        } else {
            handler.removeCallbacksAndMessages(null);
            handler.post(new Runnable() {
                @Override
                public void run() {
                    showToastInUi(message);
                }
            });
        }
    }

    private void showToastInUi(String message) {
        if (toast != null) {
            toast.cancel();
        } else {
            if (context!=null){
                toast = Toast.makeText(context, message, Toast.LENGTH_SHORT);

            }else {
                toast = Toast.makeText(App.getApp(), message, Toast.LENGTH_SHORT);
            }
        }
        toast.show();

    }

    private boolean isOnMainThread() {
        return Looper.getMainLooper() == Looper.myLooper();
    }

    public Toaster withContext(Context context) {
        this.context = context;
        return this;
    }
}
