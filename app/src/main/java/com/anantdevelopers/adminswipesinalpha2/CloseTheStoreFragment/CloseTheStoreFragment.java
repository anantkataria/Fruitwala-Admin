
package com.anantdevelopers.adminswipesinalpha2.CloseTheStoreFragment;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.anantdevelopers.adminswipesinalpha2.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import static android.view.View.GONE;

public class CloseTheStoreFragment extends Fragment {

     private Button openCloseButton, noticeButton;
     private TextView openCloseTextView;
     private ProgressBar progressBar;
     private ValueEventListener listener;

     DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference();

     String openAgainTimeString = "nothing";

     private interface afterFetch{
          void afterFetching(boolean isOpen);
     }

     public CloseTheStoreFragment() {
          // Required empty public constructor
     }

     @Override
     public void onCreate(Bundle savedInstanceState) {
          super.onCreate(savedInstanceState);
     }

     @Override
     public View onCreateView(LayoutInflater inflater, ViewGroup container,
                              Bundle savedInstanceState) {
          // Inflate the layout for this fragment
          final View view = inflater.inflate(R.layout.fragment_close_the_store, container, false);

          openCloseButton = view.findViewById(R.id.open_close_button);
          openCloseTextView = view.findViewById(R.id.open_close_textView);
          noticeButton = view.findViewById(R.id.notice_button);
          progressBar = view.findViewById(R.id.progress_bar);

          getData(new afterFetch() {
               @Override
               public void afterFetching(boolean isOpen) {
                    if(!isOpen){
                         openCloseTextView.setText("Closed");
                         openCloseButton.setText("Open Stores");
                         openCloseButton.setBackgroundColor(getResources().getColor(R.color.holoGreenLight));
                         noticeButton.setVisibility(View.VISIBLE);
                    }
                    else {
                         openCloseTextView.setText("Open");
                         openCloseButton.setText("Close Stores");
                         openCloseButton.setBackgroundColor(getResources().getColor(R.color.holoRedLight));
                         noticeButton.setVisibility(GONE);
                    }
                    changeVisibilitiesToNormal();
               }
          });

          openCloseButton.setOnClickListener(new View.OnClickListener() {
               @Override
               public void onClick(View v) {
                    if(openCloseTextView.getText().equals(getString(R.string.open_string))){
                         //store is open
                         //store should be closed
                         AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
                         builder.setMessage("Are you sure you want to close the store?");
                         builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                              @Override
                              public void onClick(DialogInterface dialog, int which) {
                                   dialog.dismiss();
                                   Navigation.findNavController(view).navigate(R.id.action_closed_today_dest_to_closingDescriptionFragment);
                              }
                         });
                         builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
                              @Override
                              public void onClick(DialogInterface dialog, int which) {
                                   dialog.dismiss();
                              }
                         });

                         AlertDialog dialog = builder.create();
                         dialog.show();

                    }
                    else {
                         AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
                         builder.setMessage("Are you sure you want to open the store?");
                         builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                              @Override
                              public void onClick(DialogInterface dialog, int which) {
                                   DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference();

                                   databaseReference.child("OpenOrClose").child("isOpen").setValue(true).addOnCompleteListener(new OnCompleteListener<Void>() {
                                        @Override
                                        public void onComplete(@NonNull Task<Void> task) {
                                             if(task.isSuccessful()){
                                                  Toast.makeText(getContext(), "Store Opened Successfully!", Toast.LENGTH_SHORT).show();
                                             }else {
                                                  Toast.makeText(getContext(), "Something Went Wrong!", Toast.LENGTH_LONG).show();
                                             }
                                        }
                                   });
                              }
                         });
                         builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
                              @Override
                              public void onClick(DialogInterface dialog, int which) {
                                   dialog.dismiss();
                              }
                         });

                         AlertDialog dialog = builder.create();
                         dialog.show();
                    }
               }
          });

          noticeButton.setOnClickListener(new View.OnClickListener() {
               @Override
               public void onClick(View v) {
                    Bundle b = new Bundle();
                    b.putString("openAgainTimeString", openAgainTimeString);
                    Navigation.findNavController(view).navigate(R.id.action_closed_today_dest_to_closingDescriptionFragment, b);
               }
          });

          return view;
     }

     private void getData(final afterFetch afterFetch) {
          listener = new ValueEventListener() {

               @Override
               public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                    changeVisibilitiesToFetchingMode();

                    boolean isOpen = dataSnapshot.child("isOpen").getValue(Boolean.class);
                    openAgainTimeString = dataSnapshot.child("openingAgainTime").getValue(String.class);

                    afterFetch.afterFetching(isOpen);
               }

               @Override
               public void onCancelled(@NonNull DatabaseError databaseError) {

               }
          };

          databaseReference.child("OpenOrClose").addValueEventListener(listener);
     }

     private void changeVisibilitiesToFetchingMode() {
          openCloseTextView.setVisibility(GONE);
          progressBar.setVisibility(View.VISIBLE);
          noticeButton.setVisibility(GONE);
          openCloseButton.setVisibility(GONE);
     }

     private void changeVisibilitiesToNormal() {
          openCloseTextView.setVisibility(View.VISIBLE);
          progressBar.setVisibility(GONE);
          openCloseButton.setVisibility(View.VISIBLE);
     }

     @Override
     public void onDestroyView() {
          super.onDestroyView();
          databaseReference.child("OpenOrClose").removeEventListener(listener);
     }
}
