package com.example.notkink.mpt_android.upload;

import com.example.notkink.mpt_android.auth.Receipt;
import com.example.notkink.mpt_android.login.LoginRequest;
import com.example.notkink.mpt_android.receipes.ReceiptsRequest;
import com.example.notkink.mpt_android.register.RegisterRequest;

import java.util.List;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;

public interface BillPleaseApi {

    @Multipart
    @POST("image/")
    Call<ResponseBody> uploadImage(@Part("image") RequestBody description,
                                   @Part MultipartBody.Part body);

    @POST("image/")
    Call<ResponseBody> uploadPhoto(@Body RequestBody photo);

    @POST("image/")
    Call<ResponseBody> uploadBase64Photo(@Body String photo);

    @POST("register/")
    Call<ResponseBody> registerUser(@Body RegisterRequest request);

    @POST("login/")
    Call<ResponseBody> loginUser(@Body LoginRequest request);


    @POST("receipts/")
    Call<List<Receipt>> getReceipts(@Body ReceiptsRequest request);

}
