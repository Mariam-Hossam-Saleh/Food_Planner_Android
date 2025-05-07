package com.example.food_planner.utils.adapters;

import android.annotation.SuppressLint;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.resource.bitmap.CenterCrop;
import com.bumptech.glide.load.resource.bitmap.RoundedCorners;
import com.bumptech.glide.request.RequestOptions;
import com.example.food_planner.R;
import com.example.food_planner.model.pojos.meal.FavoriteMeal;
import com.example.food_planner.model.pojos.meal.Meal;
import com.example.food_planner.model.pojos.meal.PlannedMeal;
import com.example.food_planner.utils.mutual_interfaces.OnCalendarIconClickListener;
import com.example.food_planner.utils.mutual_interfaces.OnFavIconClickListener;
import com.example.food_planner.utils.mutual_interfaces.OnMealClickListener;
import com.example.food_planner.utils.mutual_interfaces.SetIconsStatus;

import java.util.ArrayList;
import java.util.List;

public class MealAdapter extends RecyclerView.Adapter<MealAdapter.ViewHolder> {

    private final Context context;
    private List<Meal> meals;
    private List<FavoriteMeal> favoriteMeals = new ArrayList<>();
    private final OnMealClickListener onMealClickListener;
    private final OnFavIconClickListener onFavIconClickListener;
    private final OnCalendarIconClickListener onCalendarIconClickListener;
    private static final String TAG = "MealAdapter";
    private final SetIconsStatus setIconsStatus;


    public MealAdapter(Context _context, List<Meal> meals, OnMealClickListener onMealClickListener, OnFavIconClickListener onFavIconClickListener, OnCalendarIconClickListener onCalendarIconClickListener, SetIconsStatus setIconsStatus) {
        this.context = _context;
        this.meals = meals;
        this.onMealClickListener = onMealClickListener;
        this.onFavIconClickListener = onFavIconClickListener;
        this.onCalendarIconClickListener = onCalendarIconClickListener;
        this.setIconsStatus = setIconsStatus;

    }

    public void setMeals(List<Meal> meals) {
        this.meals = meals;
        notifyDataSetChanged();
    }

    public void setFavoriteMeals(List<FavoriteMeal> favoriteMeals) {
        this.favoriteMeals = favoriteMeals;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup recyclerView, int viewType) {
        View view = LayoutInflater.from(recyclerView.getContext())
                .inflate(R.layout.meal_recycleview_element, recyclerView, false);
        return new ViewHolder(view);
    }

    @SuppressLint({"SetTextI18n", "ResourceType"})
    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Meal meal = meals.get(position);
        FavoriteMeal favoriteMeal = new FavoriteMeal(meal);
        PlannedMeal plannedMeal = new PlannedMeal(meal, "");
        holder.txtMealTitle.setText(meal.getStrMeal());

        Glide.with(context)
                .load(meal.getStrMealThumb())
                .apply(new RequestOptions()
                        .diskCacheStrategy(DiskCacheStrategy.ALL)
                        .transform(new CenterCrop(), new RoundedCorners(30))
                        .override(200, 200)
                        .placeholder(R.drawable.loading)
                        .error(R.drawable.imagefailed))
                .into(holder.mealImage);
        setIconsStatus.setHeartStatus(holder.favouriteIcon,favoriteMeal);
        setIconsStatus.setCalendarStatus(holder.calendarIcon,plannedMeal);


        if (favoriteMeals != null) {
            boolean isFavorite = false;
            for (FavoriteMeal fav : favoriteMeals) {
                if (meal.getIdMeal().equals(fav.getIdMeal())) {
                    holder.favouriteIcon.setImageResource(R.drawable.favourite_colored);
                    isFavorite = true;
                    break;
                }
            }
            if (!isFavorite) {
                holder.favouriteIcon.setImageResource(R.drawable.favourite);
            }
        } else {
            holder.favouriteIcon.setImageResource(R.drawable.favourite);
        }


        holder.favouriteIcon.setOnClickListener(v -> {
            if (onFavIconClickListener != null) {
                onFavIconClickListener.onFavIconClickListener(holder.favouriteIcon, favoriteMeal);
            }
        });

        holder.mealImage.setOnClickListener(v -> {
            if (onMealClickListener != null) {
                onMealClickListener.onMealClickListener(holder.favouriteIcon, meal);
            }
        });

        holder.calendarIcon.setOnClickListener(v -> {
            if (onCalendarIconClickListener != null) {
                onCalendarIconClickListener.onCalendarIconClickListener(holder.calendarIcon ,plannedMeal);
            }
        });


        Log.i(TAG, "Bound meal: " + meal.getStrMeal());
    }

    @Override
    public int getItemCount() {
        if(meals != null)
            return meals.size();
        else
            return 0;
    }


    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView txtMealTitle;
        ImageView mealImage;
        ImageView favouriteIcon;
        ImageView calendarIcon;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            txtMealTitle = itemView.findViewById(R.id.mealName);
            mealImage = itemView.findViewById(R.id.mealImage);
            favouriteIcon = itemView.findViewById(R.id.addFavouritesIcon);
            calendarIcon = itemView.findViewById(R.id.addCalendarIcon);
        }
    }
}
