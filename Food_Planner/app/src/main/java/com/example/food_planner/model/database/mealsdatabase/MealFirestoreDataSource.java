package com.example.food_planner.model.database.mealsdatabase;

import android.util.Log;

import androidx.annotation.NonNull;

import com.example.food_planner.model.pojos.meal.FavoriteMeal;
import com.example.food_planner.model.pojos.meal.PlannedMeal;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MealFirestoreDataSource {
    private static final String TAG = "MealFirestoreDataSource";
    private final FirebaseFirestore db;
    private final FirebaseAuth mAuth;
    private static MealFirestoreDataSource instance = null;

    private MealFirestoreDataSource() {
        db = FirebaseFirestore.getInstance();
        mAuth = FirebaseAuth.getInstance();
    }

    public static MealFirestoreDataSource getInstance() {
        if (instance == null) {
            instance = new MealFirestoreDataSource();
        }
        return instance;
    }

    public Task<QuerySnapshot> getFavoriteMealsFromFirestore() {
        FirebaseUser user = mAuth.getCurrentUser();
        if (user == null) {
            Log.e(TAG, "No user signed in");
            return null;
        }
        String userId = user.getUid();
        return db.collection("users")
                .document(userId)
                .collection("favoriteMeals")
                .get();
    }

    public Task<QuerySnapshot> getPlannedMealsFromFirestore() {
        FirebaseUser user = mAuth.getCurrentUser();
        if (user == null) {
            Log.e(TAG, "No user signed in");
            return null;
        }
        String userId = user.getUid();
        return db.collection("users")
                .document(userId)
                .collection("plannedMeals")
                .get();
    }

    public void syncFavoriteMealsToFirestore(FavoriteMeal meal, boolean isInsert) {
        FirebaseUser user = mAuth.getCurrentUser();
        if (user == null) {
            Log.e(TAG, "No user signed in");
            return;
        }
        String userId = user.getUid();
        DocumentReference docRef = db.collection("users")
                .document(userId)
                .collection("favoriteMeals")
                .document(meal.favoriteMealID);

        if (isInsert) {
            Map<String, Object> mealData = mealToMap(meal);
            docRef.set(mealData)
                    .addOnSuccessListener(aVoid -> Log.d(TAG, "Favorite meal synced to Firestore: " + meal.favoriteMealID))
                    .addOnFailureListener(e -> Log.e(TAG, "Error syncing favorite meal", e));
        } else {
            docRef.delete()
                    .addOnSuccessListener(aVoid -> Log.d(TAG, "Favorite meal deleted from Firestore: " + meal.favoriteMealID))
                    .addOnFailureListener(e -> Log.e(TAG, "Error deleting favorite meal", e));
        }
    }

    public void syncPlannedMealsToFirestore(PlannedMeal meal, boolean isInsert) {
        FirebaseUser user = mAuth.getCurrentUser();
        if (user == null) {
            Log.e(TAG, "No user signed in");
            return;
        }
        if (meal.date == null || meal.plannedMealID == null) {
            Log.e(TAG, "Invalid meal data: date or plannedMealID is null");
            return;
        }
        String userId = user.getUid();
        // Convert date to yyyy-MM-dd if needed
        String formattedDate = meal.date.replace("/", "-"); // e.g., yyyy/MM/dd -> yyyy-MM-dd
        String docId = meal.plannedMealID + "_" + formattedDate;
        DocumentReference docRef = db.collection("users")
                .document(userId)
                .collection("plannedMeals")
                .document(docId);

        if (isInsert) {
            Map<String, Object> mealData = plannedMealToMap(meal);
            docRef.set(mealData)
                    .addOnSuccessListener(aVoid -> Log.d(TAG, "Planned meal synced to Firestore: " + meal.plannedMealID))
                    .addOnFailureListener(e -> Log.e(TAG, "Error syncing planned meal", e));
        } else {
            docRef.delete()
                    .addOnSuccessListener(aVoid -> Log.d(TAG, "Planned meal deleted from Firestore: " + meal.plannedMealID))
                    .addOnFailureListener(e -> Log.e(TAG, "Error deleting planned meal", e));
        }
    }

    private Map<String, Object> plannedMealToMap(PlannedMeal meal) {
        Map<String, Object> mealData = mealToMap(new FavoriteMeal(meal));
        mealData.put("date", meal.date != null ? meal.date.replace("/", "-") : "");
        mealData.put("idMeal", meal.getIdMeal());
        mealData.put("isPlanned", meal.isPlanned != null ? meal.isPlanned : false);
        return mealData;
    }

    private Map<String, Object> mealToMap(FavoriteMeal meal) {
        Map<String, Object> mealData = new HashMap<>();
        mealData.put("id", meal.favoriteMealID);
        mealData.put("strMeal", meal.getStrMeal());
        mealData.put("strCategory", meal.getStrCategory());
        mealData.put("strInstructions", meal.getStrInstructions());
        mealData.put("strArea", meal.getStrArea());
        mealData.put("strMealThumb", meal.getStrMealThumb());
        mealData.put("strTags", meal.getStrTags());
        mealData.put("strYoutube", meal.getStrYoutube());
        // Add ingredients and measures
        for (int i = 1; i <= 20; i++) {
            String ingredient = getField(meal, "strIngredient" + i);
            String measure = getField(meal, "strMeasure" + i);
            if (ingredient != null && !ingredient.isEmpty()) {
                mealData.put("strIngredient" + i, ingredient);
                mealData.put("strMeasure" + i, measure != null ? measure : "");
            }
        }
        mealData.put("strSource", meal.getStrSource());
        mealData.put("strImageSource", meal.getStrImageSource());
        mealData.put("strCreativeCommonsConfirmed", meal.getStrCreativeCommonsConfirmed());
        mealData.put("dateModified", meal.getDateModified());
        return mealData;
    }

    private String getField(Object obj, String fieldName) {
        try {
            return (String) obj.getClass().getField(fieldName).get(obj);
        } catch (NoSuchFieldException | IllegalAccessException e) {
            return null;
        }
    }
}