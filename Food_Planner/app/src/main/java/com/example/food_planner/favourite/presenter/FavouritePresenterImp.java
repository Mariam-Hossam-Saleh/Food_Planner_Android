package com.example.food_planner.favourite.presenter;

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

public class FavouritePresenterImp implements FavouritePresenter, MealNetworkCallback , IngredientsNetworkCallback {
    private FavouriteView view;
    private MealsRepository mealsRepo;
    private IngredientsRepository ingredientsRepository;

    public FavouritePresenterImp(MealsRepository mealsRepo, IngredientsRepository ingredientsRepository, FavouriteView view) {
        this.mealsRepo = mealsRepo;
        this.view = view;
        this.ingredientsRepository = ingredientsRepository;
    }

    @Override
    public void addMealToFavourite(Meal meal) {
        mealsRepo.insertMeal(meal);
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

    @Override
    public void onSuccessIngredient(List<Ingredient> ingredients) { view.ShowIngredients(ingredients); }

    @Override
    public void onFailureIngredient(String errorMSG) {

    }
}
