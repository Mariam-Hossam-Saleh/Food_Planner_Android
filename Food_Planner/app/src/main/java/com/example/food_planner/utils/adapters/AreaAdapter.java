package com.example.food_planner.utils.adapters;

import static android.content.ContentValues.TAG;

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
import com.example.food_planner.model.pojos.area.Area;
import com.example.food_planner.model.pojos.area.AreaFlags;
import com.example.food_planner.utils.OnAreaClickListener;

import java.util.List;

public class AreaAdapter extends RecyclerView.Adapter<AreaAdapter.ViewHolder> {
    private final Context context;
    private List<Area> areas;
    private final OnAreaClickListener listener;

    public AreaAdapter(Context _context, List<Area> areas, OnAreaClickListener _listener) {
        this.context = _context;
        this.areas = areas;
        this.listener = _listener;
    }

    public void setAreas(List<Area> areas) {
        this.areas = areas;
    }

    @NonNull
    @Override
    public AreaAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup recyclerView, int viewType) {
        View view = LayoutInflater.from(recyclerView.getContext())
                .inflate(R.layout.area_recyclview, recyclerView, false);
        return new AreaAdapter.ViewHolder(view);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull AreaAdapter.ViewHolder holder, int position) {
        Area area = areas.get(position);
        holder.txtAreaTitle.setText(area.getStrArea());
        int flagResId = AreaFlags.getFlagForArea(area.getStrArea());

        Glide.with(context)
                .load(flagResId)
                .apply(new RequestOptions()
                        .circleCrop()
                        .diskCacheStrategy(DiskCacheStrategy.ALL)
                        .placeholder(R.drawable.loading)
                        .error(R.drawable.imagefailed))
                .into(holder.imageView);

        holder.imageView.setOnClickListener(v -> {

            if (listener != null) {
                listener.onAreaClickListener(holder.imageView, area);
            }

        });

        Log.i(TAG, "Bound meal: " + area.getStrArea());
    }

    @Override
    public int getItemCount() {
        if(areas.isEmpty())
            return 0;
        else
            return areas.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView txtAreaTitle;
        ImageView imageView;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            txtAreaTitle = itemView.findViewById(R.id.areaName);
            imageView = itemView.findViewById(R.id.areaImage);
        }
    }
}

