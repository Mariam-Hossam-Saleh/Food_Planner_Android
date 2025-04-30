package com.example.food_planner.home.presenter;

import com.example.food_planner.home.view.HomeView;
import com.example.food_planner.model.network.area.AreaNetworkCallback;
import com.example.food_planner.model.network.category.CategoryNetworkCallback;
import com.example.food_planner.model.network.ingredient.IngredientsNetworkCallback;
import com.example.food_planner.model.network.meal.MealNetworkCallback;
import com.example.food_planner.model.pojos.area.Area;
import com.example.food_planner.model.pojos.category.Category;
import com.example.food_planner.model.pojos.ingredient.Ingredient;
import com.example.food_planner.model.pojos.meal.Meal;
import com.example.food_planner.model.repositories.area.AreaRepository;
import com.example.food_planner.model.repositories.category.CategoryRepository;
import com.example.food_planner.model.repositories.ingredent.IngredientsRepository;
import com.example.food_planner.model.repositories.meal.MealsRepository;

import java.util.ArrayList;
import java.util.List;

public class HomePresenterImp implements HomePresenter, MealNetworkCallback , IngredientsNetworkCallback , CategoryNetworkCallback , AreaNetworkCallback {
    private final HomeView view;
    private final MealsRepository mealsRepo;
    private final IngredientsRepository ingredientsRepo;
    private final CategoryRepository categoryRepo;
    private final AreaRepository areaRepo;
    private static final int mealsCount = 10;
    private Boolean isSingleMeal = true;
    private final List<Meal> tenRandomMeals = new ArrayList<>();

    public HomePresenterImp(MealsRepository mealsRepo, IngredientsRepository ingredientsRepo, CategoryRepository categoryRepo, AreaRepository areaRepo, HomeView view) {
        this.mealsRepo = mealsRepo;
        this.view = view;
        this.ingredientsRepo = ingredientsRepo;
        this.categoryRepo = categoryRepo;
        this.areaRepo = areaRepo;
    }

    @Override
    public void getSingleRandomMeal() {
        isSingleMeal = true ;
        mealsRepo.getSingleRandomMeal(this,true);
    }

    @Override
    public void getTenRandomMeals() {
        tenRandomMeals.clear();
        for(int itr = 0 ; itr < mealsCount ; itr++){
            mealsRepo.getSingleRandomMeal(this,false);
        }
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
    public void addMealToFavourite(Meal meal) { mealsRepo.insertMeal(meal); }

    @Override
    public void removeMealFromFavourite(Meal meal) { mealsRepo.deleteMeal(meal); }

    @Override
    public void onSuccessMeal(List<Meal> meals) {
        if(isSingleMeal) {
            view.ShowMeals(meals);
            isSingleMeal = false ;
        }
         else {
            if (!meals.isEmpty()) {
                tenRandomMeals.add(meals.get(0));
            }
            if (tenRandomMeals.size() == mealsCount) {
                view.ShowMeals(new ArrayList<>(tenRandomMeals));
            }
        }
    }

    @Override
    public void onFailureResult(String errorMSG) { view.ShowErrMsg(errorMSG); }

    @Override
    public void onSuccessIngredient(List<Ingredient> ingredients) { view.ShowIngredients(ingredients); }

    @Override
    public void onFailureIngredient(String errorMSG) { view.ShowErrMsg(errorMSG); }

    @Override
    public void onSuccessCategory(List<Category> categories) { view.ShowCategories(categories); }

    @Override
    public void onFailureCategory(String errorMSG) { view.ShowErrMsg(errorMSG); }

    @Override
    public void onSuccessArea(List<Area> areas) { view.ShowAreas(areas); }

    @Override
    public void onFailureArea(String errorMSG) { view.ShowErrMsg(errorMSG); }
}
