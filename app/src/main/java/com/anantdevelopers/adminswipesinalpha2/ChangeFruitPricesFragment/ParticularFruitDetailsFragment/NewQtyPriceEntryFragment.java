package com.anantdevelopers.adminswipesinalpha2.ChangeFruitPricesFragment.ParticularFruitDetailsFragment;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;

import com.anantdevelopers.adminswipesinalpha2.R;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;
import java.util.Map;

public class NewQtyPriceEntryFragment extends Fragment {

     private EditText qtyEditText, priceEditText;
     private Button addQtyPriceButton;
     private ProgressBar progressBar;

     private FirebaseDatabase firebaseDatabase;
     private DatabaseReference databaseReference;

     private String fruitName;

     public NewQtyPriceEntryFragment() {
          // Required empty public constructor
     }

     @Override
     public void onCreate(@Nullable Bundle savedInstanceState) {
          super.onCreate(savedInstanceState);

          Bundle b = getArguments();
          fruitName = b.getString("fruitName");

          firebaseDatabase = FirebaseDatabase.getInstance();
          databaseReference = firebaseDatabase.getReference();
     }

     @Override
     public View onCreateView(LayoutInflater inflater, ViewGroup container,
                              Bundle savedInstanceState) {
          // Inflate the layout for this fragment
          View v = inflater.inflate(R.layout.fragment_new_qty_price_entry, container, false);

          qtyEditText = v.findViewById(R.id.qty_edit_text);
          priceEditText = v.findViewById(R.id.price_edit_text);
          progressBar = v.findViewById(R.id.progressBar);

          addQtyPriceButton = v.findViewById(R.id.add_qty_price_button);

          addQtyPriceButton.setOnClickListener(new View.OnClickListener() {
               @Override
               public void onClick(View v) {
                    String qty = qtyEditText.getText().toString();
                    String price = priceEditText.getText().toString();

                    if(qty.isEmpty()){
                         Snackbar.make(v, "Enter Quantity first!", Snackbar.LENGTH_SHORT).show();
                    }
                    else if(price.isEmpty()){
                         Snackbar.make(v, "Enter Price first!", Snackbar.LENGTH_LONG).show();
                    }
                    else {
                         progressBar.setVisibility(View.VISIBLE);
                         //disable the screen touch also
                         sendToDatabase(v, qty, price);
                    }
               }
          });

          return v;
     }

     private void sendToDatabase(final View v, String quantity, String price) {
          String key = databaseReference.push().getKey();
          Map<String, Object> map = new HashMap<>();
          map.put("/qty/" + key, quantity);
          map.put("/prices/" + key, price);
          databaseReference.child("Fruits").child(fruitName).updateChildren(map).addOnSuccessListener(
                  new OnSuccessListener<Void>() {
                       @Override
                       public void onSuccess(Void aVoid) {
                            //Snackbar.make(v, "Success", Snackbar.LENGTH_SHORT).show();
                            Navigation.findNavController(v).popBackStack();
                       }
                  }
          ).addOnFailureListener(new OnFailureListener() {
               @Override
               public void onFailure(@NonNull Exception e) {
                    Snackbar.make(v, "Failure", Snackbar.LENGTH_SHORT).show();
               }
          });

     }
}
