package com.example.food_planner.model.pojos.area;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

import com.google.gson.annotations.SerializedName;

@Entity(tableName = "areas_table")
public class Area {
    @PrimaryKey
    @NonNull
    @SerializedName("strArea")
    private String strArea;

    public Area(@NonNull String strArea) {
        this.strArea = strArea;
    }
    public String getStrArea() {
        return strArea;
    }

}
