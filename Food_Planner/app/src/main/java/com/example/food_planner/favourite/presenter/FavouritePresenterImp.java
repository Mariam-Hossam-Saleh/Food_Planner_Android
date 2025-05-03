package com.example.food_planner.favourite.presenter;

import androidx.lifecycle.LiveData;

import com.example.food_planner.favourite.view.FavouriteView;
import com.example.food_planner.home.view.HomeView;
import com.example.food_planner.model.network.ingredient.IngredientsNetworkCallback;
import com.example.food_planner.model.network.meal.MealNetworkCallback;
import com.example.food_planner.model.pojos.ingredient.Ingredient;
import com.example.food_planner.model.pojos.meal.Meal;
import com.example.food_planner.model.repositories.ingredent.IngredientsRepository;
import com.example.food_planner.model.repositories.meal.MealsRepository;

import java.util.ArrayList;
import java.util.List;

public class FavouritePresenterImp implements FavouritePresenter, MealNetworkCallback {
    private FavouriteView view;
    private MealsRepository mealsRepo;

    public FavouritePresenterImp(MealsRepository mealsRepo , FavouriteView view) {
        this.mealsRepo = mealsRepo;
        this.view = view;
    }

    @Override
    public LiveData<List<Meal>> getFavouriteMeals() {
        return mealsRepo.getStoredMeals();
    }

    @Override
    public void removeMealFromFavourite(Meal meal) {
        mealsRepo.deleteMeal(meal);
    }


    @Override
    public void onSuccessMeal(List<Meal> meals) {
        view.ShowMeals(meals);
    }

    @Override
    public void onFailureResult(String errorMSG) {
        view.ShowErrMsg(errorMSG);
    }


}
