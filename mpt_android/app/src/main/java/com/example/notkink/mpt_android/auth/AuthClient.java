package com.example.notkink.mpt_android.auth;

import java.util.List;

import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by Notkink on 10.12.2017.
 */

public class AuthClient {

    AuthenticationApi api;

    public AuthClient() {

        api = new Retrofit.Builder()
                .baseUrl(AuthenticationApi.endpoint)
                .addConverterFactory(GsonConverterFactory.create())
                .build()
                .create(AuthenticationApi.class);
    }

    public Call<UserResponse> registerUser(UserRequest userRequest) {
        return api.registerUser(userRequest);
    }
    public Call<UserResponse> loginUser(UserRequest userRequest) {
        return api.loginUser(userRequest);
    }

    public Call<Receipt> getRecipes(String id){
        return api.getReceipes(id);
    }

    public Call<List<Receipt>> getRecipes(){
        return api.getReceipes();
    }

}
