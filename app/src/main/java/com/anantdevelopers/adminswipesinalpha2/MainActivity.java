package com.anantdevelopers.adminswipesinalpha2;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.appbar.CollapsingToolbarLayout;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.InstanceIdResult;


public class MainActivity extends AppCompatActivity {

     private AppBarConfiguration appBarConfiguration;

     private String token;

     @Override
     protected void onCreate(Bundle savedInstanceState) {
          super.onCreate(savedInstanceState);
          setContentView(R.layout.activity_main);

          //CollapsingToolbarLayout layout = findViewById(R.id.collapsing_toolbar_layout);

          Toolbar toolbar = findViewById(R.id.toolbar);
          setSupportActionBar(toolbar);

          DrawerLayout drawer = findViewById(R.id.drawer_layout);

          NavigationView navigationView = findViewById(R.id.nav_view);

          appBarConfiguration = new AppBarConfiguration.Builder(R.id.all_orders_dest, R.id.change_fruit_prices_dest, R.id.all_previous_orders_dest, R.id.closed_today_dest, R.id.change_website_url_dest).setDrawerLayout(drawer).build();
          NavController navController = Navigation.findNavController(this, R.id.my_nav_host_fragment);

          NavigationUI.setupWithNavController(toolbar, navController, appBarConfiguration);
          NavigationUI.setupActionBarWithNavController(this, navController, appBarConfiguration);
          NavigationUI.setupWithNavController(navigationView, navController);

          getToken();
     }

     private void sendToken() {
          DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference();

          databaseReference.child("tokens").child("adminToken").setValue(token).addOnCompleteListener(new OnCompleteListener<Void>() {
               @Override
               public void onComplete(@NonNull Task<Void> task) {
                    if(task.isSuccessful()){
                         Toast.makeText(MainActivity.this, "Token updated successfully", Toast.LENGTH_SHORT).show();
                    }
                    else {
                         Toast.makeText(MainActivity.this, "Problem occurred during token updation", Toast.LENGTH_LONG).show();
                    }
               }
          });
     }

     private void getToken() {
          FirebaseInstanceId.getInstance().getInstanceId().addOnSuccessListener(new OnSuccessListener<InstanceIdResult>() {
               @Override
               public void onSuccess(InstanceIdResult instanceIdResult) {
                    token = instanceIdResult.getToken();
                    sendToken();
               }
          });
     }
}
