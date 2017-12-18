package com.example.notkink.mpt_android.auth;

import android.util.Log;
import android.widget.EditText;

import com.example.notkink.mpt_android.toast.Toaster;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.HttpException;
import retrofit2.Response;

/**
 * Created by Notkink on 10.12.2017.
 */

public class Example {

    public static final String TAG = Example.class.getSimpleName();

    void foo() {

        AuthClient client = new AuthClient();

        UserRequest r = new UserRequest( new User("janusz@gmail.com", "Janusz", "12343"));

        Log.d(TAG, "foo: " + r.user);
        client.registerUser(r).enqueue(new Callback<UserResponse>() {
            @Override
            public void onResponse(Call<UserResponse> call, Response<UserResponse> response) {
                UserResponse respresonse = response.body();

            }

            @Override
            public void onFailure(Call<UserResponse> call, Throwable t) {
                String errorMessage = t.getMessage();
                if (t instanceof HttpException) {
                    HttpException exception = (HttpException) t;
                    int code = exception.code();
                }
            }
        });
    }
}
