package com.example.food_planner.meal_details.view;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.resource.bitmap.CenterCrop;
import com.bumptech.glide.load.resource.bitmap.RoundedCorners;
import com.bumptech.glide.request.RequestOptions;
import com.example.food_planner.R;
import com.example.food_planner.databinding.FragmentMealDetailsBinding;
import com.example.food_planner.meal_details.presenter.MealDetailsPresenter;
import com.example.food_planner.meal_details.presenter.MealDetailsPresenterImp;
import com.example.food_planner.model.database.mealsdatabase.MealLocalDataSourceImp;
import com.example.food_planner.model.network.meal.MealRemoteDataSourceImp;
import com.example.food_planner.model.pojos.ingredient.Ingredient;
import com.example.food_planner.model.pojos.meal.Meal;
import com.example.food_planner.model.repositories.meal.MealsRepositoryImp;
import com.example.food_planner.utils.OnFavIconClickListener;
import com.example.food_planner.utils.OnIngredientClickListener;
import com.example.food_planner.utils.OnMealClickListener;

import java.util.ArrayList;
import java.util.List;


public class MealDetailsFragment extends Fragment implements MealDetailsView, OnIngredientClickListener , OnFavIconClickListener {
    ArrayList<Ingredient> ingredientArrayList;
    ImageView mealImage;
    ImageView addFavouritesIcon;
    TextView mealName;
    TextView mealInstructions;
    WebView youtubeVideo;
    MealIngredientsAdapter mealIngredientsAdapter;
    RecyclerView recyclerviewIngredients;
    MealDetailsPresenter mealDetailsPresenter;
    LinearLayoutManager linearLayoutManager;
    private FragmentMealDetailsBinding binding;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }


    @SuppressLint("SetJavaScriptEnabled")
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        if (getArguments() != null) {
            String mealID = getArguments().getString("mealID");

            binding = FragmentMealDetailsBinding.inflate(inflater, container, false);
            mealImage = binding.mealImage;
            addFavouritesIcon = binding.addFavouritesIcon;
            mealName = binding.mealName;
            mealInstructions = binding.mealInstructions;

            youtubeVideo = binding.youtubeVideo;
            youtubeVideo.getSettings().setJavaScriptEnabled(true);
            youtubeVideo.getSettings().setDomStorageEnabled(true);
            youtubeVideo.setWebChromeClient(new WebChromeClient()); // required for video playback
            youtubeVideo.setWebViewClient(new WebViewClient());

            ingredientArrayList = new ArrayList<>();
            mealIngredientsAdapter = new MealIngredientsAdapter(getContext(), ingredientArrayList);

            linearLayoutManager = new LinearLayoutManager(getActivity());
            linearLayoutManager.setOrientation(RecyclerView.HORIZONTAL);


            recyclerviewIngredients = binding.recyclerviewIngredients;
            recyclerviewIngredients.setLayoutManager(linearLayoutManager);
            recyclerviewIngredients.setAdapter(mealIngredientsAdapter);

            mealDetailsPresenter = new MealDetailsPresenterImp(MealsRepositoryImp.getInstance(MealRemoteDataSourceImp.getInstance(), MealLocalDataSourceImp.getInstance(getContext())),this);
            mealDetailsPresenter.searchMealByID(mealID);

        }
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

    @Override
    public void ShowMealDetails(List<Meal> mealList){
        Meal meal = mealList.get(0);
        Glide.with(getContext())
                .load(meal.getStrMealThumb())
                .apply(new RequestOptions()
                        .diskCacheStrategy(DiskCacheStrategy.ALL)
                        .transform(new CenterCrop(), new RoundedCorners(30))
                        .override(200, 200)
                        .placeholder(R.drawable.loading)
                        .error(R.drawable.imagefailed))
                .into(mealImage);

        addFavouritesIcon.setOnClickListener(v -> {
            onFavIconClickListener(addFavouritesIcon,meal,false);
        });
        mealName.setText(meal.getStrMeal());
        mealInstructions.setText(meal.getStrInstructions());
        String videoURL = meal.getStrYoutube();
        String videoID = extractVideoID(videoURL);
        String embedUrl = "https://www.youtube.com/embed/" + videoID;
        youtubeVideo.loadUrl(embedUrl);

        for(int count = 1 ; count < 20 ; count++)
        {
            String ingredientField = null;
            try {
                ingredientField = (String) Meal.class.getDeclaredField("strIngredient" + count ).get(meal);
            } catch (IllegalAccessException e) {
                throw new RuntimeException(e);
            } catch (NoSuchFieldException e) {
                throw new RuntimeException(e);
            }
            String measureField = null;
            try {
                measureField = (String) Meal.class.getDeclaredField("strMeasure" + count ).get(meal);
            } catch (IllegalAccessException e) {
                throw new RuntimeException(e);
            } catch (NoSuchFieldException e) {
                throw new RuntimeException(e);
            }
            if(ingredientField != "null") {
                Ingredient ingredient = new Ingredient(ingredientField, measureField);
                ingredientArrayList.add(ingredient);
            }
        }
        ShowIngredients(ingredientArrayList);

//        addFavouritesIcon = binding.addFavouritesIcon;
//        mealDescription = binding.mealDescription;
//        mealCalories = binding.mealCalories;
//        mealProtein = binding.mealProtein;
//        mealCarbs = binding.mealCarbs;
//        instructions = binding.instructions;

    }

    @Override
    public void ShowIngredients(List<Ingredient> ingredientList) {
        mealIngredientsAdapter.setIngredients(ingredientList);
        mealIngredientsAdapter.notifyDataSetChanged();
    }

    @Override
    public void ShowErrMsg(String error) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        builder.setMessage(error).setTitle("An Error Occured");
        AlertDialog dialog = builder.create();
        dialog.show();
    }


    @Override
    public void onFavIconClickListener(ImageView imageView, Meal meal, boolean favState) {
        if(favState) {
            mealDetailsPresenter.removeMealFromFavourite(meal);
        }
        else{
            mealDetailsPresenter.addMealToFavourite(meal);
        }
    }

    @Override
    public void onIngredientClickListener(ImageView imageView, Ingredient ingredient) {

    }

    private String extractVideoID(String videoUrl){
        String videoId = "";

        if (videoUrl.contains("v=")) {
            int index = videoUrl.indexOf("v=") + 2;
            videoId = videoUrl.substring(index);

            // In case there are additional parameters (e.g., &t=2s)
            int ampIndex = videoId.indexOf("&");
            if (ampIndex != -1) {
                videoId = videoId.substring(0, ampIndex);
            }
        }
        return videoId;
    }
}
