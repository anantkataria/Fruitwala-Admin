package com.anantdevelopers.adminswipesinalpha2.AdaptersAndStuff;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.anantdevelopers.adminswipesinalpha2.R;

import java.util.ArrayList;

//this adapter will be common for both AllOrdersFragment and AllPreviousOrderFragment
public class OrderItemAdapter extends RecyclerView.Adapter<OrderItemAdapter.ViewHolder> {

     private ArrayList<CheckoutUser> orders;
     private OnItemClickListener mListener;

     public OrderItemAdapter(ArrayList<CheckoutUser> orders){
          this.orders = orders;
     }

     public interface OnItemClickListener{
          void onItemClick(int position);
     }

     public void setOnItemClickListener(OnItemClickListener listener){
          mListener = listener;
     }

     @NonNull
     @Override
     public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
          View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_current_order, parent, false);
          return new ViewHolder(v, mListener);
     }

     @Override
     public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
          User u = orders.get(position).getUser();
          holder.userNameTxt.setText(u.getUserName());
          holder.userPhoneNumberTxt.setText(u.getPhoneNum1());
          holder.userAddressTxt.setText(u.getWing() + " - " + u.getRoom());
     }

     @Override
     public int getItemCount() {
          return orders.size();
     }

     static class ViewHolder extends RecyclerView.ViewHolder {

          TextView userNameTxt;
          TextView userAddressTxt;
          TextView userPhoneNumberTxt;

          ViewHolder(@NonNull View itemView, final OnItemClickListener listener) {
               super(itemView);
               userNameTxt = itemView.findViewById(R.id.userNameTxt);
               userAddressTxt = itemView.findViewById(R.id.userAddressTxt);
               userPhoneNumberTxt = itemView.findViewById(R.id.userPhoneNumberTxt);

               itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                         if(listener != null){
                              int position = getAdapterPosition();
                              if(position != RecyclerView.NO_POSITION){
                                   listener.onItemClick(position);
                              }
                         }
                    }
               });
          }

     }
}