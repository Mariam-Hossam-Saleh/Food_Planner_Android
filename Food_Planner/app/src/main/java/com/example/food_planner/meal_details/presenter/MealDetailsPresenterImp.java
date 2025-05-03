package com.example.food_planner.meal_details.presenter;

import com.example.food_planner.meal_details.view.MealDetailsView;
import com.example.food_planner.model.network.meal.MealNetworkCallback;
import com.example.food_planner.model.pojos.meal.Meal;
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
    public void addMealToFavourite(Meal meal) { mealsRepo.insertMeal(meal); }

    @Override
    public void removeMealFromFavourite(Meal meal) { mealsRepo.deleteMeal(meal); }

    @Override
    public void onSuccessMeal(List<Meal> meals) { view.ShowMealDetails(meals); }

    @Override
    public void onFailureResult(String errorMSG) { view.ShowErrMsg(errorMSG); }


}
