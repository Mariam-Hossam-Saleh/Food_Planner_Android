package com.example.food_planner.calendar.presenter;

import androidx.lifecycle.LiveData;

import com.example.food_planner.calendar.view.CalendarView;
import com.example.food_planner.favourite.presenter.FavouritePresenter;
import com.example.food_planner.favourite.view.FavouriteView;
import com.example.food_planner.model.network.meal.FavoriteMealNetworkCallback;
import com.example.food_planner.model.network.meal.PlannedMealNetworkCallback;
import com.example.food_planner.model.pojos.meal.FavoriteMeal;
import com.example.food_planner.model.pojos.meal.PlannedMeal;
import com.example.food_planner.model.repositories.meal.MealsRepository;

import java.util.Calendar;
import java.util.List;

public class CalendarPresenterImp implements CalendarPresenter, PlannedMealNetworkCallback {
    private final CalendarView view;
    private final MealsRepository mealsRepo;

    public CalendarPresenterImp(MealsRepository mealsRepo , CalendarView view) {
        this.mealsRepo = mealsRepo;
        this.view = view;
    }

    @Override
    public LiveData<List<PlannedMeal>> getStoredPlannedMeals() {
        return mealsRepo.getStoredPlannedMeals();
    }

    @Override
    public LiveData<List<PlannedMeal>> getPlannedMealsByDate(String date) {
        return mealsRepo.getPlannedMealsByDate(date);
    }

    @Override
    public void removeMealFromCalendar(PlannedMeal meal) {
        mealsRepo.deletePlannedMeal(meal);
    }

    @Override
    public LiveData<Boolean> isMealFavorite(FavoriteMeal meal) {
        return mealsRepo.isMealFavorite(meal);
    }

    @Override
    public void onSuccessPlannedMeal(List<PlannedMeal> meals) {
        view.ShowPlannedMeals(meals);
    }

    @Override
    public void onFailurePlannedResult(String errorMSG) {
        view.ShowErrMsg(errorMSG);
    }
}
