package com.anantdevelopers.adminswipesinalpha2.ChangeFruitPricesFragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.anantdevelopers.adminswipesinalpha2.R;

import java.util.ArrayList;


public class ChangeFruitPricesAdapter extends RecyclerView.Adapter<ChangeFruitPricesAdapter.ViewHolder> {

     private ArrayList<String> fruitNames;
     private OnItemClickListener mListener;

     public ChangeFruitPricesAdapter(ArrayList<String> fruitNames) {
          this.fruitNames = fruitNames;
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
          View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_change_fruit_price, parent, false);
          return new ViewHolder(v, mListener);
     }

     @Override
     public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
          String currentName = fruitNames.get(position);
          holder.fruitNameTxt.setText(currentName);
     }

     @Override
     public int getItemCount() {
          return fruitNames.size();
     }

     public static class ViewHolder extends RecyclerView.ViewHolder {

          private TextView fruitNameTxt;

          public ViewHolder(@NonNull View itemView, final OnItemClickListener listener) {
               super(itemView);
               fruitNameTxt = itemView.findViewById(R.id.fruit_name);

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
