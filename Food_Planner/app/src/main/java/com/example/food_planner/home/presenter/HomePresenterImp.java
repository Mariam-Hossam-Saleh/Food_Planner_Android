package com.example.food_planner.home.presenter;

import com.example.food_planner.home.view.HomeView;
import com.example.food_planner.model.network.category.CategoryNetworkCallback;
import com.example.food_planner.model.network.ingredient.IngredientsNetworkCallback;
import com.example.food_planner.model.network.meal.MealNetworkCallback;
import com.example.food_planner.model.pojos.category.Category;
import com.example.food_planner.model.pojos.ingredient.Ingredient;
import com.example.food_planner.model.pojos.meal.Meal;
import com.example.food_planner.model.repositories.category.CategoryRepository;
import com.example.food_planner.model.repositories.ingredent.IngredientsRepository;
import com.example.food_planner.model.repositories.meal.MealsRepository;

import java.util.ArrayList;
import java.util.List;

public class HomePresenterImp implements HomePresenter, MealNetworkCallback , IngredientsNetworkCallback , CategoryNetworkCallback {
    private HomeView view;
    private MealsRepository mealsRepo;
    private IngredientsRepository ingredientsRepo;
    private CategoryRepository categoryRepo;

    public HomePresenterImp(MealsRepository mealsRepo, IngredientsRepository ingredientsRepo, CategoryRepository categoryRepo, HomeView view) {
        this.mealsRepo = mealsRepo;
        this.view = view;
        this.ingredientsRepo = ingredientsRepo;
        this.categoryRepo = categoryRepo;
    }

    @Override
    public void getSingleRandomMeal() {
        mealsRepo.getSingleRandomMeal(this);
    }

    @Override
    public void getTenRandomMeals(ArrayList<Meal> meals) {
        mealsRepo.getTenRandomMeal(this,meals);
    }

    @Override
    public void searchMealByName(String mealName) {
        mealsRepo.searchMealByName(this,mealName);
    }

    @Override
    public void getMealsByFirstLetter(String letter) { mealsRepo.getMealsByFirstLetter(this,letter); }

    @Override
    public void filterByMainIngredient(String ingredient) { mealsRepo.filterByIngredient(this,ingredient);}

    @Override
    public void getAllIngredients() { ingredientsRepo.getAllIngredients(this); }

    @Override
    public void getAllCategories() { categoryRepo.getAllCategories(this); }

    @Override
    public void filterMealByCategory(String category) { mealsRepo.filterByCategory(this,category); }

    @Override
    public void addMealToFavourite(Meal meal) { mealsRepo.insertMeal(meal); }

    @Override
    public void removeMealFromFavourite(Meal meal) {
        mealsRepo.deleteMeal(meal);
    }

    @Override
    public void onSuccessResult(List<Meal> meals) {
        view.ShowMeals(meals);
    }

    @Override
    public void onFailureResult(String errorMSG) {
        view.ShowErrMsg(errorMSG);
    }

    @Override
    public void onSuccessIngredient(List<Ingredient> ingredients) { view.ShowIngredients(ingredients); }

    @Override
    public void onFailureIngredient(String errorMSG) { view.ShowErrMsg(errorMSG); }

    @Override
    public void onSuccessCategory(List<Category> categories) { view.ShowCategories(categories); }

    @Override
    public void onFailureCategory(String errorMSG) { view.ShowErrMsg(errorMSG); }
}
