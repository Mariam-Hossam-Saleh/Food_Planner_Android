package com.example.food_planner.model.pojos.ingredient;

import com.google.gson.annotations.SerializedName;
import java.util.List;

public class IngredientResponse {
    @SerializedName("meals")
    private List<Ingredient> meals;
    public List<Ingredient> getMeals() {
        return meals;
    }
    public void setMeals(List<Ingredient> meals) {
        this.meals = meals;
    }
}
