package com.emam8.emam8_universal.Auth;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.emam8.emam8_universal.BuildConfig;
import com.emam8.emam8_universal.MainActivity;
import com.emam8.emam8_universal.Model.AuthenticationResponseModel;
import com.emam8.emam8_universal.R;
import com.emam8.emam8_universal.services.ConnectionDetector;
import java.util.HashMap;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import com.emam8.emam8_universal.services.RetroService;
import com.emam8.emam8_universal.utilities.AppPreferenceTools;
import com.emam8.emam8_universal.utilities.ClientConfigs;

public class login_activity extends AppCompatActivity {

    private String username,password;
    private EditText usr,pass;
    private TextInputLayout inputLayoutUsr,inputLayoutPass;
    private Button login_btn;
    private TextView creat_acnt_txt,cancle_signup;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        findview();

        usr.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (v != usr && usr.getText().toString().isEmpty()) {
                    inputLayoutUsr.setErrorEnabled(true);
                    inputLayoutUsr.setError("نام کاربری اشتباه وارد شده است");
                } else {
                    inputLayoutUsr.setErrorEnabled(false);
                }
            }
        });
        pass.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (v != pass && pass.getText().toString().isEmpty()) {
                    inputLayoutPass.setErrorEnabled(true);
                    inputLayoutPass.setError("رمز عبور اشتباه وارد شده است");
                } else {
                    inputLayoutPass.setErrorEnabled(false);
                }
            }
        });

        ConnectionDetector connectionDetector=new ConnectionDetector(this);
        if(!connectionDetector.is_connected())
        {
            Toast.makeText(getApplicationContext(),"اتصال اینترنت را چک کنید",Toast.LENGTH_LONG).show();

        }

        login_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                login_user();
            }
        });

        cancle_signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(getApplicationContext(),MainActivity.class);
                startActivity(intent);
                finish();
            }
        });


        creat_acnt_txt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i=new Intent(getApplicationContext(),signup.class);
                startActivity(i);

            }
        });






    }

void findview(){
        inputLayoutUsr = (TextInputLayout)findViewById(R.id.input_layout_email);
        inputLayoutPass = (TextInputLayout)findViewById(R.id.input_layout_password);
        usr=(EditText) findViewById(R.id.input_email);
        pass=(EditText)findViewById(R.id.input_password);
        login_btn=(Button) findViewById(R.id.btn_login);
        creat_acnt_txt=(TextView) findViewById(R.id.link_signup);
        cancle_signup=(TextView) findViewById(R.id.link_cancel_signup);

}


void login_user(){
      username=usr.getText().toString().trim();
      password=pass.getText().toString().trim();
    final ProgressDialog pDialog;
    pDialog=new ProgressDialog(login_activity.this);
    pDialog.setMessage("در حال فراخوانی اطلاعات ...");
    pDialog.setCancelable(true);
    pDialog.show();
    ClientConfigs clientConfigs=new ClientConfigs();
    Retrofit retro=new Retrofit.Builder()
            .baseUrl(BuildConfig.Apikey_BaseUrl)
            .addConverterFactory(GsonConverterFactory.create())
            .build();

    HashMap<String,String> headerMap=new HashMap<String,String>();
    headerMap.put("content-type","application/json");

    RetroService retroService=retro.create(RetroService.class);
    Call<AuthenticationResponseModel> call=retroService.signIn(headerMap,username,password,clientConfigs.App_name,"json");

    call.enqueue(new Callback<AuthenticationResponseModel>() {
        @Override
        public void onResponse(Call<AuthenticationResponseModel> call, Response<AuthenticationResponseModel> response) {
            if (response.isSuccessful()) {
                Log.d(MainActivity.TAG,"onResponse : server response token="+response.body().access_token);
                AppPreferenceTools appPreferenceTools = new AppPreferenceTools(getBaseContext());
                appPreferenceTools.saveUserAuthenticationInfo(response.body());
                //navigate to main activity
                startActivity(new Intent(getBaseContext(), MainActivity.class));
//                finish this activity
                finish();
            }

            pDialog.dismiss();
        }

        @Override
        public void onFailure(Call<AuthenticationResponseModel> call, Throwable t) {
            Log.d(MainActivity.TAG,"Erro message :"+t.getMessage());

            pDialog.dismiss();
        }
    });

}

}
