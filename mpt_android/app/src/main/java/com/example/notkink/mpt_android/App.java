package com.example.notkink.mpt_android;

import android.app.Application;
import android.content.Context;
import android.os.Handler;
import android.os.Looper;

import com.example.notkink.mpt_android.upload.BillPleaseApiClient;


public class App extends Application {
    private static Context context;


    private BillPleaseApiClient billPleaseApiClient;
    private Handler handler;


    public BillPleaseApiClient getBillPleaseApiClient() {
        return billPleaseApiClient;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        context = this;

        billPleaseApiClient = new BillPleaseApiClient("http://192.168.1.9:3000/");
        handler = new Handler(Looper.getMainLooper());
    }

    public static App getApp() {
        return (App) context;
    }

    public static App getApp(Context context) {
        return (App) context.getApplicationContext();
    }

}
