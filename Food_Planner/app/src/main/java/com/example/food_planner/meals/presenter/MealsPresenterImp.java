package com.example.food_planner.meals.presenter;

import androidx.lifecycle.LiveData;

import com.example.food_planner.meals.view.MealsView;
import com.example.food_planner.model.network.meal.MealNetworkCallback;
import com.example.food_planner.model.pojos.meal.FavoriteMeal;
import com.example.food_planner.model.pojos.meal.Meal;
import com.example.food_planner.model.pojos.meal.PlannedMeal;
import com.example.food_planner.model.repositories.meal.MealsRepository;

import java.util.List;

public class MealsPresenterImp implements MealsPresenter, MealNetworkCallback {
    private final MealsView view;
    private final MealsRepository mealsRepo;
    public MealsPresenterImp(MealsRepository mealsRepo, MealsView view) {
        this.mealsRepo = mealsRepo;
        this.view = view;
    }

    @Override
    public void filterByMainIngredient(String ingredient) { mealsRepo.filterByIngredient(this,ingredient);}

    @Override
    public void filterMealByCategory(String category) { mealsRepo.filterByCategory(this,category); }

    @Override
    public void filterByArea(String area) { mealsRepo.filterByArea(this,area); }

    @Override
    public void addMealToFavourite(FavoriteMeal meal) { mealsRepo.insertFavoriteMeal(meal); }

    @Override
    public void addMealToCalendar(PlannedMeal meal) { mealsRepo.insertPlannedMeal(meal); }

    @Override
    public void removeMealFromFavourite(FavoriteMeal meal) { mealsRepo.deleteFavoriteMeal(meal); }

    @Override
    public LiveData<List<FavoriteMeal>> getFavouriteMeals() {
        return mealsRepo.getStoredFavoriteMeals();
    }

    @Override
    public void removeMealFromCalendar(PlannedMeal meal) { mealsRepo.deletePlannedMeal(meal); }

    @Override
    public void onSuccessMeal(List<Meal> meals) {
        view.ShowMeals(meals);

//        if(isSingleMeal) {
//            view.ShowMeals(meals);
//            isSingleMeal = false ;
//        }
//         else {
//            if (!meals.isEmpty()) {
//                tenRandomMeals.add(meals.get(0));
//            }
//            if (tenRandomMeals.size() == mealsCount) {
//                view.ShowMeals(new ArrayList<>(tenRandomMeals));
//            }
//        }
    }

    @Override
    public void onFailureResult(String errorMSG) { view.ShowErrMsg(errorMSG); }

}
