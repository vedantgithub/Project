

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.net.ConnectivityManagerCompat;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.Network;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.InterstitialAd;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executor;
import java.util.concurrent.ScheduledExecutorService;

  public class MainActivity extends AppCompatActivity {


    ListView listView;
    DatabaseReference databaseReference;
    List<String> title_list,news_list,new_list;
    ArrayAdapter<String> adapter;

    mynews mynews;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);





        //internet  is onn/off
        checkConnection();


        listView=findViewById(R.id.listview);
        databaseReference= FirebaseDatabase.getInstance().getReference("News");

        mynews= new mynews();
        title_list=new ArrayList<>();
        news_list=new ArrayList<>();
        adapter=new ArrayAdapter<>(this,R.layout.item,R.id.item_txt,title_list);

        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                title_list.clear();
                news_list.clear();

                for(DataSnapshot ds : dataSnapshot.getChildren()){
                    mynews =ds.getValue(mynews.class);
                    if (mynews != null) {
                        title_list.add(mynews.getTitle());
                    }
                    if (mynews != null) {
                        news_list.add(mynews.getNews());
                    }

                }
                listView.setAdapter(adapter);
                listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        Intent intent = new Intent(MainActivity.this,Next.class);
                        String p=news_list.get(position);
                        intent.putExtra("key",p);
                        startActivity(intent);
                    }
                });
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });


    }

    // check the internet connection
    public void  checkConnection(){
        ConnectivityManager manager = (ConnectivityManager) getApplicationContext().getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetwork= manager.getActiveNetworkInfo();
        if (null!=activeNetwork)
        {
            if (activeNetwork.getType() == ConnectivityManager.TYPE_WIFI){
                Toast.makeText(this, "WIFI Enabled", Toast.LENGTH_SHORT).show();
            }
                else if (activeNetwork.getType() == ConnectivityManager.TYPE_MOBILE ){
                Toast.makeText(this, "Data Network Enabled", Toast.LENGTH_SHORT).show();
            }
        }

        else{
            Toast.makeText(this, "No Internet Connection", Toast.LENGTH_LONG).show();
        }

    }





}
