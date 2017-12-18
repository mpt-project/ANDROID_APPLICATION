package com.example.notkink.mpt_android;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.NavUtils;
import android.util.DisplayMetrics;
import android.view.View;
import android.widget.Button;

/**
 * Created by Notkink on 01.12.2017.
 */

public class Pop extends Activity{
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.sign_up_window);

        DisplayMetrics dm  = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(dm);

        int width = dm.widthPixels;
        int height = dm.heightPixels;

        getWindow().setLayout((int)(width*0.9),(int)(height*0.7));

        Button signUp;
        signUp = findViewById(R.id.sign_up);

        signUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                NavUtils.navigateUpFromSameTask(Pop.this);
            }

        });
    }
}
