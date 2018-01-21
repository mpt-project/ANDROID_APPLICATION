package com.example.notkink.mpt_android.login;

import com.google.gson.annotations.SerializedName;

public class LoginResponse {

    @SerializedName("message")
    public String message;

    @SerializedName("id")
    public String id;

    public LoginResponse() {
    }

}
