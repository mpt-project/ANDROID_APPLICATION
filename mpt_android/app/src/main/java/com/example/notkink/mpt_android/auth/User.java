package com.example.notkink.mpt_android.auth;

import com.google.gson.annotations.SerializedName;

/**
 * Created by Notkink on 10.12.2017.
 */

public class User {

    public static String TAG = "User";

    @SerializedName("email")
    public String email;

    @SerializedName("name")
    public String name;

    @SerializedName("password")
    public String password;
    public User(){}
    public User(String email, String name, String pass){
        this.email = email;
        this.name = name;
        this.password = pass;
    }

    @Override
    public String toString() {
        return "User{" +
                "email='" + email + '\'' +
                ", name='" + name + '\'' +
                ", password='" + password + '\'' +
                '}';
    }
}
