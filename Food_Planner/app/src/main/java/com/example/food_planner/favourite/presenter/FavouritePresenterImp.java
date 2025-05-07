package com.example.food_planner.favourite.presenter;

import androidx.lifecycle.LiveData;

import com.example.food_planner.favourite.view.FavouriteView;
import com.example.food_planner.model.network.meal.FavoriteMealNetworkCallback;
import com.example.food_planner.model.pojos.meal.FavoriteMeal;
import com.example.food_planner.model.pojos.meal.PlannedMeal;
import com.example.food_planner.model.repositories.meal.MealsRepository;

import java.util.List;

public class FavouritePresenterImp implements FavouritePresenter, FavoriteMealNetworkCallback {
    private FavouriteView view;
    private MealsRepository mealsRepo;

    public FavouritePresenterImp(MealsRepository mealsRepo , FavouriteView view) {
        this.mealsRepo = mealsRepo;
        this.view = view;
    }

    @Override
    public LiveData<List<FavoriteMeal>> getFavouriteMeals() {
        return mealsRepo.getStoredFavoriteMeals();
    }

    @Override
    public void removeMealFromFavourite(FavoriteMeal meal) {
        mealsRepo.deleteFavoriteMeal(meal);
    }

    @Override
    public void addMealToCalendar(PlannedMeal meal) {
        mealsRepo.insertPlannedMeal(meal);
    }


    @Override
    public void onSuccessFavoriteMeal(List<FavoriteMeal> meals) {
        view.ShowFavoriteMeals(meals);
    }

    @Override
    public void onFailureFavoriteResult(String errorMSG) {
        view.ShowErrMsg(errorMSG);
    }
}
