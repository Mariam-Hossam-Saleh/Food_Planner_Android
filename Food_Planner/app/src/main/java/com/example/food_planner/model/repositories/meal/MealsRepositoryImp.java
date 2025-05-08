package com.example.food_planner.model.repositories.meal;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

import androidx.lifecycle.LiveData;

import com.example.food_planner.model.database.mealsdatabase.MealLocalDataSource;
import com.example.food_planner.model.network.meal.MealNetworkCallback;
import com.example.food_planner.model.network.meal.MealRemoteDataSource;
import com.example.food_planner.model.pojos.meal.FavoriteMeal;
import com.example.food_planner.model.pojos.meal.Meal;
import com.example.food_planner.model.pojos.meal.PlannedMeal;
import com.google.common.reflect.TypeToken;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.gson.Gson;

import java.lang.reflect.Type;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class MealsRepositoryImp implements MealsRepository {
    private final MealRemoteDataSource remoteDataSource;
    private final MealLocalDataSource mealLocalDataSource;
    private final FirebaseAuth auth;
    private final FirebaseFirestore firestore;
    private static MealsRepositoryImp productsRepository = null;
    private final Context context;


    public static MealsRepositoryImp getInstance(Context context,MealRemoteDataSource remoteDataSource, MealLocalDataSource mealLocalDataSource) {
        if (productsRepository == null) {
            productsRepository = new MealsRepositoryImp( context,remoteDataSource, mealLocalDataSource);
        }
        return productsRepository;
    }

    private MealsRepositoryImp(Context context,MealRemoteDataSource remoteDataSource, MealLocalDataSource mealLocalDataSource) {
        this.context = context;
        this.remoteDataSource = remoteDataSource;
        this.mealLocalDataSource = mealLocalDataSource;
        this.auth = FirebaseAuth.getInstance();
        this.firestore = FirebaseFirestore.getInstance();
    }

    @Override
    public void searchMealByName(MealNetworkCallback mealNetworkCallback, String mealName) {
        remoteDataSource.makeNetworkCallForSearchMealByName(mealNetworkCallback, mealName);
    }

    @Override
    public void searchMealByID(MealNetworkCallback mealNetworkCallback, String mealID) {
        remoteDataSource.makeNetworkCallToFilterMealByID(mealNetworkCallback, mealID);
    }
    @Override
    public void getSingleRandomMeal(MealNetworkCallback mealNetworkCallback, Boolean isSingle) {
        if (isSingle &&  context != null) {
            SharedPreferences prefs = context.getSharedPreferences("MealPrefs", Context.MODE_PRIVATE);
            String storedDate = prefs.getString("KEY_DATE", "");
            String today = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(new Date());

            if (storedDate.equals(today)) {
                String mealsJson = prefs.getString("KEY_MEALS_JSON", "");
                if (!mealsJson.isEmpty()) {
                    try {
                        Gson gson = new Gson();
                        Type type = new TypeToken<List<Meal>>() {}.getType();
                        List<Meal> meals = gson.fromJson(mealsJson, type);
                        mealNetworkCallback.onSuccessMeal(meals);
                        return;
                    } catch (Exception e) {
                        Log.e("MealsRepo", "Failed to parse cached meals JSON", e);
                    }
                }
            }
        }

        remoteDataSource.makeNetworkCallForSingleRandomMeal(mealNetworkCallback, isSingle);
    }

//
//    @Override
//    public void getSingleRandomMeal(MealNetworkCallback mealNetworkCallback, Boolean isSingle) {
//        if(isSingle) {
//            SharedPreferences prefs = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
//            String storedDate = prefs.getString(KEY_DATE, "");
//            String today = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(new Date());
//
//            if (storedDate.equals(today)) {
//                String mealsJson = prefs.getString(KEY_MEALS_JSON, "");
//                if (!mealsJson.isEmpty()) {
//                    try {
//                        Gson gson = new Gson();
//                        Type type = new TypeToken<List<Meal>>() {
//                        }.getType();
//                        List<Meal> meals = gson.fromJson(mealsJson, type);
//                        callback.onSuccessResult(meals);
//                        return;
//                    } catch (Exception e) {
//                        // Parsing failed, proceed to fetch new meals
//                    }
//                }
//            }
//        }
//        remoteDataSource.makeNetworkCallForSingleRandomMeal(mealNetworkCallback, isSingle);
//    }

    @Override
    public void getMealsByFirstLetter(MealNetworkCallback mealNetworkCallback, String letter) {
        remoteDataSource.makeNetworkCallForMealsByFirstLetter(mealNetworkCallback, letter);
    }

    @Override
    public void filterByIngredient(MealNetworkCallback mealNetworkCallback, String ingredient) {
        remoteDataSource.makeNetworkCallToFilterMealByIngredient(mealNetworkCallback, ingredient);
    }

    @Override
    public void filterByCategory(MealNetworkCallback mealNetworkCallback, String category) {
        remoteDataSource.makeNetworkCallToFilterMealByCategory(mealNetworkCallback, category);
    }

    @Override
    public void filterByArea(MealNetworkCallback mealNetworkCallback, String area) {
        remoteDataSource.makeNetworkCallToFilterMealByArea(mealNetworkCallback, area);
    }

    @Override
    public void insertFavoriteMeal(FavoriteMeal meal) {
        mealLocalDataSource.insertFavoriteMeal(meal);
        if (auth.getCurrentUser() != null) {
            firestore.collection("users")
                    .document(auth.getCurrentUser().getUid())
                    .collection("favoriteMeals")
                    .document(meal.getIdMeal())
                    .set(meal)
                    .addOnFailureListener(e ->
                            Log.e("Firebase", "Error adding favorite to Firebase", e));
        }
    }

    @Override
    public void deleteFavoriteMeal(FavoriteMeal meal) {
        mealLocalDataSource.deleteFavoriteMeal(meal);
        if (auth.getCurrentUser() != null) {
            firestore.collection("users")
                    .document(auth.getCurrentUser().getUid())
                    .collection("favoriteMeals")
                    .document(meal.getIdMeal())
                    .delete()
                    .addOnFailureListener(e ->
                            Log.e("Firebase", "Error removing favorite from Firebase", e));
        }
    }

    @Override
    public void insertPlannedMeal(PlannedMeal meal) {
        mealLocalDataSource.insertPlannedMeal(meal);
        if (auth.getCurrentUser() != null) {
            firestore.collection("users")
                    .document(auth.getCurrentUser().getUid())
                    .collection("plannedMeals")
                    .document(meal.getIdMeal() + "_" + meal.date)
                    .set(meal)
                    .addOnFailureListener(e ->
                            Log.e("Firebase", "Error adding planned meal to Firebase", e));
        }
    }

    @Override
    public void deletePlannedMeal(PlannedMeal meal) {
        mealLocalDataSource.deletePlannedMeal(meal);
        if (auth.getCurrentUser() != null) {
            firestore.collection("users")
                    .document(auth.getCurrentUser().getUid())
                    .collection("plannedMeals")
                    .document(meal.getIdMeal() + "_" + meal.date)
                    .delete()
                    .addOnFailureListener(e ->
                            Log.e("Firebase", "Error removing planned meal from Firebase", e));
        }
    }

    @Override
    public LiveData<Boolean> isMealPlanned(PlannedMeal meal) {
        return mealLocalDataSource.isMealPlanned(meal);
    }

    @Override
    public LiveData<List<PlannedMeal>> getStoredPlannedMeals() {
        if (auth.getCurrentUser() != null) {
            syncPlannedMealsFromFirebase();
        }
        return mealLocalDataSource.getStoredPlannedMeals();
    }

    @Override
    public LiveData<List<PlannedMeal>> getPlannedMealsByDate(String date) {
        return mealLocalDataSource.getStoredPlannedMealsByDate(date);
    }

    @Override
    public LiveData<List<FavoriteMeal>> getStoredFavoriteMeals() {
        if (auth.getCurrentUser() != null) {
            syncFavoritesFromFirebase();
        }
        return mealLocalDataSource.getStoredFavoriteMeals();
    }

    @Override
    public LiveData<Boolean> isMealFavorite(FavoriteMeal meal) {
        return mealLocalDataSource.isMealFavorite(meal);
    }

    private void syncFavoritesFromFirebase() {
        firestore.collection("users")
                .document(auth.getCurrentUser().getUid())
                .collection("favoriteMeals")
                .get()
                .addOnSuccessListener(querySnapshot -> {
                    List<FavoriteMeal> firebaseFavorites = querySnapshot.toObjects(FavoriteMeal.class);
                    mealLocalDataSource.syncFavoriteMeals(firebaseFavorites);
                })
                .addOnFailureListener(e ->
                        Log.e("Firebase", "Error syncing favorites from Firebase", e));
    }

    private void syncPlannedMealsFromFirebase() {
        firestore.collection("users")
                .document(auth.getCurrentUser().getUid())
                .collection("plannedMeals")
                .get()
                .addOnSuccessListener(querySnapshot -> {
                    List<PlannedMeal> firebasePlanned = querySnapshot.toObjects(PlannedMeal.class);
                    mealLocalDataSource.syncPlannedMeals(firebasePlanned);
                })
                .addOnFailureListener(e ->
                        Log.e("Firebase", "Error syncing planned meals from Firebase", e));
    }
}