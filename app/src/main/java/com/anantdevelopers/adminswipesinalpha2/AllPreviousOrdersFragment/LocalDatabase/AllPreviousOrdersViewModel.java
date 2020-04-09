package com.anantdevelopers.adminswipesinalpha2.AllPreviousOrdersFragment.LocalDatabase;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import java.util.List;

public class AllPreviousOrdersViewModel extends AndroidViewModel {

     private Repository repository;
     private LiveData<List<DatabaseNode>> allPreviousOrders;

     public AllPreviousOrdersViewModel(@NonNull Application application) {
          super(application);
          repository = new Repository(application);
          allPreviousOrders = repository.getAllPreviousOrders();
     }

     public void insert(DatabaseNode node){
          repository.insert(node);
     }

     public void delete(DatabaseNode node){
          repository.delete(node);
     }

     public void deleteAll() {
          repository.deleteAll();
     }

     public LiveData<List<DatabaseNode>> getAllPreviousOrders() {
          return allPreviousOrders;
     }
}
