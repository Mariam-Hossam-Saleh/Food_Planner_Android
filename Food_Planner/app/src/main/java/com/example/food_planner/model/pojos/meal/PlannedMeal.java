package com.example.food_planner.model.pojos.meal;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "planned_meals_table",primaryKeys = {"date", "plannedMealID"})
public class PlannedMeal extends Meal{
    @NonNull
    public String date;
    @NonNull
    public String plannedMealID;

    public PlannedMeal(){}

    public PlannedMeal(Meal meal) {
        this.plannedMealID = meal.idMeal;
        this.idMeal = meal.idMeal;
        this.strMeal = meal.strMeal;
        this.strMealAlternate = meal.strMealAlternate;
        this.strCategory = meal.strCategory;
        this.strInstructions = meal.strInstructions;
        this.strArea = meal.strArea;
        this.strMealThumb = meal.strMealThumb;
        this.strTags = meal.strTags;
        this.strYoutube = meal.strYoutube;
        this.strIngredient1 = meal.strIngredient1;
        this.strIngredient2 = meal.strIngredient2;
        this.strIngredient3 = meal.strIngredient3;
        this.strIngredient4 = meal.strIngredient4;
        this.strIngredient5 = meal.strIngredient5;
        this.strIngredient6 = meal.strIngredient6;
        this.strIngredient7 = meal.strIngredient7;
        this.strIngredient8 = meal.strIngredient8;
        this.strIngredient9 = meal.strIngredient9;
        this.strIngredient10 = meal.strIngredient10;
        this.strIngredient11 = meal.strIngredient11;
        this.strIngredient12 = meal.strIngredient12;
        this.strIngredient13 = meal.strIngredient13;
        this.strIngredient14 = meal.strIngredient14;
        this.strIngredient15 = meal.strIngredient15;
        this.strIngredient16 = meal.strIngredient16;
        this.strIngredient17 = meal.strIngredient17;
        this.strIngredient18 = meal.strIngredient18;
        this.strIngredient19 = meal.strIngredient19;
        this.strIngredient20 = meal.strIngredient20;
        this.strMeasure1 = meal.strMeasure1;
        this.strMeasure2 = meal.strMeasure2;
        this.strMeasure3 = meal.strMeasure3;
        this.strMeasure4 = meal.strMeasure4;
        this.strMeasure5 = meal.strMeasure5;
        this.strMeasure6 = meal.strMeasure6;
        this.strMeasure7 = meal.strMeasure7;
        this.strMeasure8 = meal.strMeasure8;
        this.strMeasure9 = meal.strMeasure9;
        this.strMeasure10 = meal.strMeasure10;
        this.strMeasure11 = meal.strMeasure11;
        this.strMeasure12 = meal.strMeasure12;
        this.strMeasure13 = meal.strMeasure13;
        this.strMeasure14 = meal.strMeasure14;
        this.strMeasure15 = meal.strMeasure15;
        this.strMeasure16 = meal.strMeasure16;
        this.strMeasure17 = meal.strMeasure17;
        this.strMeasure18 = meal.strMeasure18;
        this.strMeasure19 = meal.strMeasure19;
        this.strMeasure20 = meal.strMeasure20;
        this.strSource = meal.strSource;
        this.strImageSource = meal.strImageSource;
        this.strCreativeCommonsConfirmed = meal.strCreativeCommonsConfirmed;
        this.dateModified = meal.dateModified;
    }
    public PlannedMeal(@NonNull Meal meal,@NonNull String date){
        this.date = date;
        this.plannedMealID = meal.idMeal;
        this.idMeal = meal.idMeal;
        this.strMeal = meal.strMeal;
        this.strMealAlternate = meal.strMealAlternate;
        this.strCategory = meal.strCategory;
        this.strInstructions = meal.strInstructions;
        this.strArea = meal.strArea;
        this.strMealThumb = meal.strMealThumb;
        this.strTags = meal.strTags;
        this.strYoutube = meal.strYoutube;
        this.strIngredient1 = meal.strIngredient1;
        this.strIngredient2 = meal.strIngredient2;
        this.strIngredient3 = meal.strIngredient3;
        this.strIngredient4 = meal.strIngredient4;
        this.strIngredient5 = meal.strIngredient5;
        this.strIngredient6 = meal.strIngredient6;
        this.strIngredient7 = meal.strIngredient7;
        this.strIngredient8 = meal.strIngredient8;
        this.strIngredient9 = meal.strIngredient9;
        this.strIngredient10 = meal.strIngredient10;
        this.strIngredient11 = meal.strIngredient11;
        this.strIngredient12 = meal.strIngredient12;
        this.strIngredient13 = meal.strIngredient13;
        this.strIngredient14 = meal.strIngredient14;
        this.strIngredient15 = meal.strIngredient15;
        this.strIngredient16 = meal.strIngredient16;
        this.strIngredient17 = meal.strIngredient17;
        this.strIngredient18 = meal.strIngredient18;
        this.strIngredient19 = meal.strIngredient19;
        this.strIngredient20 = meal.strIngredient20;
        this.strMeasure1 = meal.strMeasure1;
        this.strMeasure2 = meal.strMeasure2;
        this.strMeasure3 = meal.strMeasure3;
        this.strMeasure4 = meal.strMeasure4;
        this.strMeasure5 = meal.strMeasure5;
        this.strMeasure6 = meal.strMeasure6;
        this.strMeasure7 = meal.strMeasure7;
        this.strMeasure8 = meal.strMeasure8;
        this.strMeasure9 = meal.strMeasure9;
        this.strMeasure10 = meal.strMeasure10;
        this.strMeasure11 = meal.strMeasure11;
        this.strMeasure12 = meal.strMeasure12;
        this.strMeasure13 = meal.strMeasure13;
        this.strMeasure14 = meal.strMeasure14;
        this.strMeasure15 = meal.strMeasure15;
        this.strMeasure16 = meal.strMeasure16;
        this.strMeasure17 = meal.strMeasure17;
        this.strMeasure18 = meal.strMeasure18;
        this.strMeasure19 = meal.strMeasure19;
        this.strMeasure20 = meal.strMeasure20;
        this.strSource = meal.strSource;
        this.strImageSource = meal.strImageSource;
        this.strCreativeCommonsConfirmed = meal.strCreativeCommonsConfirmed;
        this.dateModified = meal.dateModified;
    }
    public void setDate(@NonNull String date) {
        this.date = date;
    }
}
