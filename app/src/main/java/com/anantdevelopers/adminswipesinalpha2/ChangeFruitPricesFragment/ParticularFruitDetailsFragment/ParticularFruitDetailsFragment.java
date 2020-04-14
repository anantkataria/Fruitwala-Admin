package com.anantdevelopers.adminswipesinalpha2.ChangeFruitPricesFragment.ParticularFruitDetailsFragment;

import android.graphics.Color;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.anantdevelopers.adminswipesinalpha2.R;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class ParticularFruitDetailsFragment extends Fragment {

     private static final String BUTTON_TEXT_NOT_AVAILABLE = "Change to Not-Available";
     private static final String BUTTON_TEXT_AVAILABLE = "Change to Available";
     private static final String AVAILABLE_STRING = "Available";
     private static final String NOT_AVAILABLE_STRING = "Not-Available";
     private static final String NOT_SET_YET_STRING = "Not set yet";

     private FirebaseDatabase firebaseDatabase;
     private DatabaseReference databaseReference;

     private TextView availabilityTextView, noQtyPricesTextView;
     private Button changeAvailabilityButton;
     private ProgressBar progressBar;
     private RecyclerView recyclerView;
     private FloatingActionButton floatingActionButton;
     private LinearLayout ll;

     private ParticularFruitQtyPriceAdapter adapter;

     private ArrayList<String> quantities, prices, keys; //keys are firebase keys, they will be stored for ease in deletion process

     private String fruitName;

     private ValueEventListener valueEventListener;

     public ParticularFruitDetailsFragment() {
          // Required empty public constructor
     }

     private interface checkExistingQtyPricesInterface{
          void onSnapshotExist();
          void onSnapshotDontExist();
     }

     @Override
     public void onCreate(@Nullable Bundle savedInstanceState) {
          super.onCreate(savedInstanceState);

          Bundle b = getArguments();
          fruitName = b.getString("fruitName");

          quantities = new ArrayList<>();
          prices = new ArrayList<>();
          keys = new ArrayList<>();

          firebaseDatabase = FirebaseDatabase.getInstance();
          databaseReference = firebaseDatabase.getReference();

          adapter = new ParticularFruitQtyPriceAdapter();
     }

     @Override
     public View onCreateView(LayoutInflater inflater, ViewGroup container,
                              Bundle savedInstanceState) {
          // Inflate the layout for this fragment
          View v = inflater.inflate(R.layout.fragment_particular_fruit_details, container, false);

          availabilityTextView = v.findViewById(R.id.availability_textView);
          noQtyPricesTextView = v.findViewById(R.id.no_qty_prices_textView);
          changeAvailabilityButton = v.findViewById(R.id.change_availability_button);
          progressBar = v.findViewById(R.id.progressBar);
          recyclerView = v.findViewById(R.id.qty_prices_recycler_view);
          floatingActionButton = v.findViewById(R.id.floating_action_button);
          ll = v.findViewById(R.id.linear_layout);

          //first thing is to go to the firebase database and check if there are any already saved
          //qty-prices, if yes then bring them here first
          checkExistingQtyPrices(new checkExistingQtyPricesInterface() {
               @Override
               public void onSnapshotExist() {
                    // get the adapter ready and show it in the recyclerview
                    adapter.setQuantitiesAndPrices(quantities, prices);
                    recyclerView.setAdapter(adapter);
                    new ItemTouchHelper(itemTouchHelperCallback).attachToRecyclerView(recyclerView);
                    recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

                    noQtyPricesTextView.setVisibility(View.GONE);
                    recyclerView.setVisibility(View.VISIBLE);
                    progressBar.setVisibility(View.GONE);
                    ll.setVisibility(View.VISIBLE);
                    floatingActionButton.setVisibility(View.VISIBLE);
               }

               @Override
               public void onSnapshotDontExist() {
                    //show no qty-price stored textview
                    //availabilityTextView.setText(NOT_SET_YET_STRING);
                    progressBar.setVisibility(View.GONE);
                    ll.setVisibility(View.VISIBLE);
                    recyclerView.setVisibility(View.GONE);
                    noQtyPricesTextView.setVisibility(View.VISIBLE);
                    floatingActionButton.setVisibility(View.VISIBLE);
               }
          });


          //then implement change availability button and floating action button
          changeAvailabilityButton.setOnClickListener(new View.OnClickListener() {
               @Override
               public void onClick(View v) {
                    if(changeAvailabilityButton.getText().equals(BUTTON_TEXT_AVAILABLE)){
                         //then change the text to AVAILABLE_STRING in the database
                         databaseReference.child("Fruits").child(fruitName).child("Availability").setValue(AVAILABLE_STRING).addOnSuccessListener(new OnSuccessListener<Void>() {
                              @Override
                              public void onSuccess(Void aVoid) {
                                   availabilityTextView.setText(AVAILABLE_STRING);
                                   availabilityTextView.setTextColor(Color.parseColor("#99cc00"));
                                   changeAvailabilityButton.setText(BUTTON_TEXT_NOT_AVAILABLE);
                              }
                         }).addOnFailureListener(new OnFailureListener() {
                              @Override
                              public void onFailure(@NonNull Exception e) {
                                   Toast.makeText(getContext(), "Something went wrong while changing availability", Toast.LENGTH_SHORT).show();
                              }
                         });

                    }
                    else{
                         //change the text to NOT_AVAILABLE_STRING in the database
                         databaseReference.child("Fruits").child(fruitName).child("Availability").setValue(NOT_AVAILABLE_STRING).addOnSuccessListener(new OnSuccessListener<Void>() {
                              @Override
                              public void onSuccess(Void aVoid) {
                                   availabilityTextView.setText(NOT_AVAILABLE_STRING);
                                   availabilityTextView.setTextColor(Color.parseColor("#ff4444"));
                                   changeAvailabilityButton.setText(BUTTON_TEXT_AVAILABLE);
                              }
                         }).addOnFailureListener(new OnFailureListener() {
                              @Override
                              public void onFailure(@NonNull Exception e) {
                                   Toast.makeText(getContext(), "Something went wrong while changing availability", Toast.LENGTH_SHORT).show();
                              }
                         });
                    }
               }
          });

          //in case of change availability button, change colors(red and green) appropriately
          //in case of floating action button, open new fragment, which has the facility
          // to add new item to the list and eventually to the database

          floatingActionButton.setOnClickListener(new View.OnClickListener() {
               @Override
               public void onClick(View v) {
                    //this button is used to add new qty-price entry in the database

                    //open a new fragment that will accept the user data (qty-price pair) and
                    //will make a new entry into the database

                    Bundle b = new Bundle();
                    b.putString("fruitName", fruitName);
                    Navigation.findNavController(v).navigate(R.id.action_particularFruitDetailsFragment_to_newQtyPriceEntryFragment, b);
               }
          });

          return v;
     }

     private ItemTouchHelper.SimpleCallback itemTouchHelperCallback = new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.RIGHT | ItemTouchHelper.LEFT) {
          @Override
          public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
               return false;
          }

          @Override
          public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
               int position = viewHolder.getAdapterPosition();
               progressBar.setVisibility(View.VISIBLE);
               //also disable the touch
               removeFromDatabase(position);
          }
     };

     private void removeFromDatabase(final int position) {
          String key = keys.get(position);
          Map<String, Object> map = new HashMap<>();
          map.put("qty/"+key, null);
          map.put("prices/"+key, null);
          databaseReference.child("Fruits").child(fruitName).updateChildren(map).addOnSuccessListener(new OnSuccessListener<Void>() {
               @Override
               public void onSuccess(Void aVoid) {
                    Snackbar.make(getView(), "Removed from database", Snackbar.LENGTH_SHORT).show();
                    quantities.remove(position);
                    prices.remove(position);
                    adapter.notifyItemRemoved(position);
                    progressBar.setVisibility(View.GONE);
               }
          }).addOnFailureListener(new OnFailureListener() {
               @Override
               public void onFailure(@NonNull Exception e) {
                    Snackbar.make(getView(), "Failed to remove from database", Snackbar.LENGTH_SHORT).show();
               }
          });
     }

     private void checkExistingQtyPrices(final checkExistingQtyPricesInterface Interface) {
          valueEventListener = new ValueEventListener() {
               @Override
               public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    if (dataSnapshot.child("qty").exists()){
                         quantities.clear();
                         prices.clear();
                         keys.clear();
                         //then there are some qty-prices already stored
                         for(DataSnapshot d1 : dataSnapshot.child("qty").getChildren()){
                              quantities.add(d1.getValue(String.class));
                              keys.add(d1.getKey());
                         }

                         for (DataSnapshot d2: dataSnapshot.child("prices").getChildren()){
                              prices.add(d2.getValue(String.class));
                         }

                         if(dataSnapshot.child("Availability").exists()){
                              String value = dataSnapshot.child("Availability").getValue(String.class);
                              availabilityTextView.setText(value);
                              if(value.equals(AVAILABLE_STRING)){
                                   availabilityTextView.setTextColor(Color.parseColor("#99cc00"));
                                   changeAvailabilityButton.setText(BUTTON_TEXT_NOT_AVAILABLE);
                              }
                              else{
                                   availabilityTextView.setTextColor(Color.parseColor("#ff4444"));
                                   changeAvailabilityButton.setText(BUTTON_TEXT_AVAILABLE);
                              }
                         }
                         else{
                              availabilityTextView.setText(NOT_SET_YET_STRING);
                         }

                         Interface.onSnapshotExist();
                    }
                    else{

                         if(dataSnapshot.child("Availability").exists()){
                              String value = dataSnapshot.child("Availability").getValue(String.class);
                              availabilityTextView.setText(value);
                              if(value.equals(AVAILABLE_STRING)){
                                   availabilityTextView.setTextColor(Color.parseColor("#99cc00"));
                                   changeAvailabilityButton.setText(BUTTON_TEXT_NOT_AVAILABLE);
                              }
                              else{
                                   availabilityTextView.setTextColor(Color.parseColor("#ff4444"));
                                   changeAvailabilityButton.setText(BUTTON_TEXT_AVAILABLE);
                              }
                         }
                         else{
                              availabilityTextView.setText(NOT_SET_YET_STRING);
                         }
                         //there are no qty-prices stored in the database
                         Interface.onSnapshotDontExist();
                    }


               }

               @Override
               public void onCancelled(@NonNull DatabaseError databaseError) {

               }
          };

          databaseReference.child("Fruits").child(fruitName).addListenerForSingleValueEvent(valueEventListener);
     }

     @Override
     public void onDestroy() {
          super.onDestroy();
          databaseReference.child("Fruits").child(fruitName).removeEventListener(valueEventListener);
     }
}
