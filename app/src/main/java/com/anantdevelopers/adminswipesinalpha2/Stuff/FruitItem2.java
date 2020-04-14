package com.anantdevelopers.adminswipesinalpha2.Stuff;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;

public class FruitItem2 implements Parcelable {
     private String fruitName;
     private ArrayList<String> fruitQtys;
     private ArrayList<String> fruitPrices;

     public FruitItem2(String fruitName, ArrayList<String> fruitQtys, ArrayList<String> fruitPrices) {
          this.fruitName = fruitName;
          this.fruitQtys = fruitQtys;
          this.fruitPrices = fruitPrices;
     }

     protected FruitItem2(Parcel in) {
          fruitName = in.readString();
          fruitQtys = in.createStringArrayList();
          fruitPrices = in.createStringArrayList();
     }

     public static final Creator<FruitItem2> CREATOR = new Creator<FruitItem2>() {
          @Override
          public FruitItem2 createFromParcel(Parcel in) {
               return new FruitItem2(in);
          }

          @Override
          public FruitItem2[] newArray(int size) {
               return new FruitItem2[size];
          }
     };

     public String getFruitName() {
          return fruitName;
     }

     public void setFruitName(String fruitName) {
          this.fruitName = fruitName;
     }

     public ArrayList<String> getFruitQtys() {
          return fruitQtys;
     }

     public void setFruitQtys(ArrayList<String> fruitQtys) {
          this.fruitQtys = fruitQtys;
     }

     public ArrayList<String> getFruitPrices() {
          return fruitPrices;
     }

     public void setFruitPrices(ArrayList<String> fruitPrices) {
          this.fruitPrices = fruitPrices;
     }

     @Override
     public int describeContents() {
          return 0;
     }

     @Override
     public void writeToParcel(Parcel dest, int flags) {
          dest.writeString(fruitName);
          dest.writeStringList(fruitQtys);
          dest.writeStringList(fruitPrices);
     }
}
