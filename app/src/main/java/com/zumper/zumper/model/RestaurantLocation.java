package com.zumper.zumper.model;

import com.google.gson.annotations.SerializedName;

public class RestaurantLocation {
    @SerializedName("lat")
    public float lat;
    @SerializedName("lng")
    public float lng;
}
