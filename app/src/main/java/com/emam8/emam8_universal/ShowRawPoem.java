package com.emam8.emam8_universal;


import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.emam8.emam8_universal.Model.Poem_retro;
import com.emam8.emam8_universal.services.Load_poems;
import com.emam8.emam8_universal.services.ConnectionDetector;
import com.google.gson.JsonArray;
import com.google.gson.JsonIOException;
import com.google.gson.JsonParseException;

import java.util.HashMap;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import static com.emam8.emam8_universal.MainActivity.app_name;
import static com.emam8.emam8_universal.MainActivity.app_version;

//import com.emam8.emam8_universal.App.Article_functions;

public class ShowRawPoem extends AppCompatActivity {
    public TextView body_raw;
    private  database db;
    private String article_id,state;
    public String body_r,new_body;
    ImageView heart_btn,share_btn;
    private SwipeRefreshLayout swipeRefreshLayout;
    private String body_response,id,title,sabk,sname,cname,sectionid,catid,state_1,body,poet_id,poet_name;
    private ConnectionDetector connectionDetector;
    public final String Site_url="https://emam8.com/";
    private static final String url_load_poem="https://emam8.com/api/emam8_apps/";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_raw_poem);
        Bundle bundle = getIntent().getExtras();


        article_id=bundle.getString("article_id");
        state=bundle.getString("state");
        String poet=bundle.getString("poet");
        String title=bundle.getString("title");




        db = new database(ShowRawPoem.this);
        db.useable();
        db.open();

        body_raw=(TextView)findViewById(R.id.txt_raw_body);
        heart_btn=(ImageView)findViewById(R.id.img_heart_btn_raw);
        share_btn=(ImageView)findViewById(R.id.img_share_btn_raw);

        if(db.check_fav_content(article_id))
        {
            heart_btn.setImageResource(R.drawable.heart_red);
        }else
        {
            heart_btn.setImageResource(R.drawable.heart_white);
        }



        swipeRefreshLayout = (SwipeRefreshLayout)findViewById(R.id.swiprefresh_raw);

        swipeRefreshLayout.setColorSchemeColors(Color.GRAY, Color.GREEN, Color.BLUE,
                Color.RED, Color.CYAN);
        swipeRefreshLayout.setDistanceToTriggerSync(20);// in dips
        swipeRefreshLayout.setSize(SwipeRefreshLayout.DEFAULT);// LARGE also can be used
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {

               load_data();

            }
        });




        load_data();


        body_raw.setText(body_response);
        Typeface tf = Typeface.createFromAsset(getAssets(),"fonts/Vazir.ttf");
        body_raw.setTypeface(tf);




        share_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent myIntent=new Intent(Intent.ACTION_SEND);
                myIntent.setType("text/plain");
                String share_body=body_raw.getText()+System.getProperty("line.separator")+" منبع: سایت امام هشت  "+System.getProperty("line.separator")+" www.emam8.com/article/"+article_id;
                String share_sub="ارسال مطلب به دیگران ";
                myIntent.putExtra(Intent.EXTRA_SUBJECT,share_sub);
                myIntent.putExtra(Intent.EXTRA_TEXT,share_body);
                startActivity(Intent.createChooser(myIntent,"اشتراک گذاری"));

            }
        });


        db.close();
    }



    void load_data(){
        JsonArray array=new JsonArray();

        // Log.w("info",url);

        final ProgressDialog pDialog;
        pDialog=new ProgressDialog(ShowRawPoem.this);
        pDialog.setMessage("در حال فراخوانی اطلاعات ...");
        pDialog.setCancelable(true);
        pDialog.show();
        connectionDetector=new ConnectionDetector(this);
        if(!connectionDetector.is_connected())
        {
            Toast.makeText(getApplicationContext(),"اتصال اینترنت را چک کنید",Toast.LENGTH_LONG).show();
            pDialog.dismiss();
//            return "عدم اتصال اینترنت لطفا اتصال اینترنت خود را بررسی کنید";
        }



        Retrofit retro=new Retrofit.Builder()
                .baseUrl(Site_url+"api/emam8_apps/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        HashMap<String,String> headerMap=new HashMap<String,String>();
        headerMap.put("content-type","application/json");


        Load_poems load_poems=retro.create(Load_poems.class);


        Call<Poem_retro> call=load_poems.load_article(headerMap,article_id,app_name,app_version,"json");

        call.enqueue(new Callback<Poem_retro>() {
            @Override
            public void onResponse(Call<Poem_retro> call, Response<Poem_retro> response) {
                Log.d(MainActivity.TAG,"onResponse : server response :"+response.toString());


                try {
                    body=response.body().getBody();
                    body_raw.setText(body);
                    id=response.body().getId();
                    sabk=response.body().getSabk();
                    title=response.body().getTitle();
                    cname=response.body().getCname();
                    catid=response.body().getCatid();
                    sname=response.body().getSname();
                    sectionid=response.body().getSectionid();
                    state=response.body().getState();
                    poet_id=response.body().getPoet_id();
                    poet_name=response.body().getPoet_name();


                    swipeRefreshLayout.setRefreshing(false);
                    pDialog.dismiss();

//                    Log.e(" Full json gson => ", new Gson().toJson(response));
//                    Log.e(MainActivity.TAG,"on response:Json :"+data.optString("json"));
                }catch (JsonIOException e){
                    Log.e(MainActivity.TAG,"on response:JsonException :"+e.getMessage());
                    swipeRefreshLayout.setRefreshing(false);
                    pDialog.dismiss();
                }catch (JsonParseException e) {
                    Log.e(MainActivity.TAG,"on response:JsonParseException :"+e.getMessage());
                    e.printStackTrace();
                    swipeRefreshLayout.setRefreshing(false);
                    pDialog.dismiss();
                }
            }

            @Override
            public void onFailure(Call<Poem_retro> call, Throwable t) {
            Log.e(MainActivity.TAG,"On Failure :something went wrong ..."+t.getMessage());
            Toast.makeText(ShowRawPoem.this,"متاسفانه خطایی رخ داد مجددا تلاش کنید",Toast.LENGTH_SHORT).show();
            }
        });

        String text=body_response;

        pDialog.dismiss();

    }


}
