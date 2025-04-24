package com.example.food_planner.home.view;

import android.annotation.SuppressLint;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.example.food_planner.R;
import com.example.food_planner.model.pojos.meal.Meal;

import java.util.List;

public class HomeAdapter extends RecyclerView.Adapter<HomeAdapter.ViewHolder> {


    private final Context context;
    private List<Meal> meals;
    private OnMealClickListener listener;

    public void setMeals(List<Meal> meals) {
        this.meals = meals;
    }

    public HomeAdapter(Context _context, List<Meal> meals, OnMealClickListener _listener) {
        context = _context;
        this.meals = meals;
        this.listener = _listener;
    }
    private static final String TAG = "HomeRecyclerView";
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup recyclerView, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(recyclerView.getContext());
        View view = inflater.inflate(R.layout.recycleview_home_meals,recyclerView,false);
        ViewHolder viewHolder = new ViewHolder(view);
        Log.i(TAG, "onCreateViewHolder: ");
        return viewHolder;
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, @SuppressLint("RecyclerView") int position) {
//        holder.txtTitle.setText(meals.get(position).getTitle());
//        holder.txtDesc.setText(products.get(position).getDescription());
//        holder.txtPrice.setText(products.get(position).getPrice()+"");
//        holder.ratingBar.setRating((float) products.get(position).getRating());
//        holder.ratingBar.setIsIndicator(true);
//        holder.btnAddFavProduct.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                listener.onProductClickListener(products.get(position));
//            }
//        });
        Glide.with(context).load(meals.get(position).getStrMealThumb())
                .apply(new RequestOptions().override(200,200)
                        .placeholder(R.drawable.loading)
                        .error(R.drawable.imagefailed))
                .into(holder.imageView);
        Log.i(TAG, "onBindViewHolder: ");
    }

    @Override
    public int getItemCount() {
        return meals.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder{
//        TextView txtTitle;
//        TextView txtPrice;
//        TextView txtDesc;
//        RatingBar ratingBar;
        ImageView imageView;
        View row;
//        Button btnAddFavProduct;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            row = itemView;
//            txtTitle = row.findViewById(R.id.txtTitle);
//            txtPrice = row.findViewById(R.id.txtPrice);
//            txtDesc = row.findViewById(R.id.txtDesc);
            imageView = row.findViewById(R.id.imageViewHome);
//            ratingBar = row.findViewById(R.id.ratingBar);
//            btnAddFavProduct = row.findViewById(R.id.btnAddFavProduct);
        }
    }
}
