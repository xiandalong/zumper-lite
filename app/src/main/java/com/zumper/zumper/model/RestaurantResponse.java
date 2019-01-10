package com.zumper.zumper.model;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

public class RestaurantResponse implements Serializable {
    @SerializedName("results")
    public List<Restaurant> restaurants;
}
