package com.example.notkink.mpt_android.auth;

import com.google.gson.annotations.SerializedName;

/**
 * Created by Notkink on 10.12.2017.
 */

public class UserRequest {

    @SerializedName("user")
    public User user;

    public UserRequest(User user) {
        this.user = user;
    }

    @Override
    public String toString() {
        return "UserRequest{" +
                "user=" + user +
                '}';
    }
}
