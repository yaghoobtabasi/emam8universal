package com.emam8.emam8_universal;


import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.emam8.emam8_universal.Model.AuthenticationResponseModel;
import com.emam8.emam8_universal.Model.User_info;
import com.emam8.emam8_universal.services.RetroService;
import com.emam8.emam8_universal.utilities.AppPreferenceTools;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.HashMap;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class splash_book extends AppCompatActivity {
    TextView imageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_book);
        imageView=(TextView)findViewById(R.id.txt_splash_book);
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(splash_book.this,MainActivity.class);
                startActivity(intent);
                finish();
            }
        });

        Thread myThread = new Thread(){
            @Override
            public void run() {
                try {
                    sleep(3000);
                    Intent intent=new Intent(splash_book.this,MainActivity.class);
                    startActivity(intent);
                    finish();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        };

//        myThread.start();



    };

}
