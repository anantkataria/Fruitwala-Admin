package com.anantdevelopers.adminswipesinalpha2.AllOrdersFragment;

import android.graphics.Color;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.anantdevelopers.adminswipesinalpha2.Stuff.CheckoutUser;
import com.anantdevelopers.adminswipesinalpha2.Stuff.FruitItem;
import com.anantdevelopers.adminswipesinalpha2.Stuff.User;
import com.anantdevelopers.adminswipesinalpha2.AllPreviousOrdersFragment.LocalDatabase.AllPreviousOrdersViewModel;
import com.anantdevelopers.adminswipesinalpha2.AllPreviousOrdersFragment.LocalDatabase.DatabaseNode;
import com.anantdevelopers.adminswipesinalpha2.R;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.TimeZone;

public class ParticularOrderDetailsFragment extends Fragment {

     private AllPreviousOrdersViewModel allPreviousOrdersViewModel;

     private FirebaseDatabase firebaseDatabase;
     private DatabaseReference databaseReference;

     private String authPhoneNumber, pushKey, status, timeOrderPlaced;
     private String FruitsList = "";
     private int totalPrice = 0;

     private CheckoutUser user;
     private User u;

     private TextView listOfFruitsTextView, grandTotalTextView, paymentMethodTextView, userNameTextView, phoneNumberTextView1, phoneNumberTextView2, buildingTextView, wingLetterTextView, roomNumberTextView, statusTextView, orderPlacedTimeTextView;
     private Button statusOrderOnWayButton, statusOrderDeliveredButton, statusOrderCancelledButton;

     private SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
     private Date date;

     public ParticularOrderDetailsFragment() {
          // Required empty public constructor
     }

     @Override
     public void onCreate(@Nullable Bundle savedInstanceState) {
          super.onCreate(savedInstanceState);

          firebaseDatabase = FirebaseDatabase.getInstance();
          databaseReference = firebaseDatabase.getReference();

          Bundle b = getArguments();
          user = b.getParcelable("Order");
          u = b.getParcelable("user");

          authPhoneNumber = u.getPhoneNum1();
          pushKey = user.getFirebaseDatabaseKey();
          status = user.getStatus();
          timeOrderPlaced = user.getOrderPlacedDate();

          formatter.setTimeZone(TimeZone.getTimeZone("Asia/Kolkata"));
          date = new Date(Long.parseLong(timeOrderPlaced));

          allPreviousOrdersViewModel = new ViewModelProvider(this).get(AllPreviousOrdersViewModel.class);
     }

