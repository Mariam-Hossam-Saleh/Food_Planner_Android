package com.example.food_planner.model.database.mealsdatabase;

import android.content.Context;
import android.util.Log;

import androidx.lifecycle.LiveData;

import com.example.food_planner.model.pojos.meal.FavoriteMeal;
import com.example.food_planner.model.pojos.meal.PlannedMeal;
import com.google.firebase.firestore.DocumentSnapshot;

import java.util.ArrayList;
import java.util.List;

public class MealLocalDataSourceImp implements MealLocalDataSource {
    private final FavoriteMealDAO favoriteMealDAO;
    private final PlannedMealDAO plannedMealDAO;
    private static MealLocalDataSourceImp localDataSourceImp = null;
    private final LiveData<List<FavoriteMeal>> storedFavoriteMeals;
    private final LiveData<List<PlannedMeal>> storedPlannedMeals;
    private final MealFirestoreDataSource firestoreDataSource;

    private MealLocalDataSourceImp(Context context) {
        FavoriteMealDatabase favoriteMealDatabase = FavoriteMealDatabase.getInstance(context.getApplicationContext());
        PlannedMealDatabase plannedMealDatabase = PlannedMealDatabase.getInstance(context.getApplicationContext());
        favoriteMealDAO = favoriteMealDatabase.getMealDAO();
        plannedMealDAO = plannedMealDatabase.getMealDAO();
        storedFavoriteMeals = favoriteMealDAO.getAllFavoriteMeals();
        storedPlannedMeals = plannedMealDAO.getPlannedMeals();
        firestoreDataSource = MealFirestoreDataSource.getInstance();
    }

    public static MealLocalDataSourceImp getInstance(Context context) {
        if (localDataSourceImp == null) {
            localDataSourceImp = new MealLocalDataSourceImp(context);
        }
        return localDataSourceImp;
    }

    @Override
    public void insertFavoriteMeal(FavoriteMeal meal) {
        new Thread(() -> {
            favoriteMealDAO.insertFavoriteMeal(meal);
            firestoreDataSource.syncFavoriteMealsToFirestore(meal, true);
        }).start();
    }

    @Override
    public void deleteFavoriteMeal(FavoriteMeal meal) {
        new Thread(() -> {
            favoriteMealDAO.deleteFavoriteMeal(meal);
            firestoreDataSource.syncFavoriteMealsToFirestore(meal, false);
        }).start();
    }

    @Override
    public LiveData<Boolean> isMealFavorite(FavoriteMeal meal) {
        return favoriteMealDAO.isMealFavorite(meal.idMeal);
    }

    @Override
    public LiveData<List<FavoriteMeal>> getStoredFavoriteMeals() {
        return storedFavoriteMeals;
    }

    @Override
    public void insertPlannedMeal(PlannedMeal meal) {
        new Thread(() -> {
            plannedMealDAO.insertPlannedMeal(meal);
            firestoreDataSource.syncPlannedMealsToFirestore(meal, true);
        }).start();
    }

    @Override
    public void deletePlannedMeal(PlannedMeal meal) {
        new Thread(() -> {
            plannedMealDAO.deletePlannedMeal(meal.date, meal.plannedMealID);
            firestoreDataSource.syncPlannedMealsToFirestore(meal, false);
        }).start();
    }

    @Override
    public LiveData<Boolean> isMealPlanned(PlannedMeal meal) {
        return plannedMealDAO.isMealPlanned(meal.idMeal);
    }

    @Override
    public LiveData<List<PlannedMeal>> getStoredPlannedMeals() {
        return storedPlannedMeals;
    }

    @Override
    public LiveData<List<PlannedMeal>> getStoredPlannedMealsByDate(String date) {
        return plannedMealDAO.getPlannedMealsByDate(date);
    }

    @Override
    public void syncFavoriteMeals(List<FavoriteMeal> favoriteMeals) {
        new Thread(() -> {
//            favoriteMealDAO.deleteAllFavoriteMeals();
            for (FavoriteMeal meal : favoriteMeals) {
                favoriteMealDAO.insertFavoriteMeal(meal);
            }
        }).start();
    }

    @Override
    public void syncPlannedMeals(List<PlannedMeal> plannedMeals) {
        new Thread(() -> {
            plannedMealDAO.deleteAllPlannedMeals();
            for (PlannedMeal meal : plannedMeals) {
                plannedMealDAO.insertPlannedMeal(meal);
            }
        }).start();
    }
    @Override
    public void clearAllFavoriteMeals() {
        new Thread(() -> {
            favoriteMealDAO.deleteAllFavoriteMeals();
            Log.d("LocalDataSource", "All favorite meals cleared");
        }).start();
    }

    @Override
    public void clearAllPlannedMeals() {
        new Thread(() -> {
            plannedMealDAO.deleteAllPlannedMeals();
            Log.d("LocalDataSource", "All planned meals cleared");
        }).start();
    }

    public void syncWithFirestore() {
        // Sync favorite meals
        firestoreDataSource.getFavoriteMealsFromFirestore()
                .addOnSuccessListener(querySnapshot -> {
                    List<FavoriteMeal> favoriteMeals = new ArrayList<>();
                    for (DocumentSnapshot doc : querySnapshot.getDocuments()) {
                        FavoriteMeal meal = doc.toObject(FavoriteMeal.class);
                        if (meal != null) {
                            meal.favoriteMealID = doc.getId();
                            favoriteMeals.add(meal);
                        }
                    }
                    syncFavoriteMeals(favoriteMeals);
                    Log.d("MealLocalDataSource", "Synced " + favoriteMeals.size() + " favorite meals from Firestore");
                })
                .addOnFailureListener(e -> Log.e("MealLocalDataSource", "Error syncing favorite meals from Firestore", e));

        // Sync planned meals
        firestoreDataSource.getPlannedMealsFromFirestore()
                .addOnSuccessListener(querySnapshot -> {
                    List<PlannedMeal> plannedMeals = new ArrayList<>();
                    for (DocumentSnapshot doc : querySnapshot.getDocuments()) {
                        PlannedMeal meal = doc.toObject(PlannedMeal.class);
                        if (meal != null) {
                            meal.plannedMealID = doc.getId().split("_")[0];
                            meal.date = doc.getString("date");
                            plannedMeals.add(meal);
                        }
                    }
                    syncPlannedMeals(plannedMeals);
                    Log.d("MealLocalDataSource", "Synced " + plannedMeals.size() + " planned meals from Firestore");
                })
                .addOnFailureListener(e -> Log.e("MealLocalDataSource", "Error syncing planned meals from Firestore", e));
    }
}