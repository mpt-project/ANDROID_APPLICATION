package com.example.notkink.mpt_android.receipes;

import com.google.gson.annotations.SerializedName;

public class ReceiptsRequest {

    @SerializedName("id")
    public String id;

    @SerializedName("_id")
    public String _id;

    @SerializedName("userId")
    public String userId;

    public ReceiptsRequest(String id) {
        this.id = id;
        _id = userId = id;

        System.out.println("ReceiptsRequest: " + id);
    }

    public ReceiptsRequest() {
    }
}
