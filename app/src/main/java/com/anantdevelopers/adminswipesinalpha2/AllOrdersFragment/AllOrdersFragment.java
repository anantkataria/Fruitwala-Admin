package com.anantdevelopers.adminswipesinalpha2.AllOrdersFragment;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.anantdevelopers.adminswipesinalpha2.Stuff.CheckoutUser;
import com.anantdevelopers.adminswipesinalpha2.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;


public class AllOrdersFragment extends Fragment {

     private FirebaseDatabase firebaseDatabase;
     private DatabaseReference databaseReference;

     private RecyclerView recyclerView;
     private ProgressBar progressBar;
     private TextView noOrdersTxt;

     private OrderItemAdapter adapter;

     private ArrayList<CheckoutUser> orders;

     private interface firebaseRetrieval {
          void afterFetch();
     }

     public AllOrdersFragment() {
          // Required empty public constructor
     }

     @Override
     public void onCreate(@Nullable Bundle savedInstanceState) {
          super.onCreate(savedInstanceState);

          firebaseDatabase = FirebaseDatabase.getInstance();
          databaseReference = firebaseDatabase.getReference();

          orders = new ArrayList<>();
     }

     @Override
     public View onCreateView(LayoutInflater inflater, ViewGroup container,
                              Bundle savedInstanceState) {
          // Inflate the layout for this fragment
          final View v = inflater.inflate(R.layout.fragment_all_orders, container, false);

          recyclerView = v.findViewById(R.id.recyclerViewForCurrentOrders);
          progressBar = v.findViewById(R.id.progressBar);
          noOrdersTxt = v.findViewById(R.id.noOrdersTxt);

          fetchOrders(new firebaseRetrieval() {
               @Override
               public void afterFetch() {
                    if(orders.isEmpty()){
                         progressBar.setVisibility(View.GONE);
                         noOrdersTxt.setVisibility(View.VISIBLE);
                    }
                    else {
                         adapter = new OrderItemAdapter(orders);
                         progressBar.setVisibility(View.GONE);
                         recyclerView.setVisibility(View.VISIBLE);
                         recyclerView.setAdapter(adapter);
                         recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

                         //handle on item click
                         handleItemClicks(v);
                    }
               }
          });

          return v;
     }

     private void handleItemClicks(final View v) {
          adapter.setOnItemClickListener(new OrderItemAdapter.OnItemClickListener() {
               @Override
               public void onItemClick(int position) {
                    Bundle b = new Bundle();
                    b.putParcelable("Order", orders.get(position));
                    b.putParcelable("user", orders.get(position).getUser());
                    //Navigation.createNavigateOnClickListener(R.id.action_all_orders_dest_to_particularOrderDetailsFragment, null);
                    Navigation.findNavController(v).navigate(R.id.action_all_orders_dest_to_particularOrderDetailsFragment, b);
               }
          });
     }

     private void fetchOrders(final firebaseRetrieval fr) {
          databaseReference.child("Orders").addValueEventListener(new ValueEventListener() {
               @Override
               public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    orders.clear(); //need to clear arraylist other wise there will be duplicate items in the list

                    for(DataSnapshot data : dataSnapshot.getChildren()){
                         for(DataSnapshot d : data.getChildren()){
                              orders.add(d.getValue(CheckoutUser.class));
                         }
                    }

                    fr.afterFetch();
               }

               @Override
               public void onCancelled(@NonNull DatabaseError databaseError) {
                    //implement error response
               }
          });
     }
}

