package com.zumper.zumper.model;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class Geometry implements Serializable {
    @SerializedName("location")
    public RestaurantLocation location;
}
