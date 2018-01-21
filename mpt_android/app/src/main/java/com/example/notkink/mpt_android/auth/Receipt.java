package com.example.notkink.mpt_android.auth;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class Receipt implements Serializable {

    public static String TAG = Receipt.class.getSimpleName();
    @SerializedName("receiptId")
    public String id;

    @SerializedName("name")
    public String name;

    @SerializedName("descr")
    public String descr;

    @SerializedName("shop")
    public String shop;

    @SerializedName("createdAt")
    public String createdAt;

    @SerializedName("expire")
    public String expire;

    @SerializedName("image")
    public String url;
}
