package com.example.topnews;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.textservice.TextInfo;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.firebase.client.Firebase;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.InterstitialAd;
import com.google.android.gms.ads.MobileAds;
import com.google.firebase.FirebaseApp;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.concurrent.Executor;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class Activity3 extends AppCompatActivity {
    private AdView mAdView;
   // private Firebase Ref;    // have to comment
    private EditText username,feedback;
    FirebaseDatabase rootnode;
    DatabaseReference ref;

   // News news;

    Button insert;   // added
    private InterstitialAd mInterstitialAd;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_3);

        MobileAds.initialize(this,"ca-app-pub-3940256099942544~3347511713");
        mAdView = findViewById(R.id.adView);
        AdRequest adRequest = new AdRequest.Builder().build();
        mAdView.loadAd(adRequest);


        MobileAds.initialize(this,"ca-app-pub-3940256099942544~3347511713");
        mAdView = findViewById(R.id.adViews);
        AdRequest adRequests = new AdRequest.Builder().build();
        mAdView.loadAd(adRequest);


        prepareAD();
        ScheduledExecutorService scheduledExecutorService =
                Executors.newSingleThreadScheduledExecutor();
            scheduledExecutorService.scheduleAtFixedRate(new Runnable() {
                @Override
                public void run() {
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            if (mInterstitialAd.isLoaded()) {
                                mInterstitialAd.show();
                            }
                            else {
                                Log.d("TAG","Interstital not Loaded");
                            }
                            prepareAD();
                        }
                    });
                }
            },55,55, TimeUnit.SECONDS);



            // feedback coding
        username=findViewById(R.id.Username);
        feedback=findViewById(R.id.feedback);
        insert=findViewById(R.id.response);

        insert.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                rootnode=FirebaseDatabase.getInstance();
                ref=rootnode.getReference("Feedback");

                String name= username.getText().toString();
                String comment = feedback.getText().toString();



                feedbackActivity3 helperclass = new feedbackActivity3(name,comment);

                //ref.setValue("first data");
                ref.child(comment).setValue(helperclass);
                Toast.makeText(Activity3.this, "send", Toast.LENGTH_SHORT).show();


            }
        });
        

    }
      // interstial ad
    public void prepareAD()
    {
        mInterstitialAd = new InterstitialAd(this);
        mInterstitialAd.setAdUnitId("ca-app-pub-3940256099942544/1033173712");
        mInterstitialAd.loadAd(new AdRequest.Builder().build());
    }
}



