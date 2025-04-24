package com.example.food_planner.home.presenter;

import com.example.food_planner.model.pojos.meal.Meal;

public interface HomePresenter {
    public void getMealsByFirstLetter(String letter);
    public void addToFavourite(Meal meal);

}
