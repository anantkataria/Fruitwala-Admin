package com.anantdevelopers.adminswipesinalpha2.AllPreviousOrdersFragment.LocalDatabase;

import android.provider.ContactsContract;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.anantdevelopers.adminswipesinalpha2.R;

import java.sql.Time;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.TimeZone;

public class AllPreviousOrdersAdapter extends RecyclerView.Adapter<AllPreviousOrdersAdapter.ViewHolder> {

     private List<DatabaseNode> AllPreviousOrders = new ArrayList<>();
     private OnItemClickListener mListener;

     private SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");

     public interface OnItemClickListener{
          void onItemClick(int position);
     }

     public void setOnItemClickListener(OnItemClickListener listener){
          mListener = listener;
     }

     public DatabaseNode getItemAt(int position){
          return AllPreviousOrders.get(position);
     }

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
          return new ViewHolder(itemView, mListener);
     }

     @Override
     public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
          DatabaseNode currentNode = AllPreviousOrders.get(position);

          formatter.setTimeZone(TimeZone.getTimeZone("Asia/Kolkata"));

          String timeOrderPlaced = currentNode.getTimeOrderPlaced();
          Date orderPlacedTime = new Date(Long.parseLong(timeOrderPlaced));

          String timeOrderDeliveredOrCancelled = currentNode.getTimeOrderDeliveredOrCancelled();
          Date orderDeliveredOrCancelledTime = new Date(Long.parseLong(timeOrderDeliveredOrCancelled));

          String TimeOrderPlacedText = "Ordered : " + formatter.format(orderPlacedTime);
          String TimeOrderDeliveredOrCancelledText = formatter.format(orderDeliveredOrCancelledTime);

          String status = currentNode.getOrderStatus();
          if(status.equals("ORDER DELIVERED")){
               TimeOrderDeliveredOrCancelledText = "Delivered : " + TimeOrderDeliveredOrCancelledText;
          }else {
               TimeOrderDeliveredOrCancelledText = "Cancelled : " + TimeOrderDeliveredOrCancelledText;
          }

          holder.NameTxt.setText(currentNode.getName());
          holder.PhoneNumberTxt.setText(currentNode.getPhoneNumber1());
          holder.TimeOrderPlaced.setText(TimeOrderPlacedText);
          holder.TimeOrderDeliveredOrCancelled.setText(TimeOrderDeliveredOrCancelledText);
     }

     @Override
     public int getItemCount() {
          return AllPreviousOrders.size();
     }

     static class ViewHolder extends RecyclerView.ViewHolder {
          private TextView NameTxt;
          private TextView PhoneNumberTxt;
          private TextView TimeOrderPlaced;
          private TextView TimeOrderDeliveredOrCancelled;

          ViewHolder(@NonNull View itemView, final OnItemClickListener listener) {
               super(itemView);
               NameTxt = itemView.findViewById(R.id.userNameTxt);
               PhoneNumberTxt = itemView.findViewById(R.id.userPhoneNumberTxt);
               TimeOrderPlaced = itemView.findViewById(R.id.order_placed_time_text_view);
               TimeOrderDeliveredOrCancelled = itemView.findViewById(R.id.order_delivered_or_cancelled_text_view);

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
