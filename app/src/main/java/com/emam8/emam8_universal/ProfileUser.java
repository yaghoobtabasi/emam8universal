package com.emam8.emam8_universal;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.RelativeLayout;

import com.emam8.emam8_universal.utilities.AppPreferenceTools;

public class ProfileUser extends AppCompatActivity {

    private RelativeLayout rltv_user,rltv_about,rltv_out;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile_user);


        rltv_user = findViewById(R.id.rltv_prof);
        rltv_about=findViewById(R.id.rltv_about);
        rltv_out=findViewById(R.id.rltv_out);



        rltv_user.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ProfileUser.this,EditProfileUser.class);
                startActivity(intent);
            }
        });

        rltv_about.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ProfileUser.this,About_us.class);
                startActivity(intent);
            }
        });

        rltv_out.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AppPreferenceTools appPreferenceTools=new AppPreferenceTools(getApplicationContext());
                appPreferenceTools.removeAllPrefs();
            }
        });

    }
}