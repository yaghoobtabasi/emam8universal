package com.emam8.emam8_universal.Auth;

import android.content.Intent;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.emam8.emam8_universal.MainActivity;
import com.emam8.emam8_universal.Model.AuthenticationResponseModel;
import com.emam8.emam8_universal.Model.SignUpRequestModel;
import com.emam8.emam8_universal.R;
import com.emam8.emam8_universal.services.RetroService;
import com.emam8.emam8_universal.utilities.AppPreferenceTools;

import java.util.HashMap;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class signup extends AppCompatActivity {
    private EditText username,pass,conf_pass;
    String txt_pass,txt_conf_pass,txt_username;
    private TextInputLayout inputLayoutUserName,inputLayoutPass,inputLayoutConfPass;
    Button btn_signup;
    Boolean check_pass;
    Snackbar snackbar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);
        findView();


        btn_signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                check_pass=check_conf_pass();
                if (check_pass){
                    signup_user();
                }
            }
        });




    }

    void signup_user(){
        txt_conf_pass=conf_pass.getText().toString().trim();
        txt_username=username.getText().toString().trim();
        String Base_url="https://emam8.com/api/auth/";
        Retrofit retrofit=new Retrofit.
                Builder().
                baseUrl(Base_url).
                addConverterFactory(GsonConverterFactory.create()).
                build();

        HashMap<String,String> headerMap=new HashMap<String,String>();
        headerMap.put("content-type","application/json");

        RetroService retrosignup=retrofit.create(RetroService.class);
        Call<AuthenticationResponseModel> call=retrosignup.signUp(headerMap,txt_username,txt_conf_pass);
        call.enqueue(new Callback<AuthenticationResponseModel>() {
            @Override
            public void onResponse(Call<AuthenticationResponseModel> call, Response<AuthenticationResponseModel> response) {
                Log.d("Emam8",response.body().toString());
                AppPreferenceTools appPreferenceTools = new AppPreferenceTools(getBaseContext());
                appPreferenceTools.saveUserAuthenticationInfo(response.body());
                if(response.body().getSuccess()==true){
                    Intent i=new Intent(getApplicationContext(), MainActivity.class);
                    startActivity(i);
                    finish();
                }

            }

            @Override
            public void onFailure(Call<AuthenticationResponseModel> call, Throwable t) {
                    Log.w("Emam8","Error occured "+t.getMessage());
            }
        });



        }




    boolean check_conf_pass(){
        txt_pass=pass.getText().toString().trim();
        txt_conf_pass=conf_pass.getText().toString().trim();
        txt_username=username.getText().toString().trim();
        if(txt_conf_pass.compareTo(txt_pass)!=0){
            Snackbar snackbar=Snackbar.make(findViewById(R.id.rltv_act),this.getString(R.string.msg_err_signup_pass_confirm),Snackbar.LENGTH_LONG);
            snackbar.show();
            return false;
        }else if
                (txt_pass.length()<6 || txt_conf_pass.length()<6 ){
            Log.d("emam8","less than 6"+txt_pass+"**"+txt_conf_pass);
//            Toast.makeText(this,this.getString(R.string.msg_err_signup_lenth),Toast.LENGTH_LONG).show();
            Snackbar snackbar=Snackbar.make(findViewById(R.id.rltv_act),this.getString(R.string.msg_err_signup_lenth),Snackbar.LENGTH_LONG);
            snackbar.show();
            return false;
        }

        else if(txt_username.length()<10){
            Snackbar snackbar=Snackbar.make(findViewById(R.id.rltv_act),this.getString(R.string.msg_err_signup_username_length),Snackbar.LENGTH_LONG);
            snackbar.show();
            return  false;
        }
        return  true;
    }

    void  findView(){
        inputLayoutUserName = (TextInputLayout)findViewById(R.id.input_layout_signup_email);
        inputLayoutPass = (TextInputLayout)findViewById(R.id.input_layout_signup_password);
        inputLayoutConfPass = (TextInputLayout)findViewById(R.id.input_layout_signup_password_confirm);
        username=(EditText)findViewById(R.id.signup_email);
        pass=(EditText)findViewById(R.id.signup_password);
        conf_pass=(EditText)findViewById(R.id.signup_password_confirm);
        btn_signup=(Button)findViewById(R.id.btnsignup);
    }
}
