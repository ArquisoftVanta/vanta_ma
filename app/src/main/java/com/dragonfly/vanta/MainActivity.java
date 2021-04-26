package com.dragonfly.vanta;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.navigation.NavController;
import androidx.navigation.NavDestination;
import androidx.navigation.Navigation;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.AdapterView;

import androidx.appcompat.widget.Toolbar;
import androidx.navigation.ui.NavigationUI;

import com.google.android.material.navigation.NavigationView;

public class MainActivity extends AppCompatActivity {

    DrawerLayout drawerLayout;
    Toolbar toolbar;
    NavigationView navigationView;
    NavController navController;
    ActionBarDrawerToggle drawerToggle;
    MenuItem logoutItem;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_activity);

        drawerLayout = findViewById(R.id.drawer_layout);
        navigationView = findViewById(R.id.nav_view);
            logoutItem = navigationView.getMenu().findItem(R.id.logOut);
        toolbar = findViewById(R.id.toolbar);

        this.setSupportActionBar(toolbar);

        //Setup automatic fragment navigation from the xml settings
        navController = Navigation.findNavController(this, R.id.nav_fragment);
        NavigationUI.setupWithNavController(navigationView, navController);

        drawerToggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar, R.string.abrir, R.string.cerrar);
        drawerLayout.addDrawerListener(drawerToggle);
        drawerToggle.syncState();

        //Add extra actions for navigation items
        navController.addOnDestinationChangedListener(new NavController.OnDestinationChangedListener() {
            @Override
            public void onDestinationChanged(@NonNull NavController controller, @NonNull NavDestination destination, @Nullable Bundle arguments) {            }
        });

        //Add actions for non-navigation items
        logoutItem.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                logOut();
                return true;
            }
        });
    }

    private void logOut() {
        SharedPreferences sharedPref = getApplicationContext().getSharedPreferences("jwt", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putString("token", "");
        editor.commit();

        Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
        startActivity(intent);
        finish();
    }


}