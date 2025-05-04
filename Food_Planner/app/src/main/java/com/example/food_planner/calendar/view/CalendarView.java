package com.example.food_planner.calendar.view;

import com.example.food_planner.model.pojos.meal.FavoriteMeal;
import com.example.food_planner.model.pojos.meal.PlannedMeal;

import java.util.List;

public interface CalendarView {
    public void ShowPlannedMeals(List<PlannedMeal> mealList);
    public void ShowErrMsg(String error);
}
