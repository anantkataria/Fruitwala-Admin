package com.anantdevelopers.adminswipesinalpha2.CloseTheStoreFragment;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import android.provider.ContactsContract;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.anantdevelopers.adminswipesinalpha2.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;
import java.util.Map;

/**
 * A simple {@link Fragment} subclass.
 */
public class ClosingDescriptionFragment extends Fragment {

     private EditText openAgainTimeEditText;
     private Button uploadButton;
     private ProgressBar progressBar;
     private TextView hardCodedTextView;

     private String openAgainTimeString = "none";

     public ClosingDescriptionFragment() {
          // Required empty public constructor
     }

     @Override
     public void onCreate(@Nullable Bundle savedInstanceState) {
          super.onCreate(savedInstanceState);

          Bundle b = getArguments();
          if(b != null){
               openAgainTimeString = b.getString("openAgainTimeString");
          }
     }

     @Override
     public View onCreateView(LayoutInflater inflater, ViewGroup container,
                              Bundle savedInstanceState) {
          // Inflate the layout for this fragment
          final View view =  inflater.inflate(R.layout.fragment_closing_description, container, false);

          openAgainTimeEditText = view.findViewById(R.id.open_again_time_edit_text);
          uploadButton = view.findViewById(R.id.upload_button);
          progressBar = view.findViewById(R.id.progressBar);
          hardCodedTextView = view.findViewById(R.id.hard_coded_text_view);

          if(!openAgainTimeString.equals("none")){
               openAgainTimeEditText.setText(openAgainTimeString);
          }

          uploadButton.setOnClickListener(new View.OnClickListener() {
               @Override
               public void onClick(View v) {
                    String openAgainTimeString = openAgainTimeEditText.getText().toString();

                    if(openAgainTimeString.isEmpty()){
                         Toast.makeText(getContext(), "Enter Opening Again time...", Toast.LENGTH_SHORT).show();
                    }
                    else {
                         //make database node isOpen and set it false
                         final Map<String, Object> map = new HashMap<>();
                         map.put("isOpen", false);
                         map.put("openingAgainTime", openAgainTimeString);

                         AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
                         builder.setMessage("Are you sure you want to close the store?");
                         builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                              @Override
                              public void onClick(DialogInterface dialog, int which) {
                                   changeVisibilitiesToSendingMode(view);

                                   DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference();
                                   databaseReference.child("OpenOrClose").updateChildren(map).addOnCompleteListener(new OnCompleteListener<Void>() {
                                        @Override
                                        public void onComplete(@NonNull Task<Void> task) {
                                             if(task.isSuccessful()){
                                                  Toast.makeText(getContext(), "Closed successfully!", Toast.LENGTH_SHORT).show();
                                                  Navigation.findNavController(view).popBackStack();
                                             }
                                             else{
                                                  Toast.makeText(getContext(), "Something went wrong!", Toast.LENGTH_SHORT).show();
                                                  changeVisibilitiesToNormal(view);
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

          return view;
     }

     private void changeVisibilitiesToNormal(View view) {
          progressBar.setVisibility(View.GONE);
          hardCodedTextView.setVisibility(View.VISIBLE);
          openAgainTimeEditText.setVisibility(View.VISIBLE);
          uploadButton.setVisibility(View.VISIBLE);
     }

     private void changeVisibilitiesToSendingMode(View view) {
          progressBar.setVisibility(View.VISIBLE);
          hardCodedTextView.setVisibility(View.GONE);
          openAgainTimeEditText.setVisibility(View.GONE);
          uploadButton.setVisibility(View.GONE);
     }
}
