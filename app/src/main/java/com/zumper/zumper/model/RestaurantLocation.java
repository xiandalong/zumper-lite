package com.zumper.zumper.model;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class RestaurantLocation implements Serializable {
    @SerializedName("lat")
    public float lat;
    @SerializedName("lng")
    public float lng;
}
