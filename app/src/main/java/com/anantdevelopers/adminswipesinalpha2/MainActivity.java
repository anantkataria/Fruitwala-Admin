package com.anantdevelopers.adminswipesinalpha2;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import android.os.Bundle;

import com.google.android.material.appbar.CollapsingToolbarLayout;
import com.google.android.material.navigation.NavigationView;


public class MainActivity extends AppCompatActivity {

     private AppBarConfiguration appBarConfiguration;

     @Override
     protected void onCreate(Bundle savedInstanceState) {
          super.onCreate(savedInstanceState);
          setContentView(R.layout.activity_main);

          //CollapsingToolbarLayout layout = findViewById(R.id.collapsing_toolbar_layout);

          Toolbar toolbar = findViewById(R.id.toolbar);
          setSupportActionBar(toolbar);

          DrawerLayout drawer = findViewById(R.id.drawer_layout);

          NavigationView navigationView = findViewById(R.id.nav_view);

          appBarConfiguration = new AppBarConfiguration.Builder(R.id.all_orders_dest, R.id.change_fruit_prices_dest, R.id.all_previous_orders_dest, R.id.closed_today_dest).setDrawerLayout(drawer).build();
          NavController navController = Navigation.findNavController(this, R.id.my_nav_host_fragment);

          NavigationUI.setupWithNavController(toolbar, navController, appBarConfiguration);
          NavigationUI.setupActionBarWithNavController(this, navController, appBarConfiguration);
          NavigationUI.setupWithNavController(navigationView, navController);

     }

}
