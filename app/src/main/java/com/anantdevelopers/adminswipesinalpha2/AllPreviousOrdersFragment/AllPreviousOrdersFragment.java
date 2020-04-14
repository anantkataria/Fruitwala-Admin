package com.anantdevelopers.adminswipesinalpha2.AllPreviousOrdersFragment;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.anantdevelopers.adminswipesinalpha2.AllPreviousOrdersFragment.LocalDatabase.AllPreviousOrdersAdapter;
import com.anantdevelopers.adminswipesinalpha2.AllPreviousOrdersFragment.LocalDatabase.AllPreviousOrdersViewModel;
import com.anantdevelopers.adminswipesinalpha2.AllPreviousOrdersFragment.LocalDatabase.DatabaseNode;
import com.anantdevelopers.adminswipesinalpha2.R;

import java.util.List;

public class AllPreviousOrdersFragment extends Fragment {

     private TextView noOrdersTxt;
     private ProgressBar progressBar;
     private RecyclerView recyclerView;
     private AllPreviousOrdersAdapter adapter;
     private AllPreviousOrdersViewModel viewModel;

     public AllPreviousOrdersFragment() {
          // Required empty public constructor
     }

     @Override
     public void onCreate(Bundle savedInstanceState) {
          super.onCreate(savedInstanceState);
          viewModel = new ViewModelProvider(this).get(AllPreviousOrdersViewModel.class);
     }

     @Override
     public View onCreateView(LayoutInflater inflater, ViewGroup container,
                              Bundle savedInstanceState) {
          // Inflate the layout for this fragment
          final View v = inflater.inflate(R.layout.fragment_all_previous_orders, container, false);

          noOrdersTxt = v.findViewById(R.id.noOrdersTxt);
          progressBar = v.findViewById(R.id.progressBar);
          recyclerView = v.findViewById(R.id.recyclerViewForPreviousOrders);
          adapter = new AllPreviousOrdersAdapter();

          recyclerView.setAdapter(adapter);
          recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

          viewModel.getAllPreviousOrders().observe(getViewLifecycleOwner(), new Observer<List<DatabaseNode>>() {
               @Override
               public void onChanged(List<DatabaseNode> databaseNodes) {
                    if(databaseNodes.isEmpty()){
                         noOrdersTxt.setVisibility(View.VISIBLE);
                    }
                    else {
                         recyclerView.setVisibility(View.VISIBLE);
                         adapter.setAllPreviousOrders(databaseNodes);
                         noOrdersTxt.setVisibility(View.GONE);
                    }

                    progressBar.setVisibility(View.GONE);
               }
          });

          adapter.setOnItemClickListener(
                  new AllPreviousOrdersAdapter.OnItemClickListener() {
                       @Override
                       public void onItemClick(int position) {
                            Bundle b = new Bundle();
                            DatabaseNode node = adapter.getItemAt(position);
                            b.putParcelable("Order",node);
                            Navigation.findNavController(v).navigate(R.id.action_all_previous_orders_dest_to_particularPastOrderDetailsFragment, b);
                       }
                  }
          );

          return v;
     }
}
