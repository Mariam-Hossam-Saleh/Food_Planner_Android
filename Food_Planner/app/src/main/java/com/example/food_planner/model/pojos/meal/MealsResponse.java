package com.example.food_planner.model.pojos.meal;

import java.util.List;

public class MealsResponse {
    private List<Meal> meals;

    public List<Meal> getMeals() {
        return meals;
    }

    public void setMeals(List<Meal> meals) {
        this.meals = meals;
    }
}
