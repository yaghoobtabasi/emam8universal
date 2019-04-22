package com.emam8.emam8_universal;

import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

public class SherActivity extends AppCompatActivity {
    private TextView txt_farsi,txt_torki,txt_matn;
    private Intent i;
    private String poet_id,catid,mode,gid;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sher);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
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
                String title="فهرست اشعار ترکی";
                i.putExtra("sectionid", "19");
                i.putExtra("title",title);

               startActivity(i);

            }
        });

        txt_matn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                i=new Intent(getApplicationContext(),Cat_Farsi_Poems.class);
                String title="فهرست متن مقاتل";
                i.putExtra("sectionid", "30");
                i.putExtra("title",title);
                startActivity(i);
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
