package com.example.notkink.mpt_android.receipes;

import com.google.gson.annotations.SerializedName;

public class SingleRecipe {

	@SerializedName("imgUrl")
	private String imgUrl;

	@SerializedName("descr")
	private String descr;

	@SerializedName("createdAt")
	private String createdAt;

	@SerializedName("image")
	private String image;

	@SerializedName("shop")
	private String shop;

	@SerializedName("expire")
	private String expire;

	@SerializedName("__v")
	private int V;

	@SerializedName("name")
	private String name;

	@SerializedName("_id")
	private String id;

	@SerializedName("receiptId")
	private String receiptId;

	@Override
 	public String toString(){
		return 
			"SingleRecipe{" +
			"imgUrl = '" + imgUrl + '\'' + 
			",descr = '" + descr + '\'' + 
			",createdAt = '" + createdAt + '\'' + 
			",image = '" + image + '\'' + 
			",shop = '" + shop + '\'' + 
			",expire = '" + expire + '\'' + 
			",__v = '" + V + '\'' + 
			",name = '" + name + '\'' + 
			",_id = '" + id + '\'' + 
			",receiptId = '" + receiptId + '\'' + 
			"}";
		}
}