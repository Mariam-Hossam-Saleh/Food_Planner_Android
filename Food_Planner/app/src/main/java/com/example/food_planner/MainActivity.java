package com.example.food_planner;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Menu;
import android.widget.Button;

import com.example.food_planner.model.database.mealsdatabase.MealLocalDataSourceImp;
import com.google.android.material.navigation.NavigationView;

import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.appcompat.app.AppCompatActivity;

import com.example.food_planner.databinding.ActivityMainBinding;
import com.google.firebase.auth.FirebaseAuth;

public class MainActivity extends AppCompatActivity {

    private AppBarConfiguration mAppBarConfiguration;
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        com.example.food_planner.databinding.ActivityMainBinding binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        mAuth = FirebaseAuth.getInstance();

        Bundle bundle = new Bundle();
        Button btnLogOut = binding.btnLogOut;
        Button btnLoginReg = binding.btnLoginRegister;

        if(mAuth.getCurrentUser() == null){
            btnLogOut.setVisibility(View.GONE);
            btnLoginReg.setVisibility(View.VISIBLE);
        }
        else{
            btnLoginReg.setVisibility(View.GONE);
            btnLogOut.setVisibility(View.VISIBLE);
        }

        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_content_main);
        navController.setGraph(R.navigation.mobile_navigation, bundle);

        setSupportActionBar(binding.appBarMain.toolbar);

        DrawerLayout drawer = binding.drawerLayout;
        NavigationView navigationView = binding.navView;

        mAppBarConfiguration = new AppBarConfiguration.Builder(
                R.id.nav_home, R.id.nav_search, R.id.nav_favourite, R.id.nav_calender)
                .setOpenableLayout(drawer)
                .build();

        NavigationUI.setupActionBarWithNavController(this, navController, mAppBarConfiguration);
        NavigationUI.setupWithNavController(navigationView, navController);

        btnLogOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Clear local database before logging out
                MealLocalDataSourceImp localDataSource = MealLocalDataSourceImp.getInstance(MainActivity.this);
                localDataSource.clearAllFavoriteMeals();
                localDataSource.clearAllPlannedMeals();

                mAuth.signOut();
                startActivity(new Intent(MainActivity.this, WelcomeActivity.class));
                finish();
            }
        });

        btnLoginReg.setOnClickListener( v -> {
            startActivity(new Intent(MainActivity.this, WelcomeActivity.class));
            finish();
        });



    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onSupportNavigateUp() {
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_content_main);
        return NavigationUI.navigateUp(navController, mAppBarConfiguration)
                || super.onSupportNavigateUp();
    }
}