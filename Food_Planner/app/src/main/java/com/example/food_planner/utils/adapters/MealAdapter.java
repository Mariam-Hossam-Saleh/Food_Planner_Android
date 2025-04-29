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
import com.example.food_planner.model.pojos.meal.Meal;
import com.example.food_planner.utils.OnMealClickListener;

import java.util.List;

public class MealAdapter extends RecyclerView.Adapter<MealAdapter.ViewHolder> {

    private final Context context;
    private List<Meal> meals;
    private final OnMealClickListener listener;
    private static final String TAG = "HomeRecyclerView";

    public MealAdapter(Context _context, List<Meal> meals, OnMealClickListener _listener) {
        this.context = _context;
        this.meals = meals;
        this.listener = _listener;
    }

    public void setMeals(List<Meal> meals) {
        this.meals = meals;
    }


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup recyclerView, int viewType) {
        View view = LayoutInflater.from(recyclerView.getContext())
                .inflate(R.layout.meal_carousel, recyclerView, false);
        return new ViewHolder(view);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Meal meal = meals.get(position);

        holder.txtMealTitle.setText(meal.getStrMeal());

        Glide.with(context)
                .load(meal.getStrMealThumb())
                .apply(new RequestOptions()
                        .diskCacheStrategy(DiskCacheStrategy.ALL)
                        .transform(new CenterCrop(), new RoundedCorners(30))
                        .override(200, 200)
                        .placeholder(R.drawable.loading)
                        .error(R.drawable.imagefailed))
                .into(holder.imageView);

//        holder.addFavouritesIcon.setOnClickListener(v -> {
//            if (listener != null) {
//                listener.onMealClickListener(holder.addFavouritesIcon, meal);
//                holder.addFavouritesIcon.setImageResource(R.drawable.lover);
//            }
//        });
        holder.addFavouritesIcon.setOnClickListener(v -> {
            boolean isFavorite = v.getTag() != null && (boolean) v.getTag();

            if (listener != null) {
                listener.onMealClickListener(holder.addFavouritesIcon, meal,isFavorite);
            }

            if (isFavorite) {
                holder.addFavouritesIcon.setImageResource(R.drawable.favourite); // Not favorite
            } else {
                holder.addFavouritesIcon.setImageResource(R.drawable.lover); // Favorite
            }

            v.setTag(!isFavorite);
        });


        Log.i(TAG, "Bound meal: " + meal.getStrMeal());
    }

    @Override
    public int getItemCount() {
        if(meals.isEmpty())
            return 0;
        else
            return meals.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView txtMealTitle;
        ImageView imageView;
        ImageView addFavouritesIcon;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            txtMealTitle = itemView.findViewById(R.id.mealName);
            imageView = itemView.findViewById(R.id.mealImage);
            addFavouritesIcon = itemView.findViewById(R.id.addFavouritesIcon);
        }
    }
}
