package com.example.food_planner.search.presenter;

import com.example.food_planner.home.presenter.HomePresenter;
import com.example.food_planner.home.view.HomeView;
import com.example.food_planner.model.network.area.AreaNetworkCallback;
import com.example.food_planner.model.network.category.CategoryNetworkCallback;
import com.example.food_planner.model.network.ingredient.IngredientsNetworkCallback;
import com.example.food_planner.model.network.meal.MealNetworkCallback;
import com.example.food_planner.model.pojos.area.Area;
import com.example.food_planner.model.pojos.category.Category;
import com.example.food_planner.model.pojos.ingredient.Ingredient;
import com.example.food_planner.model.pojos.meal.FavoriteMeal;
import com.example.food_planner.model.pojos.meal.Meal;
import com.example.food_planner.model.pojos.meal.PlannedMeal;
import com.example.food_planner.model.repositories.area.AreaRepository;
import com.example.food_planner.model.repositories.category.CategoryRepository;
import com.example.food_planner.model.repositories.ingredent.IngredientsRepository;
import com.example.food_planner.model.repositories.meal.MealsRepository;
import com.example.food_planner.search.view.SearchView;

import java.util.ArrayList;
import java.util.List;

public class SearchPresenterImp implements SearchPresenter, MealNetworkCallback , IngredientsNetworkCallback , CategoryNetworkCallback , AreaNetworkCallback {
    private final SearchView view;
    private final MealsRepository mealsRepo;
    private final IngredientsRepository ingredientsRepo;
    private final CategoryRepository categoryRepo;
    private final AreaRepository areaRepo;
    public SearchPresenterImp(MealsRepository mealsRepo, IngredientsRepository ingredientsRepo, CategoryRepository categoryRepo, AreaRepository areaRepo, SearchView view) {
        this.mealsRepo = mealsRepo;
        this.view = view;
        this.ingredientsRepo = ingredientsRepo;
        this.categoryRepo = categoryRepo;
        this.areaRepo = areaRepo;
    }

    @Override
    public void searchMealByName(String mealName) { mealsRepo.searchMealByName(this,mealName); }

    @Override
    public void getMealsByFirstLetter(String letter) { mealsRepo.getMealsByFirstLetter(this,letter); }

    @Override
    public void filterByMainIngredient(String ingredient) { mealsRepo.filterByIngredient(this,ingredient);}

    @Override
    public void getAllIngredients() { ingredientsRepo.getAllIngredients(this); }

    @Override
    public void getAllCategories() { categoryRepo.getAllCategories(this); }

    @Override
    public void getAllAreas() { areaRepo.getAllAreas(this); }

    @Override
    public void filterMealByCategory(String category) { mealsRepo.filterByCategory(this,category); }

    @Override
    public void filterByArea(String area) { mealsRepo.filterByArea(this,area); }

    @Override
    public void onSuccessMeal(List<Meal> meals) {
        view.ShowSearchMeals(meals);
    }

    @Override
    public void onFailureResult(String errorMSG) {
        view.ShowErrMsg(errorMSG);
    }

    @Override
    public void onSuccessArea(List<Area> areas) {
        view.ShowAreas(areas);
    }

    @Override
    public void onFailureArea(String errorMSG) {
        view.ShowErrMsg(errorMSG);
    }

    @Override
    public void onSuccessCategory(List<Category> categories) {
        view.ShowCategories(categories);
    }

    @Override
    public void onFailureCategory(String errorMSG) {
        view.ShowErrMsg(errorMSG);
    }

    @Override
    public void onSuccessIngredient(List<Ingredient> meals) {
        view.ShowIngredients(meals);
    }

    @Override
    public void onFailureIngredient(String errorMSG) {
        view.ShowErrMsg(errorMSG);
    }
}

//    @Override
//    public void onFailureResult(String errorMSG) { view.ShowErrMsg(errorMSG); }
//
//    @Override
//    public void onSuccessIngredient(List<Ingredient> ingredients) { view.ShowIngredients(ingredients); }
//
//    @Override
//    public void onFailureIngredient(String errorMSG) { view.ShowErrMsg(errorMSG); }
//
//    @Override
//    public void onSuccessCategory(List<Category> categories) { view.ShowCategories(categories); }
//
//    @Override
//    public void onFailureCategory(String errorMSG) { view.ShowErrMsg(errorMSG); }
//
//    @Override
//    public void onSuccessArea(List<Area> areas) { view.ShowAreas(areas); }
//
//    @Override
//    public void onFailureArea(String errorMSG) { view.ShowErrMsg(errorMSG); }

