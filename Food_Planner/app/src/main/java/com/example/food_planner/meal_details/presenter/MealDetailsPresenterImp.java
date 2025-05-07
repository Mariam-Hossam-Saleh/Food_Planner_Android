package com.example.food_planner.meal_details.presenter;

import androidx.lifecycle.LiveData;

import com.example.food_planner.meal_details.view.MealDetailsView;
import com.example.food_planner.model.network.meal.MealNetworkCallback;
import com.example.food_planner.model.pojos.meal.FavoriteMeal;
import com.example.food_planner.model.pojos.meal.Meal;
import com.example.food_planner.model.pojos.meal.PlannedMeal;
import com.example.food_planner.model.repositories.meal.MealsRepository;

import java.util.List;

public class MealDetailsPresenterImp implements MealDetailsPresenter, MealNetworkCallback {
    private final MealDetailsView view;
    private final MealsRepository mealsRepo;

    public MealDetailsPresenterImp(MealsRepository mealsRepo, MealDetailsView view) {
        this.mealsRepo = mealsRepo;
        this.view = view;
    }

    @Override
    public void searchMealByID(String mealID) { mealsRepo.searchMealByID(this, mealID); }

    @Override
    public void addMealToFavourite(FavoriteMeal meal) { mealsRepo.insertFavoriteMeal(meal); }

    @Override
    public void removeMealFromFavourite(FavoriteMeal meal) { mealsRepo.deleteFavoriteMeal(meal); }



    @Override
    public void addMealToCalendar(PlannedMeal meal) {
        mealsRepo.insertPlannedMeal(meal);
    }

    @Override
    public LiveData<Boolean> isMealFavorite(FavoriteMeal meal) {
        return mealsRepo.isMealFavorite(meal);
    }

    @Override
    public LiveData<Boolean> isMealPlanned(PlannedMeal meal) {
        return mealsRepo.isMealPlanned(meal);
    }

    @Override
    public void onSuccessMeal(List<Meal> meals) { view.ShowMealDetails(meals); }

    @Override
    public void onFailureResult(String errorMSG) { view.ShowErrMsg(errorMSG); }


}
