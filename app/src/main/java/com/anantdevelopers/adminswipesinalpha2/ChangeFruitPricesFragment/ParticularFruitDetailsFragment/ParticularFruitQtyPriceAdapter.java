package com.anantdevelopers.adminswipesinalpha2.ChangeFruitPricesFragment.ParticularFruitDetailsFragment;

import android.text.Layout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.anantdevelopers.adminswipesinalpha2.R;

import java.lang.reflect.Array;
import java.util.ArrayList;

public class ParticularFruitQtyPriceAdapter extends RecyclerView.Adapter<ParticularFruitQtyPriceAdapter.ViewHolder> {

     private ArrayList<String> quantities, prices;

     public void setQuantitiesAndPrices(ArrayList<String> quantities, ArrayList<String> prices){
          this.quantities = quantities;
          this.prices = prices;
     }

     @NonNull
     @Override
     public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
          View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_particular_fruit_qty_price, parent, false);
          return new ViewHolder(v);
     }

     @Override
     public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
          holder.qtyTxt.setText(quantities.get(position));
          holder.priceTxt.setText(prices.get(position));
     }

     @Override
     public int getItemCount() {
          return quantities.size();
     }

     public static class ViewHolder extends RecyclerView.ViewHolder {

          private TextView qtyTxt, priceTxt;

          public ViewHolder(@NonNull View itemView) {
               super(itemView);
               qtyTxt = itemView.findViewById(R.id.qty_textView);
               priceTxt = itemView.findViewById(R.id.price_textView);
          }
     }
}
