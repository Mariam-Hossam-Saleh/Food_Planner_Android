package com.example.food_planner.model.pojos.area;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class AreaResponse {
    @SerializedName("meals")
    private List<Area> meals;

    public List<Area> getMeals() {
        return meals;
    }

    public void setMeals(List<Area> meals) {
        this.meals = meals;
    }
}
