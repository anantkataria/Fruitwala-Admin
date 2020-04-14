package com.anantdevelopers.adminswipesinalpha2.ChangeFruitPricesFragment;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.anantdevelopers.adminswipesinalpha2.R;

import java.util.ArrayList;


public class ChangeFruitPricesFragment extends Fragment {

     private ArrayList<String> fruitNames;
     private RecyclerView recyclerView;
     private ChangeFruitPricesAdapter adapter;

     public ChangeFruitPricesFragment() {
          // Required empty public constructor
     }

     @Override
     public void onCreate(Bundle savedInstanceState) {
          super.onCreate(savedInstanceState);
          fruitNames = new ArrayList<>();
          fruitNames.add("Bananas");
          fruitNames.add("Apple");
          fruitNames.add("Oranges");
          fruitNames.add("Pomegranate");
          fruitNames.add("Grapes");
          fruitNames.add("Chikoo");
          fruitNames.add("Guava");
          fruitNames.add("Kiwifruit");
          fruitNames.add("Watermelon");
          fruitNames.add("Strawberries");
          fruitNames.add("Pears");
          fruitNames.add("Mango");
          fruitNames.add("Papaya");
          fruitNames.add("Pineapple");
          fruitNames.add("CustardApple");
          fruitNames.add("Black Plum");

          adapter = new ChangeFruitPricesAdapter(fruitNames);
     }

     @Override
     public View onCreateView(LayoutInflater inflater, ViewGroup container,
                              Bundle savedInstanceState) {
          // Inflate the layout for this fragment
          View v = inflater.inflate(R.layout.fragment_change_fruit_prices, container, false);

          recyclerView = v.findViewById(R.id.recyclerViewForChangeFruitPrices);
          recyclerView.setAdapter(adapter);
          recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

          handleItemClicks(v);

          return v;
     }

     private void handleItemClicks(final View v) {
          adapter.setOnItemClickListener(new ChangeFruitPricesAdapter.OnItemClickListener() {
               @Override
               public void onItemClick(int position) {
                    Bundle b = new Bundle();
                    b.putString("fruitName", fruitNames.get(position));
                    Navigation.findNavController(v).navigate(R.id.action_change_fruit_prices_dest_to_particularFruitDetailsFragment, b);
               }
          });
     }
}
