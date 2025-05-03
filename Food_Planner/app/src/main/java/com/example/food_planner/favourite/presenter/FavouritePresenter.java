package com.example.food_planner.favourite.presenter;

import androidx.lifecycle.LiveData;

import com.example.food_planner.model.pojos.meal.Meal;

import java.util.ArrayList;
import java.util.List;

public interface FavouritePresenter {
    public LiveData<List<Meal>> getFavouriteMeals();
    public void removeMealFromFavourite(Meal meal);
}
