package com.emam8.emam8_universal;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;

import com.emam8.emam8_universal.Auth.login_activity;

public class Farsi_poems extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_farsi_poems);


        BottomNavigationView bottomNavigationView = (BottomNavigationView)
                findViewById(R.id.bottom_navigation_sher);


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
}
