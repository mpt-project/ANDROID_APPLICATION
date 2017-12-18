package com.example.notkink.mpt_android.auth;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.Query;

/**
 * Created by Notkink on 10.12.2017.
 */

public interface AuthenticationApi {

    String endpoint = "https://api.idvdv.pl/";

    @POST("register")
    Call<UserResponse>
    registerUser(@Body UserRequest request);

    @POST("login")
    Call<UserResponse>
    loginUser(@Body UserRequest request);

    @GET("receipt")
    Call<List<Receipt>>
    getReceipes();

    @GET("receipt")
    Call<Receipt>
    getReceipes(@Query("id") String id);
}
