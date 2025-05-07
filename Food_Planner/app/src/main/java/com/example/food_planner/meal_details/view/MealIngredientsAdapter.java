package com.example.food_planner.meal_details.view;

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

public class MealIngredientsAdapter extends RecyclerView.Adapter<MealIngredientsAdapter.ViewHolder> {

    private final Context context;
    private List<Ingredient> ingredients;

    public MealIngredientsAdapter(Context _context, List<Ingredient> ingredients) {
        this.context = _context;
        this.ingredients = ingredients;
    }

    public void setIngredients(List<Ingredient> ingredients) {
        this.ingredients = ingredients;
    }


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup recyclerView, int viewType) {
        View view = LayoutInflater.from(recyclerView.getContext())
                .inflate(R.layout.meal_ingredients_recycleview_element, recyclerView, false);
        return new ViewHolder(view);
    }

    @SuppressLint({"SetTextI18n", "ResourceType"})
    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Ingredient ingredient = ingredients.get(position);

        holder.ingredientName.setText(ingredient.getStrIngredient());
        holder.ingredientMeasure.setText(ingredient.getMeasure());

        Glide.with(context)
                .load("https://www.themealdb.com/images/ingredients/"+ingredient.getStrIngredient()+".png")
                .apply(new RequestOptions()
                        .diskCacheStrategy(DiskCacheStrategy.ALL)
                        .transform(new CenterCrop(), new RoundedCorners(30))
                        .override(200, 200)
                        .placeholder(R.drawable.loading)
                        .error(R.drawable.imagefailed))
                .into(holder.ingredientImage);

        Log.i("MealIngredients", "Bound meal: " + ingredient.getStrIngredient());
    }

    @Override
    public int getItemCount() {
        if(ingredients != null)
            return ingredients.size();
        else
            return 0;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView ingredientName;
        TextView ingredientMeasure;
        ImageView ingredientImage;
        ImageView favouriteIcon;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            ingredientName = itemView.findViewById(R.id.ingredientName);
            ingredientMeasure = itemView.findViewById(R.id.ingredientMeasure);
            ingredientImage = itemView.findViewById(R.id.ingredientImage);
        }
    }
}
