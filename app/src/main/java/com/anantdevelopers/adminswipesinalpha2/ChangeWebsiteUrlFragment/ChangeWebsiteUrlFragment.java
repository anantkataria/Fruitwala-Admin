package com.anantdevelopers.adminswipesinalpha2.ChangeWebsiteUrlFragment;

import android.graphics.Bitmap;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebResourceRequest;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.anantdevelopers.adminswipesinalpha2.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;


public class ChangeWebsiteUrlFragment extends Fragment {

     private TextView urlTextView;
     private ProgressBar progressBar1, progressBarMain;
     private WebView urlWebView;
     private Button changeUrlButton;

     private RelativeLayout mainLayout;

     private ValueEventListener listener;

     private DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference();

     public ChangeWebsiteUrlFragment() {
          // Required empty public constructor
     }

     private interface afterGettingFromDatabase{
          void afterFetch(String url);
     }

     @Override
     public View onCreateView(LayoutInflater inflater, ViewGroup container,
                              Bundle savedInstanceState) {
          // Inflate the layout for this fragment
          final View view = inflater.inflate(R.layout.fragment_change_website_url, container, false);

          urlTextView = view.findViewById(R.id.url_text_view);
          progressBar1 = view.findViewById(R.id.progress_bar1);
          progressBarMain = view.findViewById(R.id.progress_bar_main);
          urlWebView = view.findViewById(R.id.url_web_view);
          changeUrlButton = view.findViewById(R.id.change_url_button);

          mainLayout = view.findViewById(R.id.main_layout);

          getUrlFromDatabase(new afterGettingFromDatabase() {
               @Override
               public void afterFetch(String url) {
                    urlTextView.setText(url);
                    progressBarMain.setVisibility(View.GONE);
                    mainLayout.setVisibility(View.VISIBLE);
                    loadUrlToWebView(url);
               }
          });

          changeUrlButton.setOnClickListener(new View.OnClickListener() {
               @Override
               public void onClick(View v) {
                    String url = urlTextView.getText().toString();
                    Bundle b = new Bundle();
                    b.putString("url", url);
                    Navigation.findNavController(view).navigate(R.id.action_change_website_url_dest_to_changeWebsiteUrlFragment2, b);
               }
          });

          return view;
     }

     private void getUrlFromDatabase(final afterGettingFromDatabase Interface) {

          listener = new ValueEventListener() {
               @Override
               public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    String url = dataSnapshot.getValue(String.class);

                    Interface.afterFetch(url);
               }

               @Override
               public void onCancelled(@NonNull DatabaseError databaseError) {

               }
          };

          databaseReference.child("URLs").child("FruitsAreHealthy").addListenerForSingleValueEvent(listener);
     }

     private void loadUrlToWebView(String url) {
          progressBar1.setVisibility(View.VISIBLE);
          WebSettings webSettings = urlWebView.getSettings();
          webSettings.setJavaScriptEnabled(true);
          urlWebView.setWebViewClient(new WebViewClient());
          urlWebView.loadUrl("https://www.betterhealth.vic.gov.au/health/HealthyLiving/fruit-and-vegetables");

          urlWebView.setOnKeyListener(new View.OnKeyListener() {
               @Override
               public boolean onKey(View v, int keyCode, KeyEvent event) {
                    if( keyCode == KeyEvent.KEYCODE_BACK )
                    {
                         if (urlWebView.canGoBack()) {
                              urlWebView.goBack();
                              return true;
                         } else {
                              return false;
                         }

                    }
                    return false;
               }
          });


     }

     public class WebViewClient extends android.webkit.WebViewClient {
          @Override
          public void onPageStarted(WebView view, String url, Bitmap favicon) {
               super.onPageStarted(view, url, favicon);
          }

          @Override
          public boolean shouldOverrideUrlLoading(WebView view, WebResourceRequest request) {
               if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                    view.loadUrl(request.getUrl().toString());
               }
               return super.shouldOverrideUrlLoading(view, request);
          }

          @Override
          public void onPageFinished(WebView view, String url) {
               super.onPageFinished(view, url);
               progressBar1.setVisibility(View.GONE);
          }
     }



     @Override
     public void onDestroy() {
          super.onDestroy();
          databaseReference.child("URLs").child("FruitsAreHealthy").removeEventListener(listener);
     }
}
