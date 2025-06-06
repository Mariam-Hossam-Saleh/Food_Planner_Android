package com.example.food_planner.meal_details.view;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
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
import androidx.lifecycle.LiveData;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.resource.bitmap.CenterCrop;
import com.bumptech.glide.load.resource.bitmap.RoundedCorners;
import com.bumptech.glide.request.RequestOptions;
import com.example.food_planner.R;
import com.example.food_planner.SignInActivity;
import com.example.food_planner.WelcomeActivity;
import com.example.food_planner.databinding.FragmentMealDetailsBinding;
import com.example.food_planner.meal_details.presenter.MealDetailsPresenter;
import com.example.food_planner.meal_details.presenter.MealDetailsPresenterImp;
import com.example.food_planner.model.database.mealsdatabase.MealLocalDataSourceImp;
import com.example.food_planner.model.network.meal.MealRemoteDataSourceImp;
import com.example.food_planner.model.pojos.ingredient.Ingredient;
import com.example.food_planner.model.pojos.meal.FavoriteMeal;
import com.example.food_planner.model.pojos.meal.Meal;
import com.example.food_planner.model.pojos.meal.PlannedMeal;
import com.example.food_planner.model.repositories.meal.MealsRepositoryImp;
import com.example.food_planner.utils.mutual_interfaces.OnCalendarIconClickListener;
import com.example.food_planner.utils.mutual_interfaces.OnFavIconClickListener;
import com.example.food_planner.utils.mutual_interfaces.OnIngredientClickListener;
import com.google.firebase.auth.FirebaseAuth;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;


public class MealDetailsFragment extends Fragment implements MealDetailsView, OnIngredientClickListener , OnFavIconClickListener , OnCalendarIconClickListener {
    ArrayList<Ingredient> ingredientArrayList;
    ImageView mealImage;
    ImageView addFavouritesIcon;
    ImageView addCalendarIcon;
    TextView mealName;
    TextView mealInstructions;
    WebView youtubeVideo;
    MealIngredientsAdapter mealIngredientsAdapter;
    RecyclerView recyclerviewIngredients;
    MealDetailsPresenter mealDetailsPresenter;
    LinearLayoutManager linearLayoutManager;
    FirebaseAuth firebaseAuth;
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
            firebaseAuth = FirebaseAuth.getInstance();
            mealImage = binding.mealImage;
            addFavouritesIcon = binding.addFavouritesIcon;
            addCalendarIcon = binding.addCalendarIcon;
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

            mealDetailsPresenter = new MealDetailsPresenterImp(MealsRepositoryImp.getInstance(getContext(),MealRemoteDataSourceImp.getInstance(), MealLocalDataSourceImp.getInstance(getContext())),this);
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
        // check weather meal is in the favorite meal database and set the icon accordingly
        FavoriteMeal favoriteMeal = new FavoriteMeal(meal);
        LiveData<Boolean> isFavorite = mealDetailsPresenter.isMealFavorite(favoriteMeal);
        isFavorite.observe(getViewLifecycleOwner(), isFav -> {
            if (isFav) {
                addFavouritesIcon.setImageResource(R.drawable.favourite_colored);
                favoriteMeal.isFavorite = true;
            } else {
                addFavouritesIcon.setImageResource(R.drawable.favourite);
                favoriteMeal.isFavorite = false;
            }
        });
        // check weather meal is in the planned meal database and set the icon accordingly
        PlannedMeal plannedMeal = new PlannedMeal(meal);
        LiveData<Boolean> isPlanned = mealDetailsPresenter.isMealPlanned(plannedMeal);
        isPlanned.observe(getViewLifecycleOwner(), isPlan -> {
            if (isPlan) {
                addCalendarIcon.setImageResource(R.drawable.calendar_colored);
                plannedMeal.isPlanned = true;
            } else {
                addCalendarIcon.setImageResource(R.drawable.calendar);
                plannedMeal.isPlanned = false;
            }
        });
        addFavouritesIcon.setOnClickListener(v -> {
            onFavIconClickListener(addFavouritesIcon,favoriteMeal);
        });
        addCalendarIcon.setOnClickListener(v -> {
            onCalendarIconClickListener(addCalendarIcon,new PlannedMeal(meal));
        });
        mealName.setText(meal.getStrMeal());
        mealInstructions.setText(meal.getStrInstructions());
        String videoURL = meal.getStrYoutube();
        String videoID = extractVideoID(videoURL);
        String embedUrl = "https://www.youtube.com/embed/" + videoID;
        youtubeVideo.loadUrl(embedUrl);

