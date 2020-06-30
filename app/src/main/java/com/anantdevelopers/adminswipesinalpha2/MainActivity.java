package com.anantdevelopers.adminswipesinalpha2;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import android.content.Intent;
import android.os.Bundle;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Toast;

import com.anantdevelopers.adminswipesinalpha2.Authentication.AuthActivity;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.InstanceIdResult;


public class MainActivity extends AppCompatActivity {

     private String token;
     private FirebaseAuth firebaseAuth;

     @Override
     protected void onCreate(Bundle savedInstanceState) {
          super.onCreate(savedInstanceState);
          setContentView(R.layout.activity_main);

          checkWhetherLoggedInOrNot();

          Toolbar toolbar = findViewById(R.id.toolbar);
          setSupportActionBar(toolbar);

          DrawerLayout drawer = findViewById(R.id.drawer_layout);

          NavigationView navigationView = findViewById(R.id.nav_view);

          AppBarConfiguration appBarConfiguration = new AppBarConfiguration.Builder(R.id.all_orders_dest, R.id.change_fruit_prices_dest, R.id.all_previous_orders_dest, R.id.closed_today_dest, R.id.change_website_url_dest).setDrawerLayout(drawer).build();
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


     private void checkWhetherLoggedInOrNot() {
          firebaseAuth = FirebaseAuth.getInstance();

          FirebaseAuth.AuthStateListener listener = buildAuthStateListener();
          firebaseAuth.addAuthStateListener(listener);
     }

     private FirebaseAuth.AuthStateListener buildAuthStateListener() {
          FirebaseAuth.AuthStateListener authStateListener;

          authStateListener = new FirebaseAuth.AuthStateListener() {
               @Override
               public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                    FirebaseUser user = firebaseAuth.getCurrentUser();

                    if(user == null) {
                         //user needs to login
                         Intent intent = new Intent(MainActivity.this, AuthActivity.class);
                         intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                         startActivity(intent);
                         finish();
                    }
                    else {
                         //user is already signed in
                         String phoneNumber = user.getPhoneNumber();

                         if(phoneNumber.equals("+916353923876")){
                              //then only you can use this app
                         }
                         else {
                              Toast toast = Toast.makeText(MainActivity.this, "Only Anant has Access...", Toast.LENGTH_LONG);
                              toast.setGravity(Gravity.CENTER, 0, 0);
                              toast.show();
                              firebaseAuth.signOut();
                         }
                    }

               }
          };

          return authStateListener;
     }

     @Override
     public boolean onCreateOptionsMenu(Menu menu) {
          MenuInflater inflater = getMenuInflater();
          inflater.inflate(R.menu.options_menu, menu);
          return true;
     }

     @Override
     public boolean onOptionsItemSelected(@NonNull MenuItem item) {
          switch (item.getItemId()) {
               case R.id.logout_dest :
                    //logout the user
                    firebaseAuth.signOut();
                    return true;
               default :
                    return super.onOptionsItemSelected(item);
          }
     }
}
