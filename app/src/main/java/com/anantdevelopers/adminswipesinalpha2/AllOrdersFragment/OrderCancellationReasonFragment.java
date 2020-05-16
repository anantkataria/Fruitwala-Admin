package com.anantdevelopers.adminswipesinalpha2.AllOrdersFragment;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.anantdevelopers.adminswipesinalpha2.AllPreviousOrdersFragment.LocalDatabase.AllPreviousOrdersViewModel;
import com.anantdevelopers.adminswipesinalpha2.AllPreviousOrdersFragment.LocalDatabase.DatabaseNode;
import com.anantdevelopers.adminswipesinalpha2.R;
import com.anantdevelopers.adminswipesinalpha2.Stuff.CheckoutUser;
import com.anantdevelopers.adminswipesinalpha2.Stuff.FruitItem;
import com.anantdevelopers.adminswipesinalpha2.Stuff.User;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.HashMap;


public class OrderCancellationReasonFragment extends Fragment {

     private EditText cancellationReasonEditText;
     private LinearLayout parentLayout;

     private DatabaseReference databaseReference;

     private CheckoutUser user;
     private User u;

     private String authPhoneNumber, pushKey;

     public OrderCancellationReasonFragment() {
          // Required empty public constructor
     }

     @Override
     public void onCreate(@Nullable Bundle savedInstanceState) {
          super.onCreate(savedInstanceState);

          Bundle b = getArguments();
          user = b.getParcelable("Order");
          u = b.getParcelable("user");

          authPhoneNumber = u.getPhoneNum1();
          pushKey = user.getFirebaseDatabaseKey();

          databaseReference = FirebaseDatabase.getInstance().getReference();
     }

     @Override
     public View onCreateView(LayoutInflater inflater, ViewGroup container,
                              Bundle savedInstanceState) {
          // Inflate the layout for this fragment
          final View view = inflater.inflate(R.layout.fragment_order_cancellation_reason, container, false);

          parentLayout = view.findViewById(R.id.parent_layout);
          Button cancelOrderButton = view.findViewById(R.id.cancel_order_button);
          cancellationReasonEditText = view.findViewById(R.id.cancellation_reason_edit_text);

          cancelOrderButton.setOnClickListener(new View.OnClickListener() {
               @Override
               public void onClick(final View v) {
                    final String text = cancellationReasonEditText.getText().toString();

                    if (text.isEmpty()){
                         Snackbar.make(parentLayout, "Enter Cancellation Reason!", Snackbar.LENGTH_SHORT).show();
                    }
                    else {
                         // add the order in to "delivered or cancelled" with the reason added, which will be used
                         // for body of Notification which will be sent to that user.
                         AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
                         builder.setMessage("Are you sure you want to cancel this order?");
                         builder.setPositiveButton("Yes please", new DialogInterface.OnClickListener() {
                              @Override
                              public void onClick(DialogInterface dialog, int which) {
                                   user.setStatus("ORDER CANCELLED");
                                   long orderDeliveredDate = System.currentTimeMillis();
                                   user.setOrderDeliveredOrCancelledDate(orderDeliveredDate + "");

                                   removeOrderFromOrders();
                                   moveOrderToDeliveredOrCancelled(view, text);

                              }
                         }).setNegativeButton("Nope", new DialogInterface.OnClickListener() {
                              @Override
                              public void onClick(DialogInterface dialog, int which) {
                                   dialog.dismiss();
                              }
                         });

                         AlertDialog dialog = builder.create();
                         dialog.show();
                    }
               }
          });

          return view;
     }

     private void removeOrderFromOrders() {
          databaseReference.child("Orders").child(authPhoneNumber).child(pushKey).removeValue().addOnSuccessListener(new OnSuccessListener<Void>() {
               @Override
               public void onSuccess(Void aVoid) {
                    Toast.makeText(getContext(), "removed from orders", Toast.LENGTH_SHORT).show();
               }
          }).addOnFailureListener(new OnFailureListener() {
               @Override
               public void onFailure(@NonNull Exception e) {
                    Toast.makeText(getContext(), "Error in removeOrderFromOrders() :" + e, Toast.LENGTH_SHORT).show();
               }
          });
     }

     private void moveOrderToDeliveredOrCancelled(final View v, String reason) {

          HashMap<String, Object> map = new HashMap<>();
          map.put("Order", user);
          map.put("Reason", reason);

          databaseReference.child("Delivered or Cancelled").child(authPhoneNumber).child(pushKey).setValue(map).addOnSuccessListener(new OnSuccessListener<Void>() {
               @Override
               public void onSuccess(Void aVoid) {
                    Toast.makeText(getContext(), "Added To 'Delivered or Cancelled'", Toast.LENGTH_SHORT).show();
                    //finish();
                    addOrderToAllPreviousOrdersFragment();
                    Navigation.findNavController(v).popBackStack(R.id.all_orders_dest, false);

               }
          }).addOnFailureListener(new OnFailureListener() {
               @Override
               public void onFailure(@NonNull Exception e) {
                    Toast.makeText(getContext(), "Failure : " + e, Toast.LENGTH_SHORT).show();
               }
          });

     }

     private void addOrderToAllPreviousOrdersFragment() {
          String FruitsList="";
          int totalPrice=0;
          ArrayList<FruitItem> fruits = user.getFruits();

          AllPreviousOrdersViewModel allPreviousOrdersViewModel = new ViewModelProvider(this).get(AllPreviousOrdersViewModel.class);

          for(FruitItem f: fruits){
               FruitsList += f.getFruitName() + ", " + f.getFruitQty() + ", " + f.getFruitPrice() + "\n";
               totalPrice += Integer.valueOf(f.getFruitPrice().replaceAll("[Rs.\\s]", ""));
          }

          String Address ="Building:" + u.getBuilding() + ", wing:" + u.getWing() + ", Room:" + u.getRoom();
          DatabaseNode node = new DatabaseNode(u.getUserName(), u.getPhoneNum1(), u.getPhoneNum2(), FruitsList, Integer.toString(totalPrice), user.getOrderPlacedDate(), user.getOrderDeliveredOrCancelledDate() ,user.getStatus(), user.getPaymentMethod(), Address);
          allPreviousOrdersViewModel.insert(node);
     }

}
