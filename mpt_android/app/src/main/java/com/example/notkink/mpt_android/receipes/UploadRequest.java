package com.example.notkink.mpt_android.receipes;

import com.google.gson.annotations.SerializedName;


public class UploadRequest{

	@SerializedName("imgUrl")
	public String imgUrl;

	@SerializedName("descr")
	public String descr;

	@SerializedName("shop")
	public String shop;

	@SerializedName("expire")
	public String expire;

	@SerializedName("name")
	public String name;

	@SerializedName("id")
	public String id;
}