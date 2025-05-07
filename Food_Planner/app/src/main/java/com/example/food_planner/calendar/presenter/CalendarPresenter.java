package com.example.food_planner.calendar.presenter;

import androidx.lifecycle.LiveData;

import com.example.food_planner.model.pojos.meal.FavoriteMeal;
import com.example.food_planner.model.pojos.meal.PlannedMeal;

import java.util.List;

public interface CalendarPresenter {
     LiveData<List<PlannedMeal>> getStoredPlannedMeals();
     LiveData<List<PlannedMeal>> getPlannedMealsByDate(String date);
     void removeMealFromCalendar(PlannedMeal meal);
     LiveData<Boolean> isMealFavorite(FavoriteMeal meal);
}
