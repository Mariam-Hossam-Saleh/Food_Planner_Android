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
import com.bumptech.glide.request.RequestOptions;
import com.example.food_planner.R;
import com.example.food_planner.model.pojos.category.Category;
import com.example.food_planner.utils.mutual_interfaces.OnCategoryClickListener;

import java.util.List;

public class CategoryAdapter extends RecyclerView.Adapter<CategoryAdapter.ViewHolder> {
    private final Context context;
    private List<Category> categories;
    private final OnCategoryClickListener listener;
    private static final String TAG = "CategoryAdapter";

    public CategoryAdapter(Context _context, List<Category> categories, OnCategoryClickListener _listener) {
        this.context = _context;
        this.categories = categories;
        this.listener = _listener;
    }

    public void setCategories(List<Category> categories) {
        this.categories = categories;
    }

    @NonNull
    @Override
    public CategoryAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup recyclerView, int viewType) {
        View view = LayoutInflater.from(recyclerView.getContext())
                .inflate(R.layout.category_recycleview, recyclerView, false);
        return new CategoryAdapter.ViewHolder(view);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull CategoryAdapter.ViewHolder holder, int position) {
        Category category = categories.get(position);

        holder.txtMealTitle.setText(category.getStrCategory());

        Glide.with(context)
                .load(category.getStrCategoryThumb())
                .apply(new RequestOptions()
                        .circleCrop()
                        .diskCacheStrategy(DiskCacheStrategy.ALL)
                        .placeholder(R.drawable.loading)
                        .error(R.drawable.imagefailed))
                .into(holder.imageView);

        holder.imageView.setOnClickListener(v -> {

            if (listener != null) {
                listener.onCategoryClickListener(holder.imageView, category);
            }

        });

        Log.i(TAG, "Bound meal: " + category.getStrCategory());
    }

    @Override
    public int getItemCount() {
        if(categories != null)
            return categories.size();
        else
            return 0;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView txtMealTitle;
        ImageView imageView;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            txtMealTitle = itemView.findViewById(R.id.categoryName);
            imageView = itemView.findViewById(R.id.categoryImage);
        }
    }
}