     @Override
     public View onCreateView(LayoutInflater inflater, ViewGroup container,
                              Bundle savedInstanceState) {
          // Inflate the layout for this fragment
          final View view = inflater.inflate(R.layout.fragment_particular_order_details, container, false);

          setIds(view);
          setTexts();

          statusOrderOnWayButton.setOnClickListener(new View.OnClickListener() {
               @Override
               public void onClick(View v) {

                    if(status.equals("ORDER ON WAY!")){
                         Toast.makeText(getContext(), "status already ORDER ON WAY!", Toast.LENGTH_SHORT).show();
                    }
                    else {
                         CheckoutUser updatedUser = user;
                         updatedUser.setUser(u);
                         updatedUser.setStatus("ORDER ON WAY!");

                         databaseReference.child("Orders").child(authPhoneNumber).child(pushKey).setValue(updatedUser).addOnSuccessListener(new OnSuccessListener<Void>() {
                              @Override
                              public void onSuccess(Void aVoid) {
                                   Toast.makeText(getContext(), "Updation successful", Toast.LENGTH_SHORT).show();
                                   statusTextView.setText("Status : " + user.getStatus());
                              }
                         }).addOnFailureListener(new OnFailureListener() {
                              @Override
                              public void onFailure(@NonNull Exception e) {
                                   Toast.makeText(getContext(), "Failure : " + e, Toast.LENGTH_SHORT).show();
                              }
                         });
                    }

                    //maybe show the notification that the order is on the way

               }
          });

          statusOrderDeliveredButton.setOnClickListener(new View.OnClickListener() {
               @Override
               public void onClick(View v) {
                    //if order is delivered, then remove it from "Orders" node in firebase database
                    //and add it to the node "Delivered Orders" from which it will get updated when user logins again
                    //user will remove it from Delivered orders and add it to the shared preferences
                    user.setStatus("ORDER DELIVERED");

                    long orderDeliveredDate = System.currentTimeMillis();
                    user.setOrderDeliveredOrCancelledDate(orderDeliveredDate+"");

                    removeOrderFromOrders();
                    moveOrderToDeliveredOrCancelled(v);
               }
          });

          statusOrderCancelledButton.setOnClickListener(new View.OnClickListener() {
               @Override
               public void onClick(View v) {

                    //first , the user will request the cancel and if I approve the request then
                    //only cancel the order

                    //when status is "CANCEL REQUESTED", if i have brought the fruit than
                    //i will call him and say cancel is not possible, otherwise i will press this button

                    //when the order cancelled is COD then it will be pretty much similar to the delivered case
                    //but when it is UPIpayment, we will have to do one extra work which is pay the user back and assure them that their payment
                    //will be debited back in 30 or so minutes
                    Bundle b = new Bundle();
                    b.putParcelable("Order", user);
                    b.putParcelable("user", u);
                    Navigation.findNavController(view).navigate(R.id.action_particularOrderDetailsFragment_to_orderCancellationReasonFragment, b);
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

     private void moveOrderToDeliveredOrCancelled(final View v) {
          databaseReference.child("Delivered or Cancelled").child(authPhoneNumber).child(pushKey).child("Order").setValue(user).addOnSuccessListener(new OnSuccessListener<Void>() {
               @Override
               public void onSuccess(Void aVoid) {
                    Toast.makeText(getContext(), "Added To 'Delivered or Cancelled'", Toast.LENGTH_SHORT).show();
                    //finish();
                    addOrderToAllPreviousOrdersFragment();
                    Navigation.findNavController(v).popBackStack();

               }
          }).addOnFailureListener(new OnFailureListener() {
               @Override
               public void onFailure(@NonNull Exception e) {
                    Toast.makeText(getContext(), "Failure : " + e, Toast.LENGTH_SHORT).show();
               }
          });

     }

     private void addOrderToAllPreviousOrdersFragment() {
          String Address ="Building:" + u.getBuilding() + ", wing:" + u.getWing() + ", Room:" + u.getRoom();
          DatabaseNode node = new DatabaseNode(u.getUserName(), u.getPhoneNum1(), u.getPhoneNum2(), FruitsList, Integer.toString(totalPrice), user.getOrderPlacedDate(), user.getOrderDeliveredOrCancelledDate() ,user.getStatus(), user.getPaymentMethod(), Address);
          allPreviousOrdersViewModel.insert(node);
     }

     private void setTexts() {
          ArrayList<FruitItem> fruits = user.getFruits();


          for(FruitItem f: fruits){
               FruitsList += f.getFruitName() + ", " + f.getFruitQty() + ", " + f.getFruitPrice() + "\n";
               totalPrice += Integer.valueOf(f.getFruitPrice().replaceAll("[Rs.\\s]", ""));
          }

          listOfFruitsTextView.setText(FruitsList);
          grandTotalTextView.setText("Total bill for the User : " + totalPrice + " Rs.");
          paymentMethodTextView.setText("Payment method : " + user.getPaymentMethod());



          //Log.e("###", u.getUserName());

          userNameTextView.setText("Name : " + u.getUserName());
          phoneNumberTextView1.setText("Phone Number 1 : " + u.getPhoneNum1());
          phoneNumberTextView2.setText("Phone Number 2 : " + u.getPhoneNum2());
          buildingTextView.setText("Building : " + u.getBuilding());
          wingLetterTextView.setText("WingLetter : " + u.getWing());
          roomNumberTextView.setText("Room Number : " + u.getRoom());

          String status = user.getStatus();
          statusTextView.setText("Status : " + status);
          if(status.equals("CANCELLATION REQUESTED")){
               statusTextView.setTextColor(Color.parseColor("#cc0000"));
          }

          orderPlacedTimeTextView.setText("Time Order Placed : " + formatter.format(date));
     }

     private void setIds(View v) {
          listOfFruitsTextView = v.findViewById(R.id.listOfFruitsTextView);
          grandTotalTextView = v.findViewById(R.id.grandTotalTextView);
          paymentMethodTextView = v.findViewById(R.id.paymentMethodTextView);
          userNameTextView = v.findViewById(R.id.userNameTextView);
          phoneNumberTextView1 = v.findViewById(R.id.phoneNumberTextView1);
          phoneNumberTextView2 = v.findViewById(R.id.phoneNumberTextView2);
          buildingTextView = v.findViewById(R.id.buildingTextView);
          wingLetterTextView = v.findViewById(R.id.wingLetterTextView);
          roomNumberTextView = v.findViewById(R.id.roomNumberTextView);
          statusTextView = v.findViewById(R.id.statusTextView);
          orderPlacedTimeTextView = v.findViewById(R.id.time_order_placed_text_view);

          statusOrderOnWayButton = v.findViewById(R.id.statusOrderOnWayButton);
          statusOrderDeliveredButton = v.findViewById(R.id.statusOrderDeliveredButton);
          statusOrderCancelledButton = v.findViewById(R.id.statusOrderCancelledButton);
     }
}
