package com.example.notkink.mpt_android;

import android.app.Application;
import android.content.Context;
import android.os.Handler;
import android.os.Looper;

import com.example.notkink.mpt_android.upload.BillPleaseApiClient;

import uk.co.chrisjenx.calligraphy.CalligraphyConfig;


public class App extends Application {
    private static Context context;


    private BillPleaseApiClient billPleaseApiClient;
    public String userId;

    public BillPleaseApiClient getBillPleaseApiClient() {
        return billPleaseApiClient;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        context = this;
        CalligraphyConfig.initDefault(new CalligraphyConfig.Builder()
                .setDefaultFontPath("fonts/lato-light.ttf")
                .setFontAttrId(R.attr.fontPath)
                .build()
        );
        billPleaseApiClient = new BillPleaseApiClient("http://77.55.230.115:3000/");

    }

    public static App getApp() {
        return (App) context;
    }

    public static App getApp(Context context) {
        return (App) context.getApplicationContext();
    }

}
