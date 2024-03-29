package com.anantdevelopers.adminswipesinalpha2.AllPreviousOrdersFragment.LocalDatabase;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.sql.Time;

@Entity(tableName = "all_previous_orders_database")
public class DatabaseNode implements Parcelable {

     @PrimaryKey(autoGenerate = true)
     private int id;

     private String Name;
     private String PhoneNumber1;
     private String PhoneNumber2;
     private String OrderDetails;
     private String GrandTotal;
     private String TimeOrderPlaced;
     private String TimeOrderDeliveredOrCancelled;
     private String OrderStatus;
     private String PaymentMethod;
     private String Address;

     public DatabaseNode(String Name, String PhoneNumber1, String PhoneNumber2, String OrderDetails, String GrandTotal,
                         String TimeOrderPlaced, String TimeOrderDeliveredOrCancelled, String OrderStatus, String PaymentMethod,
                         String Address){
          this.Name = Name;
          this.PhoneNumber1 = PhoneNumber1;
          this.PhoneNumber2 = PhoneNumber2;
          this.OrderDetails = OrderDetails;
          this.GrandTotal = GrandTotal;
          this.TimeOrderPlaced = TimeOrderPlaced;
          this.TimeOrderDeliveredOrCancelled = TimeOrderDeliveredOrCancelled;
          this.OrderStatus = OrderStatus;
          this.PaymentMethod = PaymentMethod;
          this.Address = Address;
     }


     protected DatabaseNode(Parcel in) {
          id = in.readInt();
          Name = in.readString();
          PhoneNumber1 = in.readString();
          PhoneNumber2 = in.readString();
          OrderDetails = in.readString();
          GrandTotal = in.readString();
          TimeOrderPlaced = in.readString();
          TimeOrderDeliveredOrCancelled = in.readString();
          OrderStatus = in.readString();
          PaymentMethod = in.readString();
          Address = in.readString();
     }

     public static final Creator<DatabaseNode> CREATOR = new Creator<DatabaseNode>() {
          @Override
          public DatabaseNode createFromParcel(Parcel in) {
               return new DatabaseNode(in);
          }

          @Override
          public DatabaseNode[] newArray(int size) {
               return new DatabaseNode[size];
          }
     };

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

     public String getTimeOrderPlaced() {
          return TimeOrderPlaced;
     }

     public String getTimeOrderDeliveredOrCancelled() {
          return TimeOrderDeliveredOrCancelled;
     }

     @Override
     public int describeContents() {
          return 0;
     }

     @Override
     public void writeToParcel(Parcel dest, int flags) {
          dest.writeInt(id);
          dest.writeString(Name);
          dest.writeString(PhoneNumber1);
          dest.writeString(PhoneNumber2);
          dest.writeString(OrderDetails);
          dest.writeString(GrandTotal);
          dest.writeString(TimeOrderPlaced);
          dest.writeString(TimeOrderDeliveredOrCancelled);
          dest.writeString(OrderStatus);
          dest.writeString(PaymentMethod);
          dest.writeString(Address);
     }
}
