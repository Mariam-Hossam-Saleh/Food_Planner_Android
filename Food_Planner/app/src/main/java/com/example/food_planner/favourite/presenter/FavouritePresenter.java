package com.example.food_planner.favourite.presenter;

import com.example.food_planner.model.pojos.meal.Meal;

import java.util.ArrayList;

public interface FavouritePresenter {
    public void addMealToFavourite(Meal meal);
    public void removeMealFromFavourite(Meal meal);
}
