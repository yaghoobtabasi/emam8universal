package com.emam8.emam8_universal.custom_dialog;

import android.app.Dialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import com.emam8.emam8_universal.R;

public class Dialog_SignupCode extends DialogFragment {

    Dialog dialog;
    ImageView close;
    EditText code;
    Button sendCode,cancel;
    String txt_code;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view =inflater.inflate(R.layout.dialog_signup_code,container,false);
        close = view.findViewById(R.id.img_close);
        code = view.findViewById(R.id.signup_code);
        sendCode = view.findViewById(R.id.sendCode);
        cancel = view.findViewById(R.id.btn_cancel);

        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });



        close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });



        sendCode.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                txt_code = code.getText().toString().trim();
            }
        });

        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        return view;

    }
}
