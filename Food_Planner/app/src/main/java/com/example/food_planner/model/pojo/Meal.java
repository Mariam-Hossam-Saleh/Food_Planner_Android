package com.example.food_planner.model.pojo;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "meals_table")
public class Meal {
    @PrimaryKey
    @NonNull
    private String idMeal;
    private String strMeal;
    private String strCategory;
    private String strMealThumb;
    private String strArea;
    private String strInstructions;

    private String strYoutube;
    public Meal(@NonNull String idMeal, String strMeal, String strCategory, String strMealThumb, String strArea, String strInstructions, String strYoutube) {
        this.idMeal = idMeal;
        this.strMeal = strMeal;
        this.strCategory = strCategory;
        this.strMealThumb = strMealThumb;
        this.strArea = strArea;
        this.strInstructions = strInstructions;
        this.strYoutube = strYoutube;
    }

    public String getStrCategory() {
        return strCategory;
    }

    public void setStrCategory(String strCategory) {
        this.strCategory = strCategory;
    }

    public String getStrArea() {
        return strArea;
    }

    public void setStrArea(String strArea) {
        this.strArea = strArea;
    }

    public String getStrMeal() {
        return strMeal;
    }

    public void setStrMeal(String strMeal) {
        this.strMeal = strMeal;
    }

    @NonNull
    public String getIdMeal() {
        return idMeal;
    }

    public void setIdMeal(@NonNull String idMeal) {
        this.idMeal = idMeal;
    }

    public String getStrMealThumb() {
        return strMealThumb;
    }

    public void setStrMealThumb(String strMealThumb) {
        this.strMealThumb = strMealThumb;
    }

    public String getStrYoutube() { return strYoutube; }

    public void setStrYoutube(String strYoutube) { this.strYoutube = strYoutube; }

    public String getStrInstructions() {return strInstructions;}

    public void setStrInstructions(String strInstructions) {this.strInstructions = strInstructions;}
}
