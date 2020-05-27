package com.example.topnews;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.speech.tts.TextToSpeech;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.ads.initialization.InitializationStatus;
import com.google.android.gms.ads.initialization.OnInitializationCompleteListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.Locale;

public class Next extends AppCompatActivity {
    private AdView mAdView;

    TextView txt;
    Button button;
    Button share_btn;
    Button nbtn,pbtn;

    Button stopbtn;

    TextToSpeech textToSpeech;

    //int position;        //remove it

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_next);

        //add mob

        //MobileAds.initialize(this,s:" ca-app-pub-3940256099942544~3347511713");
       MobileAds.initialize(this,"ca-app-pub-3940256099942544~3347511713" );
        mAdView = findViewById(R.id.adView);
        AdRequest adRequest = new AdRequest.Builder().build();
        mAdView.loadAd(adRequest);


        MobileAds.initialize(this,"ca-app-pub-3940256099942544~3347511713" );
        mAdView = findViewById(R.id.adViewe);
        AdRequest adRequests = new AdRequest.Builder().build();
        mAdView.loadAd(adRequest);





        txt=findViewById(R.id.next_txt);
        button=findViewById(R.id.copy_bton);
        share_btn=findViewById(R.id.share_bton);
        nbtn=findViewById(R.id.next_bton);
        pbtn=findViewById(R.id.prev_bton);

        stopbtn=findViewById(R.id.stop_bton);


        final String t=getIntent().getStringExtra("key");
        //final String[] t=getIntent().getStringArrayExtra("key");          //remove it
        //position=getIntent().getIntExtra("position",0);   //remove it


        txt.setText(t);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ClipboardManager clipboard = (ClipboardManager) getSystemService(Context.CLIPBOARD_SERVICE);
                ClipData clip = ClipData.newPlainText("Text",t);
                clipboard.setPrimaryClip(clip);

                Toast.makeText(Next.this,"COPIED THE CONTENT",Toast.LENGTH_LONG).show();
            }
        });

        share_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent();
                intent.setAction(Intent.ACTION_SEND);
                intent.putExtra(Intent.EXTRA_TEXT,t);
                intent.setType("text/plain");
                intent=Intent.createChooser(intent,"Share By");
                startActivity(intent);
            }
        });

    

      

        textToSpeech =new TextToSpeech(getApplicationContext(), new TextToSpeech.OnInitListener() {
            @Override
            public void onInit(int status) {
                if (status == textToSpeech.SUCCESS)
                {
                    int lang = textToSpeech.setLanguage(Locale.ENGLISH);

                }
            }
        });

        pbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String s=txt.getText().toString();

                int speech = textToSpeech.speak(s,textToSpeech.QUEUE_FLUSH,null);

            }
        });



        nbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openActivity3();

            }
        });



        //STOP BUTTON CLICK ACTION PERFORM
        stopbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (textToSpeech.isSpeaking()){
                    //if it speaking stop
                    textToSpeech.stop();
                   // textToSpeech.shutdown();
                    Toast.makeText(Next.this,"SPEECHED STOPED",Toast.LENGTH_LONG).show();

                }
                else{
                    //not speaking
                    //Toast.makeText(.this, "", Toast.LENGTH_SHORT).show();
                    //Toast.makeText(Next.this,"SPEECH STOPED",Toast.LENGTH_LONG).show();
                }
            }
        });




    }
    public void openActivity3(){
        Intent intent=new Intent(this,Activity3.class);
        startActivity(intent);
    }


    @Override
    protected void onPause() {
        if (textToSpeech != null || textToSpeech.isSpeaking()){
            //if it speaking stop
            textToSpeech.stop();
           // textToSpeech.shutdown();
            //Toast.makeText(Next.this,"SPEECHED STOPED",Toast.LENGTH_LONG).show();

        }
        super.onPause();
    }
}
