package com.zumper.zumper.model;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class Restaurant implements Serializable {
    @SerializedName("id")
    public String id;
    @SerializedName("name")
    public String name;
    @SerializedName("geometry")
    public Geometry geometry;
}
