package com.example.food_planner.home.view;

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
import com.example.food_planner.model.pojos.ingredient.Ingredient;

import java.util.List;

public class IngredientAdapter extends RecyclerView.Adapter<IngredientAdapter.ViewHolder> {
    private final Context context;
    private List<Ingredient> ingredients;
    private final OnIngredientClickListener listener;
    private static final String TAG = "HomeRecyclerView";

    public IngredientAdapter(Context _context, List<Ingredient> ingredients, OnIngredientClickListener _listener) {
        this.context = _context;
        this.ingredients = ingredients;
        this.listener = _listener;
    }

    public void setIngredients(List<Ingredient> ingredients) {
        this.ingredients = ingredients;
    }

    @NonNull
    @Override
    public IngredientAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup recyclerView, int viewType) {
        View view = LayoutInflater.from(recyclerView.getContext())
                .inflate(R.layout.meal_carousel, recyclerView, false);
        return new IngredientAdapter.ViewHolder(view);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull IngredientAdapter.ViewHolder holder, int position) {
        Ingredient ingredient = ingredients.get(position);

        holder.txtMealTitle.setText(ingredient.getStrIngredient());

        Glide.with(context)
                .load("https://www.themealdb.com/images/ingredients/"+ingredient.getStrIngredient()+".png")
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
                listener.onIngredientClickListener(holder.addFavouritesIcon, ingredient);
            }

            if (isFavorite) {
                holder.addFavouritesIcon.setImageResource(R.drawable.favourite); // Not favorite
            } else {
                holder.addFavouritesIcon.setImageResource(R.drawable.lover); // Favorite
            }

            v.setTag(!isFavorite);
        });


        Log.i(TAG, "Bound meal: " + ingredient.getStrIngredient());
    }

    @Override
    public int getItemCount() {
        if(ingredients.isEmpty())
            return 0;
        else
            return ingredients.size();
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
