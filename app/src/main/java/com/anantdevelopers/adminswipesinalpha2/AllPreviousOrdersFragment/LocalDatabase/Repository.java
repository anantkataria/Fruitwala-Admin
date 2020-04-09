package com.anantdevelopers.adminswipesinalpha2.AllPreviousOrdersFragment.LocalDatabase;

import android.app.Application;
import android.os.AsyncTask;

import androidx.lifecycle.LiveData;

import java.util.List;

public class Repository {
     private AllPreviousOrdersDao allPreviousOrdersDao;
     private LiveData<List<DatabaseNode>> allPreviousOrders;

     Repository(Application application) {
          AllPreviousOrdersDatabase database = AllPreviousOrdersDatabase.getInstance(application);
          allPreviousOrdersDao = database.allPreviousOrdersDao();
          allPreviousOrders = allPreviousOrdersDao.getAllOrders();
     }

     void insert(DatabaseNode node){
          new InsertAsyncTask(allPreviousOrdersDao).execute(node);
     }

     void delete(DatabaseNode node){
          new DeleteAsyncTask(allPreviousOrdersDao).execute(node);
     }

     void deleteAll(){
          new DeleteAllAsyncTask(allPreviousOrdersDao).execute();
     }

     LiveData<List<DatabaseNode>> getAllPreviousOrders() {
          return allPreviousOrders;
     }

     private static class InsertAsyncTask extends AsyncTask<DatabaseNode, Void, Void> {
          private AllPreviousOrdersDao allPreviousOrdersDao;

          private InsertAsyncTask(AllPreviousOrdersDao allPreviousOrdersDao){
               this.allPreviousOrdersDao = allPreviousOrdersDao;
          }

          @Override
          protected Void doInBackground(DatabaseNode... databaseNodes) {
               allPreviousOrdersDao.insert(databaseNodes[0]);
               return null;
          }
     }

     private static class DeleteAsyncTask extends AsyncTask<DatabaseNode, Void, Void> {
          private AllPreviousOrdersDao allPreviousOrdersDao;

          private DeleteAsyncTask(AllPreviousOrdersDao allPreviousOrdersDao){
               this.allPreviousOrdersDao = allPreviousOrdersDao;
          }

          @Override
          protected Void doInBackground(DatabaseNode... databaseNodes) {
               allPreviousOrdersDao.delete(databaseNodes[0]);
               return null;
          }
     }

     private static class DeleteAllAsyncTask extends AsyncTask<Void, Void, Void> {
          private AllPreviousOrdersDao allPreviousOrdersDao;

          private DeleteAllAsyncTask(AllPreviousOrdersDao allPreviousOrdersDao){
               this.allPreviousOrdersDao = allPreviousOrdersDao;
          }

          @Override
          protected Void doInBackground(Void... Voids) {
               allPreviousOrdersDao.deleteAll();
               return null;
          }
     }
}
