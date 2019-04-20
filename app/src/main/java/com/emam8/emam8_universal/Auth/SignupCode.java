package com.emam8.emam8_universal.Auth;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.emam8.emam8_universal.R;

public class SignupCode extends AppCompatActivity {

    EditText code;
    Button sendCode , cancelCode;
    String txt_code;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_singup_code);

        code = findViewById(R.id.signup_code);
        sendCode = findViewById(R.id.send_signup_code);
        cancelCode = findViewById(R.id.cancel_signup_code);


        sendCode.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                txt_code = code.getText().toString().trim();

            }
        });

        cancelCode.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(SignupCode.this, Signup.class);
                startActivity(i);
            }
        });
    }
}
