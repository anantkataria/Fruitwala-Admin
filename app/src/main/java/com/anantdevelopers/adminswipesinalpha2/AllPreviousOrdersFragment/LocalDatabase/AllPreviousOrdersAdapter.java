package com.anantdevelopers.adminswipesinalpha2.AllPreviousOrdersFragment.LocalDatabase;

import android.provider.ContactsContract;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.anantdevelopers.adminswipesinalpha2.R;

import java.util.ArrayList;
import java.util.List;

public class AllPreviousOrdersAdapter extends RecyclerView.Adapter<AllPreviousOrdersAdapter.ViewHolder> {

     private List<DatabaseNode> AllPreviousOrders = new ArrayList<>();

     public void setAllPreviousOrders(List<DatabaseNode> AllPreviousOrders){
          this.AllPreviousOrders = AllPreviousOrders;
          notifyDataSetChanged();
          //TODO implement better than notifyDataSetChanged from CodingInFlow
     }

     @NonNull
     @Override
     public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
          View itemView = LayoutInflater.from(parent.getContext())
                  .inflate(R.layout.item_all_previous_orders, parent, false);
          return new ViewHolder(itemView);
     }

     @Override
     public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
          DatabaseNode currentNode = AllPreviousOrders.get(position);
          holder.NameTxt.setText(currentNode.getName());
          holder.PhoneNumberTxt.setText(currentNode.getPhoneNumber1());
     }

     @Override
     public int getItemCount() {
          return AllPreviousOrders.size();
     }

     static class ViewHolder extends RecyclerView.ViewHolder {
          private TextView NameTxt;
          private TextView PhoneNumberTxt;

          public ViewHolder(@NonNull View itemView) {
               super(itemView);
               NameTxt = itemView.findViewById(R.id.nameTxt);
               PhoneNumberTxt = itemView.findViewById(R.id.phoneNumberTxt);
          }
     }
}
