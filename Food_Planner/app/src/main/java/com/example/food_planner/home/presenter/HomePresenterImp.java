package com.example.food_planner.home.presenter;

import com.example.food_planner.home.view.HomeView;
import com.example.food_planner.model.network.NetworkCallback;
import com.example.food_planner.model.pojos.meal.Meal;
import com.example.food_planner.model.repositories.meal.MealsRepository;

import java.util.List;

public class HomePresenterImp implements HomePresenter, NetworkCallback {
    private HomeView view;
    private MealsRepository mealsRepo;

    public HomePresenterImp(MealsRepository mealsRepo, HomeView view) {
        this.mealsRepo = mealsRepo;
        this.view = view;
    }

    @Override
    public void getMealsByFirstLetter(String letter) {
        mealsRepo.getMealsByFirstLetter(this,letter);
    }

    @Override
    public void addToFavourite(Meal meal) {
        mealsRepo.insertMeal(meal);
    }


    @Override
    public void onSuccessResult(List<Meal> meals) {
        view.ShowMeals(meals);
    }

    @Override
    public void onFailureResult(String errorMSG) {
        view.ShowErrMsg(errorMSG);
    }
}
