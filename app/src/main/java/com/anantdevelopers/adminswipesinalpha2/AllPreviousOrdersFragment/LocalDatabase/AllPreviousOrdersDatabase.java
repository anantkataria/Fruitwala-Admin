package com.anantdevelopers.adminswipesinalpha2.AllPreviousOrdersFragment.LocalDatabase;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

@Database(entities = {DatabaseNode.class}, version = 1, exportSchema = false)
public abstract class AllPreviousOrdersDatabase extends RoomDatabase {
     private static AllPreviousOrdersDatabase instance;

     public abstract AllPreviousOrdersDao allPreviousOrdersDao();

     static synchronized AllPreviousOrdersDatabase getInstance(Context context){
          if(instance == null){
               instance = Room.databaseBuilder(context.getApplicationContext(), AllPreviousOrdersDatabase.class,
                       "all_orders_database")
                       .fallbackToDestructiveMigration()
                       .build();
          }
          return instance;
     }
}
