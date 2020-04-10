package com.anantdevelopers.adminswipesinalpha2.AllPreviousOrdersFragment;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.anantdevelopers.adminswipesinalpha2.AllPreviousOrdersFragment.LocalDatabase.DatabaseNode;
import com.anantdevelopers.adminswipesinalpha2.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class ParticularPastOrderDetailsFragment extends Fragment {

     private TextView listOfFruitsTextView, grandTotalTextView, paymentMethodTextView, userNameTextView, phoneNumberTextView1, phoneNumberTextView2, statusTextView, addressTextView;

     private DatabaseNode node;

     public ParticularPastOrderDetailsFragment() {
          // Required empty public constructor
     }

     @Override
     public void onCreate(@Nullable Bundle savedInstanceState) {
          super.onCreate(savedInstanceState);

          Bundle b = getArguments();
          node = b.getParcelable("Order");
     }

     @Override
     public View onCreateView(LayoutInflater inflater, ViewGroup container,
                              Bundle savedInstanceState) {
          // Inflate the layout for this fragment
          View v = inflater.inflate(R.layout.fragment_particular_past_order_details, container, false);

          setIds(v);
          setTexts();

          return v;
     }

     private void setTexts(){
          listOfFruitsTextView.setText(node.getOrderDetails());
          grandTotalTextView.setText("Total bill for the User : " + node.getGrandTotal() + " Rs.");
          paymentMethodTextView.setText("Payment method : " + node.getPaymentMethod());
          userNameTextView.setText("Name : " + node.getName());
          phoneNumberTextView1.setText("Phone Number 1 : " + node.getPhoneNumber1());
          phoneNumberTextView2.setText("Phone Number 2 : " + node.getPhoneNumber2());
          addressTextView.setText("Address : " + node.getAddress());
          statusTextView.setText("Status : " + node.getOrderStatus());
     }

     private void setIds(View v) {
          listOfFruitsTextView = v.findViewById(R.id.listOfFruitsTextView);
          grandTotalTextView = v.findViewById(R.id.grandTotalTextView);
          paymentMethodTextView = v.findViewById(R.id.paymentMethodTextView);
          userNameTextView = v.findViewById(R.id.userNameTextView);
          phoneNumberTextView1 = v.findViewById(R.id.phoneNumberTextView1);
          phoneNumberTextView2 = v.findViewById(R.id.phoneNumberTextView2);
          addressTextView = v.findViewById(R.id.addressTextView);
          statusTextView = v.findViewById(R.id.statusTextView);
     }
}
