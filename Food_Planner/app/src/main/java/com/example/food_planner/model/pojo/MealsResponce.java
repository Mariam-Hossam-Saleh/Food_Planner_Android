package com.example.food_planner.model.pojo;

import java.util.List;

public class MealsResponce {
//    @SerializedName("products")
    private List<Meal> meals;

    public List<Meal> getMeals() {
        return meals;
    }

    public void setMeals(List<Meal> meals) {
        this.meals = meals;
    }
}
