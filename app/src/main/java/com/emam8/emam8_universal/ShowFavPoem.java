package com.emam8.emam8_universal;


import android.content.Context;
import android.content.Intent;
import android.graphics.Typeface;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.text.method.ScrollingMovementMethod;
import android.view.View;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import com.emam8.emam8_universal.App.Article_functions;
import com.emam8.emam8_universal.services.ConnectionDetector;

import java.io.IOException;

import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

public class ShowFavPoem extends AppCompatActivity {
    SeekBar seekBar;
    MediaPlayer mediaPlayer;
    Handler handler;
    Runnable runnable;
    private String sabk_url;
    private Boolean isplay;
    public TextView txt_title,body;
    private  database db;
    public  int pos;
    private String sabk,article_id,state,new_body;
    ImageView heart_btn,img_play,share_btn,dwonload_img;

    ConnectionDetector connectionDetector;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_fav_poem);


        isplay=false;
        body=(TextView)findViewById(R.id.fp_txt_body);
        share_btn=(ImageView)findViewById(R.id.fp_img_share_btn);

        Bundle bundle = getIntent().getExtras();
        pos= bundle.getInt("Pos");
        article_id=bundle.getString("article_id");
        state=bundle.getString("state");

        Article_functions inc_article=new Article_functions();
        inc_article.inc_counter(article_id);
//        inc_counter(article_id);






//        Toast.makeText(getApplicationContext(),"state = "+state+" ",Toast.LENGTH_SHORT).show();
        img_play=(ImageView)findViewById(R.id.fp_img_play);



        if(state.compareTo("8")==0){
            Toast.makeText(getApplicationContext()," "+"شما از نسخه رایگان این برنامه استفاده می کنید برا استفاده از همه امکانات این برنامه آن را خریداری کنید ",Toast.LENGTH_LONG).show();
            Intent i=new Intent(getApplicationContext(),RecyclerPoem.class);
            startActivity(i);
            finish();


        }

        db = new database(ShowFavPoem.this);
        db.useable();
        db.open();
        heart_btn=(ImageView)findViewById(R.id.fp_img_heart_btn);


        if(db.check_fav_content(article_id))
        {
            heart_btn.setImageResource(R.drawable.heartr);
        }else
        {
            heart_btn.setImageResource(R.drawable.heart);
        }

        heart_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                db = new database(ShowFavPoem.this);
                db.writable();
                db.open();
                if(!db.check_fav_content(article_id))
                {

                    long result = db.add_fav(article_id);
                    if (result > 0) {
                        Toast.makeText(getApplicationContext(),"این مطلب به اشعار منتخب افزوده شد",Toast.LENGTH_SHORT).show();
                        heart_btn.setImageResource(R.drawable.heartr);
                    }

                }else
                {
                    long res=db.del_fav(article_id);
                    if(res>0){
                        heart_btn.setImageResource(R.drawable.heart);
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
        new_body=title+ System.getProperty("line.separator")+"********"+System.getProperty("line.separator")+text;

        body.setText(new_body);

        body.setMovementMethod(new ScrollingMovementMethod());
        sabk=db.namayesh_by_id(article_id,9,"app_contents");






        Typeface tf = Typeface.createFromAsset(getAssets(),"fonts/Vazir.ttf");
        body.setTypeface(tf);


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


        if(!TextUtils.isEmpty(sabk))
        {
            handler=new Handler();

            seekBar=(SeekBar)findViewById(R.id.fp_seekBar);


            sabk_url="https://emam8.com/"+sabk;
            mediaPlayer = new MediaPlayer();

//            Log.i("info","start Media player");

            mediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);


            img_play.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
//                    Log.i("info","Click on button Media player");
                    connectionDetector=new ConnectionDetector(getApplicationContext());
                    if(!connectionDetector.is_connected()&&(!mediaPlayer.isPlaying()))
                    {
                        Toast.makeText(getApplicationContext(),"اتصال اینترنت را چک کنید",Toast.LENGTH_LONG).show();
                        return;
                    }


                    try {
                        if(!isplay) {
                            img_play.setImageResource(R.drawable.time);
                            mediaPlayer.setDataSource(sabk_url);
                            mediaPlayer.prepare(); // might take long! (for buffering, etc)
                        }
                        else
                        {
                            if(mediaPlayer.isPlaying()){
                                mediaPlayer.pause();
                                img_play.setImageResource(R.drawable.play_btn);

                            }else{
                                mediaPlayer.start();
                                img_play.setImageResource(R.drawable.pause_btn);
                                seekBar.setProgress(mediaPlayer.getCurrentPosition());
                                runnable=new Runnable() {
                                    @Override
                                    public void run() {
                                        playCycle();
//                                        Log.i("info","Run Media player");
                                    }
                                };
                                handler.postDelayed(runnable,1000);

                            }
                        }
//                mediaPlayer.start();
//                Toast.makeText(ShowPoem.this,""+sabk_url,Toast.LENGTH_SHORT).show();

                        if(!isplay) {
                            mediaPlayer.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
                                @Override
                                public void onPrepared(MediaPlayer mp) {
//                                    Log.i("info", "Prepare Media player");
                                    seekBar.setMax(mediaPlayer.getDuration());
                                    playCycle();
                                    mediaPlayer.start();
                                    seekBar.setProgress(mediaPlayer.getCurrentPosition());
                                    runnable=new Runnable() {
                                        @Override
                                        public void run() {
                                            playCycle();
//                                            Log.i("info","Run Media player");
                                        }
                                    };
                                    handler.postDelayed(runnable,1000);
                                    img_play.setImageResource(R.drawable.pause_btn);

                                }
                            });
                        }




                        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
                            @Override
                            public void onProgressChanged(SeekBar seekBar, int progress, boolean input) {
                                if(input){
                                    mediaPlayer.seekTo(progress);
                                }

                            }

                            @Override
                            public void onStartTrackingTouch(SeekBar seekBar) {

                            }

                            @Override
                            public void onStopTrackingTouch(SeekBar seekBar) {

                            }
                        });

                    }catch (IOException e){
                        e.printStackTrace();
                    }

                    isplay=true;


                }
            });


        }




        db.close();

    }


    public void playCycle() {
        seekBar.setProgress(mediaPlayer.getCurrentPosition());

        if(mediaPlayer.isPlaying()){
//            Log.i("info","Playing Media player");
            runnable=new Runnable() {
                @Override
                public void run() {
                    playCycle();
//                    Log.i("info","Run Media player");
                }
            };
            handler.postDelayed(runnable,1000);

        }
    }



    @Override
    protected void onResume() {
        if(!TextUtils.isEmpty(sabk)){
            super.onResume();
            mediaPlayer.start();
//            Log.i("info","On Resume Media player");
//            img_play.setImageResource(R.drawable.pause_btn);
        }
    }

    @Override
    protected void onPause() {
        if(!TextUtils.isEmpty(sabk)) {
            super.onPause();
            mediaPlayer.pause();
//            Log.i("info","On Pause Media player");
//            img_play.setImageResource(R.drawable.play_btn);
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if(!TextUtils.isEmpty(sabk)){
            mediaPlayer.release();
            handler.removeCallbacks(runnable);
//            Log.i("info","Destroy Media player");
        }
    }


    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase));
    }


}