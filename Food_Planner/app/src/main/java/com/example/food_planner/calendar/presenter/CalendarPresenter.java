package com.example.food_planner.calendar.presenter;

import androidx.lifecycle.LiveData;

import com.example.food_planner.model.pojos.meal.FavoriteMeal;
import com.example.food_planner.model.pojos.meal.PlannedMeal;

import java.util.List;

public interface CalendarPresenter {
    public LiveData<List<PlannedMeal>> getStoredPlannedMeals();
    public void removeMealFromCalendar(PlannedMeal meal);
}
