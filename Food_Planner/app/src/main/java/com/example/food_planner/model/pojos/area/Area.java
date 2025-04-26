package com.example.food_planner.model.pojos.area;

import androidx.room.Entity;

@Entity(tableName = "areas_table")
public class Area {
    private String strArea;
    public String getStrArea() {
        return strArea;
    }
    public void setStrArea(String strArea) {
        this.strArea = strArea;
    }
}
