package com.anantdevelopers.adminswipesinalpha2.ChangeWebsiteUrlFragment;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.anantdevelopers.adminswipesinalpha2.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;


public class ChangeWebsiteUrlFragment2 extends Fragment {

     private Button updateUrlButton;
     private ProgressBar progressBar;
     private EditText newUrlEditText;

     private String url;

     public ChangeWebsiteUrlFragment2() {
          // Required empty public constructor
     }

     @Override
     public void onCreate(@Nullable Bundle savedInstanceState) {
          super.onCreate(savedInstanceState);

          Bundle b = getArguments();
          url = b.getString("url", "null");
     }

     @Override
     public View onCreateView(LayoutInflater inflater, ViewGroup container,
                              Bundle savedInstanceState) {
          // Inflate the layout for this fragment
          final View view = inflater.inflate(R.layout.fragment_change_website_url2, container, false);

          updateUrlButton = view.findViewById(R.id.update_url_button);
          progressBar = view.findViewById(R.id.progress_bar);
          newUrlEditText = view.findViewById(R.id.new_url_edit_text);

          newUrlEditText.setText(url);

          updateUrlButton.setOnClickListener(new View.OnClickListener() {
               @Override
               public void onClick(View v) {
                    String newUrl = newUrlEditText.getText().toString();
                    
                    if(newUrl.isEmpty()){
                         Toast.makeText(getContext(), "Add some URL text...", Toast.LENGTH_SHORT).show();
                    }
                    //upload new URL to the database
                    else {
                         uploadNewUrlToDatabase(newUrl, view);
                    }
               }
          });

          return view;
     }

     private void uploadNewUrlToDatabase(String newUrl, final View view) {
          DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference();

          changeVisibilitiesToSendingMode();
          
          databaseReference.child("URLs").child("FruitsAreHealthy").setValue(newUrl).addOnCompleteListener(new OnCompleteListener<Void>() {
               @Override
               public void onComplete(@NonNull Task<Void> task) {
                    if(task.isSuccessful()) {
                         Toast.makeText(getContext(), "Updation successful", Toast.LENGTH_SHORT).show();
                         Navigation.findNavController(view).popBackStack();
                    }
                    else {
                         Toast.makeText(getContext(), "Something went wrong!", Toast.LENGTH_SHORT).show();
                         changeVisibilitiesToNormalMode();
                    }
               }
          });
     }

     private void changeVisibilitiesToSendingMode() {
          progressBar.setVisibility(View.VISIBLE);
          newUrlEditText.setVisibility(View.INVISIBLE);
          updateUrlButton.setVisibility(View.INVISIBLE);
     }

     private void changeVisibilitiesToNormalMode() {
          progressBar.setVisibility(View.GONE);
          newUrlEditText.setVisibility(View.VISIBLE);
          updateUrlButton.setVisibility(View.VISIBLE);
     }
}
