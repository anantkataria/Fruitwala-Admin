package com.anantdevelopers.adminswipesinalpha2.AllPreviousOrdersFragment.LocalDatabase;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;

@Dao
public interface AllPreviousOrdersDao {

     @Insert
     void insert(DatabaseNode node);

     @Delete
     void delete(DatabaseNode node);

     @Query("DELETE FROM all_previous_orders_database")
     void deleteAll();

     @Query("SELECT * FROM all_previous_orders_database")
     LiveData<List<DatabaseNode>> getAllOrders();

}
