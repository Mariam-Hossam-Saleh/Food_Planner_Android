package com.example.food_planner.model.network;

import com.example.food_planner.model.pojo.Meal;

import java.util.List;

public interface NetworkCallback {
    public void onSuccessResult(List<Meal> meals);
    public void onFailureResult(String errorMSG);
}
