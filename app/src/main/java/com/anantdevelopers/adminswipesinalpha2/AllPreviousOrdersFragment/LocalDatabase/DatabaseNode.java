package com.anantdevelopers.adminswipesinalpha2.AllPreviousOrdersFragment.LocalDatabase;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "all_previous_orders_database")
public class DatabaseNode {

     @PrimaryKey(autoGenerate = true)
     private int id;

     private String Name;
     private String PhoneNumber1;
     private String PhoneNumber2;
     private String OrderDetails;
     private String GrandTotal;
     //TODO Implement below both
     //private String OrderStartDate;
     //private String OrderCompleteDate;
     private String OrderStatus;
     private String PaymentMethod;
     private String Address;

     public DatabaseNode(String Name, String PhoneNumber1, String PhoneNumber2, String OrderDetails, String GrandTotal,
                         /*String OrderStartDate, String OrderCompleteDate,*/ String OrderStatus, String PaymentMethod,
                         String Address){
          this.Name = Name;
          this.PhoneNumber1 = PhoneNumber1;
          this.PhoneNumber2 = PhoneNumber2;
          this.OrderDetails = OrderDetails;
          this.GrandTotal = GrandTotal;
          this.OrderStatus = OrderStatus;
          this.PaymentMethod = PaymentMethod;
          this.Address = Address;
     }

     public void setId(int id) {
          this.id = id;
     }

     public int getId() {
          return id;
     }

     public String getName() {
          return Name;
     }

     public String getPhoneNumber1() {
          return PhoneNumber1;
     }

     public String getPhoneNumber2() {
          return PhoneNumber2;
     }

     public String getOrderDetails() {
          return OrderDetails;
     }

     public String getGrandTotal() {
          return GrandTotal;
     }

     public String getOrderStatus() {
          return OrderStatus;
     }

     public String getPaymentMethod() {
          return PaymentMethod;
     }

     public String getAddress() {
          return Address;
     }

}
