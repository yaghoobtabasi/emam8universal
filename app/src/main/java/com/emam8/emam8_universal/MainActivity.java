package com.emam8.emam8_universal;


import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.Toolbar;

import com.emam8.emam8_universal.Auth.login_activity;
import com.emam8.emam8_universal.services.ConnectionDetector;
import com.emam8.emam8_universal.utilities.AppPreferenceTools;

import co.ronash.pushe.Pushe;
import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

public class MainActivity extends AppCompatActivity {

    public static final String app_name = "universal";
    public static final String app_version = "1.5";
    public static final String TAG = "emam8_universal";
    private final static Integer PERMISSIONS_REQUEST_READ_CONTACTS = 11;
    private final static Integer PERMISSIONS_REQUEST_READ_STORAGE = 12;
    private final static Integer PERMISSIONS_REQUEST_WRITE_STORAGE = 13;
    private final static Integer PERMISSIONS_REQUEST_READ_STATE = 14;
    private final static Integer PERMISSIONS_REQUEST_READ_SMS = 15;
    public String Device_ID;


    TextView sher_sabk, news, contact_us, shop_txt, amoozesh_maddahi, download_maddahi, quran, lectures;

    ImageView frame_first;
    ConnectionDetector connectionDetector;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        Pushe.initialize(this, false);
        findview();
        AppPreferenceTools appPreferenceTools = new AppPreferenceTools(getBaseContext());
        String id = appPreferenceTools.getUserId();
        Database db=new Database(this);

        db.useable();
        db.open();

        String count_fav=db.fav_count();
        db.close();
        Toast.makeText(this,count_fav,Toast.LENGTH_LONG).show();


        sher_sabk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent i;
//                i=new Intent(getApplicationContext(),choose_mode.class);
                i = new Intent(getApplicationContext(), SherActivity.class);
                startActivity(i);
            }
        });

        quran.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent i;
//                i=new Intent(getApplicationContext(),choose_mode.class);
//                i = new Intent(getApplicationContext(), Splash.class);
//                startActivity(i);
            }
        });

        lectures.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent i;
//                i=new Intent(getApplicationContext(),choose_mode.class);
//                i = new Intent(getApplicationContext(), Splash.class);
//                startActivity(i);
            }
        });

        amoozesh_maddahi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i;
//                i=new Intent(getApplicationContext(),choose_mode_torki.class);
//                i = new Intent(getApplicationContext(), Splash.class);
//                startActivity(i);
            }
        });
        news.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

//                Intent i=new Intent(getApplicationContext(),FavContent.class);
//                Intent i = new Intent(getApplicationContext(), Splash.class);
//                startActivity(i);
            }
        });


        contact_us.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                Intent i =new Intent(getApplicationContext(),choose_about_contact.class);
                Intent i = new Intent(MainActivity.this, Contact_us.class);
                startActivity(i);
            }
        });


        download_maddahi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                Intent intent=new Intent(getApplicationContext(),choose_amoozesh.class);
//                Intent i = new Intent(getApplicationContext(), Splash.class);
//                startActivity(i);
            }
        });


        connectionDetector = new ConnectionDetector(this);
        if (!connectionDetector.is_connected()) {
            Toast.makeText(getApplicationContext(), "اتصال اینترنت را چک کنید", Toast.LENGTH_LONG).show();
        }

        BottomNavigationView bottomNavigationView = (BottomNavigationView)
                findViewById(R.id.bottom_navigation);
        bottomNavigationView.setSelectedItemId(R.id.action_home);

        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.action_favorites:
                        Intent i = new Intent(getApplicationContext(), RecycleFavPoem.class);
                        startActivity(i);
                        break;
                    case R.id.action_home:
                        break;
                    case R.id.action_account:
                        Intent z = new Intent(MainActivity.this, ProfileUser.class);
                        startActivity(z);
                        break;

                }
                return true;
            }
        });

    }


    void findview() {
        news = (TextView) findViewById(R.id.news_txt);
        sher_sabk = (TextView) findViewById(R.id.sher_sabk_txt);
        amoozesh_maddahi = (TextView) findViewById(R.id.amoozesh_maddahi);
        download_maddahi = (TextView) findViewById(R.id.downliad_maddahi);
        contact_us = (TextView) findViewById(R.id.contatc_us_btn);
        quran = (TextView) findViewById(R.id.quran_txt);
        lectures = (TextView) findViewById(R.id.lectures_txt);
        Typeface tf = Typeface.createFromAsset(getAssets(), "fonts/Vazir.ttf");
        news.setTypeface(tf);
        contact_us.setTypeface(tf);
        download_maddahi.setTypeface(tf);
        amoozesh_maddahi.setTypeface(tf);
        sher_sabk.setTypeface(tf);
        quran.setTypeface(tf);
        lectures.setTypeface(tf);


    }

//    void show_toast() {
//        Toast.makeText(getApplicationContext(), "این گزینه بزودی فعال می گردد", Toast.LENGTH_SHORT).show();
//    }


    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase));
    }


}
