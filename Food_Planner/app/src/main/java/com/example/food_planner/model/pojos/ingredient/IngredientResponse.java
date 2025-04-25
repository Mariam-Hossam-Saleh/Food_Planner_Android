package com.example.food_planner.model.pojos.ingredient;

import com.google.gson.annotations.SerializedName;
import java.util.List;

public class IngredientResponse {
    @SerializedName("meals")
    private List<Ingredient> ingredients;
    public List<Ingredient> getIngredients() {
        return ingredients;
    }
    public void setIngredients(List<Ingredient> meals) {
        this.ingredients = meals;
    }
}
