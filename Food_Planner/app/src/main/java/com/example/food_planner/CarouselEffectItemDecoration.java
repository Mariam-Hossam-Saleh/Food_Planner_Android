package com.example.food_planner;

import android.graphics.Rect;
import android.view.View;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class CarouselEffectItemDecoration extends RecyclerView.ItemDecoration {
    private final float scaleAmount;
    private final int spacing;

    public CarouselEffectItemDecoration(float scaleAmount, int spacing) {
        this.scaleAmount = scaleAmount;
        this.spacing = spacing;
    }

    @Override
    public void getItemOffsets(@NonNull Rect outRect, @NonNull View view,
                               @NonNull RecyclerView parent, @NonNull RecyclerView.State state) {
        outRect.left = spacing;
        outRect.right = spacing;
    }

    public static void applyCarouselEffect(RecyclerView recyclerView, float scaleAmount) {
        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                applyScaling(recyclerView, scaleAmount);
            }
        });
    }

    private static void applyScaling(RecyclerView recyclerView, float scaleAmount) {
        LinearLayoutManager lm = (LinearLayoutManager) recyclerView.getLayoutManager();
        if (lm == null) return;

        int centerX = recyclerView.getWidth() / 2;
        int firstVisible = lm.findFirstVisibleItemPosition();
        int lastVisible = lm.findLastVisibleItemPosition();

        for (int i = firstVisible; i <= lastVisible; i++) {
            View view = lm.findViewByPosition(i);
            if (view != null) {
                int viewCenterX = (view.getLeft() + view.getRight()) / 2;
                float distance = Math.abs(centerX - viewCenterX);
                float scale = 1f - (distance / centerX) * scaleAmount;
                view.setScaleX(Math.max(0.8f, scale));
                view.setScaleY(Math.max(0.8f, scale));
                view.setAlpha(0.5f + (scale * 0.5f)); // Optional: fade effect
            }
        }
    }
}