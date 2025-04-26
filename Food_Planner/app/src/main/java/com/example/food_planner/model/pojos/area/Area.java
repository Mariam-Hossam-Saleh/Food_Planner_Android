package com.example.food_planner.model.pojos.area;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "areas_table")
public class Area {
    @PrimaryKey
    @NonNull
    private String strArea;

    public Area(@NonNull String strArea) {
        this.strArea = strArea;
    }

    public String getStrArea() {
        return strArea;
    }
    public void setStrArea(String strArea) {
        this.strArea = strArea;
    }
}
