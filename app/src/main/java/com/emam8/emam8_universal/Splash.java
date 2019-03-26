package com.emam8.emam8_universal;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.TextView;

import com.emam8.emam8_universal.Auth.login_activity;
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

public class Splash extends AppCompatActivity {
    TextView imageView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
//        imageView=(TextView)findViewById(R.id.splash_img);
//        imageView.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent intent=new Intent(Splash.this,splash_book.class);
//                startActivity(intent);
//                finish();
//            }
//        });

            login_check();
    }


    void login_check(){
        AppPreferenceTools appPreferenceTools = new AppPreferenceTools(getBaseContext());
        String access_token="Bearer "+appPreferenceTools.getAccessToken();
//        Log.d("Emam8",access_token);
        Gson gson = new GsonBuilder()
                .setLenient()
                .create();

        Retrofit retrofit=new Retrofit.Builder().
                baseUrl(BuildConfig.Apikey_BaseUrl_Api)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();
        HashMap<String,String> headerMap=new HashMap<String,String>();
        headerMap.put("content-type","application/json");
        headerMap.put("Authorization",access_token);
        RetroService retroService=retrofit.create(RetroService.class);

        Call<User_info> call=retroService.user_info(headerMap);

        call.enqueue(new Callback<User_info>() {
            @Override
            public void onResponse(Call<User_info> call, Response<User_info> response) {
                if(response.isSuccessful()){
                    Intent intent=new Intent(Splash.this,MainActivity.class);
                    startActivity(intent);
                    finish();
                }else{
                    Log.d("emam8","پاسخ درستی از سرور دریافت نشد");
                    Intent intent=new Intent(Splash.this,login_activity.class);
                    startActivity(intent);
                    finish();


                }
            }

            @Override
            public void onFailure(Call<User_info> call, Throwable t) {
                Intent intent=new Intent(Splash.this,login_activity.class);
                startActivity(intent);
                finish();

            }
        });
    }
}