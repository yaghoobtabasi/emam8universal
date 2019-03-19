package com.emam8.emam8_universal;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.emam8.emam8_universal.Auth.login_activity;

import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

public class SherActivity extends AppCompatActivity {
    private TextView txt_farsi,txt_torki,txt_matn;
    private Intent i;
    private String poet_id,catid,mode,gid;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sher);
        findView();

        txt_farsi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                i=new Intent(getApplicationContext(), Sec_Farsi_poems.class);
                startActivity(i);
            }
        });

        txt_torki.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                i=new Intent(getApplicationContext(),Cat_Farsi_Poems.class);
                i.putExtra("sectionid", "19");
               startActivity(i);

            }
        });

        txt_matn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                i=new Intent(getApplicationContext(),Cat_Farsi_Poems.class);
                i.putExtra("sectionid", "30");
                startActivity(i);
            }
        });




        BottomNavigationView bottomNavigationView = (BottomNavigationView)
                findViewById(R.id.bottom_navigation_sher);
        bottomNavigationView.setSelectedItemId(R.id.action_home);

        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()){
                    case R.id.action_favorites:

                        Intent i=new Intent(getApplicationContext(),login_activity.class);
                        startActivity(i);

                        break;
                    case R.id.action_home:
                        Intent y=new Intent(getApplicationContext(),MainActivity.class);
                        startActivity(y);
                        break;
                    case R.id.action_schedules:

                }
                return true;
            }
        });




    }

    void findView(){
        txt_farsi=(TextView)findViewById(R.id.sher_sabk_farsi);
        txt_torki=(TextView)findViewById(R.id.sher_torki);
        txt_matn=(TextView)findViewById(R.id.matn_maghtal);
    }

    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase));
    }

}
