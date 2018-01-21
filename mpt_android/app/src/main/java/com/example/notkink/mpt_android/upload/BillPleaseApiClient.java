package com.example.notkink.mpt_android.upload;

import android.graphics.Bitmap;
import android.support.annotation.Nullable;
import android.util.Base64;

import com.example.notkink.mpt_android.App;
import com.example.notkink.mpt_android.auth.Receipt;
import com.example.notkink.mpt_android.login.LoginRequest;
import com.example.notkink.mpt_android.receipes.ReceiptsRequest;
import com.example.notkink.mpt_android.register.RegisterRequest;
import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.concurrent.TimeUnit;

import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Call;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class BillPleaseApiClient {

    private final BillPleaseApi api;

    public BillPleaseApiClient(String endpoint) {

        api = new Retrofit.Builder()
                .baseUrl(endpoint)
                .addConverterFactory(GsonConverterFactory.create())
                .client(buildClient())
                .build()
                .create(BillPleaseApi.class);
    }

    private OkHttpClient buildClient() {
        int timeoutInSeconds = 20;

        HttpLoggingInterceptor loggingInterceptor = new HttpLoggingInterceptor();
        loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);

        OkHttpClient.Builder builder = new OkHttpClient.Builder()
                .readTimeout(timeoutInSeconds, TimeUnit.SECONDS)
                .connectTimeout(timeoutInSeconds, TimeUnit.SECONDS);

        builder.addNetworkInterceptor(loggingInterceptor);

        return builder.build();
    }

    public Call<ResponseBody> uploadPhoto(Bitmap file) {

        System.out.println("uploadPhoto");

//        RequestBody requestFile = RequestBody.create(MediaType.parse("image/*"), file);
//
//        MultipartBody.Part body = MultipartBody.Part.createFormData("image",
//                file.getName(), requestFile);
//
//        RequestBody description = RequestBody.create(okhttp3.MultipartBody.FORM, "image");
//
//
        byte[] data = FileUtils.toBinary(file);

        RequestBody requestBody = RequestBody
                .create(MediaType.parse("application/octet-stream"), data);

        return api.uploadPhoto(requestBody);
    }


    public Call<ResponseBody> uploadFile(File image) {

        RequestBody requestBody = RequestBody
                .create(MediaType.parse("image/*"), image);

        return api.uploadPhoto(requestBody);

    }

    public Call<ResponseBody> uploadBase64Photo(Bitmap image) {

        String s = getBase64FromBitmap(image);

        return api.uploadBase64Photo(s);

    }

    public static String getBase64FromBitmap(Bitmap bitmap) {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 30, byteArrayOutputStream);
        byte[] byteArray = byteArrayOutputStream.toByteArray();
        return Base64.encodeToString(byteArray, Base64.DEFAULT);
    }

    public Call<ResponseBody> registerUser(RegisterRequest registerRequest) {

        return api.registerUser(registerRequest);
    }


    public Call<ResponseBody> loginUser(LoginRequest loginRequest) {

        return api.loginUser(loginRequest);
    }

    public Call<List<Receipt>> getReceipts() {
        String id = App.getApp().userId;
        return api.getReceipts(new ReceiptsRequest(id));
    }


    @Nullable
    public <T> T getResponse(Response<ResponseBody> response, Class<T> klazz) {
        ResponseBody body = getResponseBody(response);

        int code = response.code();
        System.out.println("code: " + code);

        if (body != null) {
            try {
                String json = body.string();
                T object = new Gson().fromJson(json, klazz);
                return object;

            } catch (IOException | JsonSyntaxException ignored) {
                System.err.println(ignored);
            }
        }
        return null;
    }

    private ResponseBody getResponseBody(Response<ResponseBody> response) {
        ResponseBody responseBody = response.body();
        if (responseBody == null) {
            responseBody = response.errorBody();
        }
        return responseBody;
    }

}
