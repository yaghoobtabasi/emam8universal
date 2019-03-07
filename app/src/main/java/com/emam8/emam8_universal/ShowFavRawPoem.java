package com.emam8.emam8_universal;


import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.emam8.emam8_universal.App.Article_functions;

public class ShowFavRawPoem extends AppCompatActivity {
    public TextView body_raw;
    private  database db;
    public  int pos;
    private String article_id,state,body;
    ImageView heart_btn,share_btn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activisty_show_fav_raw_poem);
        Bundle bundle = getIntent().getExtras();
        pos= bundle.getInt("Pos");
        article_id=bundle.getString("article_id");
        state=bundle.getString("state");
        Article_functions inc_article=new Article_functions();
        inc_article.inc_counter(article_id);

        if(state.compareTo("8")==0){
            Toast.makeText(getApplicationContext()," "+"شما از نسخه رایگان این برنامه استفاده می کنید برای استفاده از همه امکانات این برنامه آن را خریداری کنید ",Toast.LENGTH_LONG).show();
            Intent i=new Intent(getApplicationContext(),RecyclerPoem.class);
            startActivity(i);
            finish();


        }


        db = new database(ShowFavRawPoem.this);
        db.useable();
        db.open();

        body_raw=(TextView)findViewById(R.id.fpr_txt_raw_body);
        heart_btn=(ImageView)findViewById(R.id.fpr_img_heart_btn_raw);
        share_btn=(ImageView)findViewById(R.id.fpr_img_share_btn_raw);

        if(db.check_fav_content(article_id))
        {
            heart_btn.setImageResource(R.drawable.heart_red);
        }else
        {
            heart_btn.setImageResource(R.drawable.heart_white);
        }

        heart_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                db = new database(ShowFavRawPoem.this);
                db.writable();
                db.open();
                if(!db.check_fav_content(article_id))
                {

                    long result = db.add_fav(article_id);
                    if (result > 0) {
                        Toast.makeText(getApplicationContext(),"این مطلب به اشعار منتخب افزوده شد",Toast.LENGTH_SHORT).show();
                        heart_btn.setImageResource(R.drawable.heart_red);
                    }

                }else
                {
                    long res=db.del_fav(article_id);
                    if(res>0){
                        heart_btn.setImageResource(R.drawable.heart_white);
                        Toast.makeText(getApplicationContext(),"این مطلب از فهرست اشعار حذف شد",Toast.LENGTH_SHORT).show();
                    }

                }

                db.close();

            }
        });


        String poet=db.namayesh_by_id(article_id,4,"app_contents");
        String title=db.namayesh_by_id(article_id,1,"app_contents");

        String text=db.namayesh_by_id(article_id,2 , "app_contents");
if(poet!=null){
    if(poet.length()>4)
        text=text+ System.getProperty("line.separator")+"شاعر: " +poet;
}


        text.replace("\\n", System.getProperty("line.separator"));
        final String new_body=title+ System.getProperty("line.separator")+"********"+System.getProperty("line.separator")+text;

        body_raw.setText(new_body);
        Typeface tf = Typeface.createFromAsset(getAssets(),"fonts/Vazir.ttf");
        body_raw.setTypeface(tf);




        share_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent myIntent=new Intent(Intent.ACTION_SEND);
                myIntent.setType("text/plain");
                String share_body=new_body+System.getProperty("line.separator")+" منبع: سایت امام هشت  "+System.getProperty("line.separator")+" www.emam8.com ";
                String share_sub="ارسال مطلب به دیگران ";
                myIntent.putExtra(Intent.EXTRA_SUBJECT,share_sub);
                myIntent.putExtra(Intent.EXTRA_TEXT,share_body);
                startActivity(Intent.createChooser(myIntent,"اشتراک گذاری"));

            }
        });


        db.close();
    }
}