        ingredientArrayList = new ArrayList<>(meal.extractIngredients());
        ShowIngredients(ingredientArrayList);

    }

    @SuppressLint("NotifyDataSetChanged")
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
    public void onFavIconClickListener(ImageView imageView, FavoriteMeal meal) {
        if (firebaseAuth.getCurrentUser() == null) {
            new AlertDialog.Builder(requireContext())
                    .setTitle("Login Required")
                    .setMessage("You need to log in to add meals to your favorites.")
                    .setPositiveButton("Login", (dialog, which) -> {
                        Intent intent = new Intent(requireContext(), WelcomeActivity.class);
                        startActivity(intent);
                        requireActivity().finish();
                    })
                    .setNegativeButton("Cancel", null)
                    .show();
            return;
        }
        else {
            if (meal.isFavorite) {
                imageView.setImageResource(R.drawable.favourite);
                mealDetailsPresenter.removeMealFromFavourite(meal);
                Toast.makeText(getActivity(), "Removed from favorite successfully!", Toast.LENGTH_SHORT).show();
                meal.isFavorite = false;
            } else {
                imageView.setImageResource(R.drawable.favourite_colored);
                mealDetailsPresenter.addMealToFavourite(meal);
                Toast.makeText(getActivity(), "Added to favorite successfully!", Toast.LENGTH_SHORT).show();
                meal.isFavorite = true;
            }
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

    @Override
    public void onCalendarIconClickListener(ImageView imageView, PlannedMeal meal) {
        if (firebaseAuth.getCurrentUser() == null) {
            new AlertDialog.Builder(requireContext())
                    .setTitle("Login Required")
                    .setMessage("You need to log in to add meals to your calendar.")
                    .setPositiveButton("Login", (dialog, which) -> {
                        Intent intent = new Intent(requireContext(), WelcomeActivity.class);
                        startActivity(intent);
                        requireActivity().finish();
                    })
                    .setNegativeButton("Cancel", null)
                    .show();
            return;
        }
        else {
            final Calendar calendar = Calendar.getInstance();
            int year = calendar.get(Calendar.YEAR);
            int month = calendar.get(Calendar.MONTH);
            int day = calendar.get(Calendar.DAY_OF_MONTH);

            DatePickerDialog datePickerDialog = new DatePickerDialog(
                    requireContext(),
                    R.style.my_dialog_theme,
                    (view, selectedYear, selectedMonth, selectedDay) -> {
                        String selectedDate = String.format(Locale.US, "%04d-%02d-%02d",
                                selectedYear, selectedMonth + 1, selectedDay);
                        meal.setDate(selectedDate);
                        mealDetailsPresenter.addMealToCalendar(meal);
                        imageView.setImageResource(R.drawable.calendar_colored);
                        Toast.makeText(requireContext(), meal.getStrMeal() + " planned for " + selectedDate, Toast.LENGTH_LONG).show();
                    },
                    year, month, day
            );
            // Set minimum date (today)
            datePickerDialog.getDatePicker().setMinDate(System.currentTimeMillis());
            // Set maximum date (1 week from today)
            Calendar maxDate = Calendar.getInstance();
            maxDate.add(Calendar.DAY_OF_YEAR, 7); // Add 7 days
            datePickerDialog.getDatePicker().setMaxDate(maxDate.getTimeInMillis());
            // If the date picker is canceled, leave icon
            datePickerDialog.setOnCancelListener(dialog -> {
                imageView.setImageResource(R.drawable.calendar);
            });
            datePickerDialog.show();
        }
    }

}
