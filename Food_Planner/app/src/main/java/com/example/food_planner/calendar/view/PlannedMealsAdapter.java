package com.example.food_planner.calendar.view;

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
import com.example.food_planner.model.pojos.meal.PlannedMeal;
import com.example.food_planner.utils.mutual_interfaces.OnCalendarIconClickListener;
import com.example.food_planner.utils.mutual_interfaces.OnMealClickListener;

import java.util.List;

public class PlannedMealsAdapter extends RecyclerView.Adapter<PlannedMealsAdapter.ViewHolder> {

    private final Context context;
    private List<PlannedMeal> meals;
    private final OnMealClickListener onMealClickListener;
    private final OnCalendarIconClickListener onCalendarIconClickListener;
    public PlannedMealsAdapter(Context _context, List<PlannedMeal> meals, OnMealClickListener onMealClickListener, OnCalendarIconClickListener onCalendarIconClickListener) {
        this.context = _context;
        this.meals = meals;
        this.onMealClickListener = onMealClickListener;
        this.onCalendarIconClickListener = onCalendarIconClickListener;
    }

    public void setPlannedMeals(List<PlannedMeal> meals) {
        this.meals = meals;
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
        PlannedMeal plannedMeal = meals.get(position);

        holder.txtMealTitle.setText(plannedMeal.getStrMeal());

        Glide.with(context)
                .load(plannedMeal.getStrMealThumb())
                .apply(new RequestOptions()
                        .diskCacheStrategy(DiskCacheStrategy.ALL)
                        .transform(new CenterCrop(), new RoundedCorners(30))
                        .override(200, 200)
                        .placeholder(R.drawable.loading)
                        .error(R.drawable.imagefailed))
                .into(holder.imageView);
        holder.calendarIcon.setImageResource(R.drawable.calendar_colored);
        holder.calendarIcon.setOnClickListener(v -> {
            onCalendarIconClickListener.onCalendarIconClickListener(holder.calendarIcon ,plannedMeal);
        });

        holder.imageView.setOnClickListener( v -> {
            if (onMealClickListener != null) {
                onMealClickListener.onMealClickListener(holder.calendarIcon, plannedMeal);
            }
        });
        Log.i("CalendarAdapter", "Bound meal: " + plannedMeal.getStrMeal());
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
        ImageView imageView;
        ImageView calendarIcon;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            txtMealTitle = itemView.findViewById(R.id.mealName);
            imageView = itemView.findViewById(R.id.mealImage);
            calendarIcon = itemView.findViewById(R.id.addCalendarIcon);
        }
    }

}
