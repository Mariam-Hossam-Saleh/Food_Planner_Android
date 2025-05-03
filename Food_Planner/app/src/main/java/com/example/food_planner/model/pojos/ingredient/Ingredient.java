package com.example.food_planner.model.pojos.ingredient;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

import com.google.gson.annotations.SerializedName;
@Entity(tableName = "ingredients_table")
public class Ingredient {

    @PrimaryKey
    @NonNull
    private String idIngredient;

    private String strIngredient;

    private String strDescription;

    private String strType;
    private String measure;

    public Ingredient(@NonNull String idIngredient, String strIngredient, String strDescription, String strType) {
        this.idIngredient = idIngredient;
        this.strIngredient = strIngredient;
        this.strDescription = strDescription;
        this.strType = strType;
    }

    @Ignore
    public Ingredient(String strIngredient, String measure){
        this.strIngredient = strIngredient;
        this.measure = measure;
    }

    public void setMeasure(String measure) {
        this.measure = measure;
    }

    public String getMeasure() {
        return measure;
    }

    @NonNull
    public String getIdIngredient() {
        return idIngredient;
    }

    public void setIdIngredient(@NonNull String idIngredient) {
        this.idIngredient = idIngredient;
    }

    public String getStrIngredient() {
        return strIngredient;
    }

    public void setStrIngredient(String strIngredient) {
        this.strIngredient = strIngredient;
    }

    public String getStrDescription() {
        return strDescription;
    }

    public void setStrDescription(String strDescription) {
        this.strDescription = strDescription;
    }

    public String getStrType() {
        return strType;
    }

    public void setStrType(String strType) {
        this.strType = strType;
    }
}
