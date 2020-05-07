package com.anantdevelopers.adminswipesinalpha2.AllOrdersFragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.anantdevelopers.adminswipesinalpha2.Stuff.CheckoutUser;
import com.anantdevelopers.adminswipesinalpha2.Stuff.User;
import com.anantdevelopers.adminswipesinalpha2.R;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.TimeZone;

//this adapter will be common for both AllOrdersFragment and AllPreviousOrderFragment
public class OrderItemAdapter extends RecyclerView.Adapter<OrderItemAdapter.ViewHolder> {

     private ArrayList<CheckoutUser> orders;
     private OnItemClickListener mListener;

     private SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");

     OrderItemAdapter(ArrayList<CheckoutUser> orders){
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
          CheckoutUser user = orders.get(position);
          User u = user.getUser();
          String orderTime = user.getOrderPlacedDate();
          Date date = new Date(Long.parseLong(orderTime));
          formatter.setTimeZone(TimeZone.getTimeZone("Asia/Kolkata"));
          holder.userNameTxt.setText(u.getUserName());
          holder.userPhoneNumberTxt.setText(u.getPhoneNum1());
          holder.userAddressTxt.setText(u.getWing() + " - " + u.getRoom());
          holder.orderPlacedTimeTxt.setText("Ordered : " + formatter.format(date));
          if(user.getStatus().equals("CANCELLATION REQUESTED")){
               holder.cancellationRequestedTxt.setVisibility(View.VISIBLE);
          }
     }

     @Override
     public int getItemCount() {
          return orders.size();
     }

     static class ViewHolder extends RecyclerView.ViewHolder {

          TextView userNameTxt;
          TextView userAddressTxt;
          TextView userPhoneNumberTxt;
          TextView orderPlacedTimeTxt;
          TextView cancellationRequestedTxt;

          ViewHolder(@NonNull View itemView, final OnItemClickListener listener) {
               super(itemView);
               userNameTxt = itemView.findViewById(R.id.userNameTxt);
               userAddressTxt = itemView.findViewById(R.id.userAddressTxt);
               userPhoneNumberTxt = itemView.findViewById(R.id.userPhoneNumberTxt);
               orderPlacedTimeTxt = itemView.findViewById(R.id.order_placed_time_text_view);
               cancellationRequestedTxt = itemView.findViewById(R.id.cancellation_requested_text_view);

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