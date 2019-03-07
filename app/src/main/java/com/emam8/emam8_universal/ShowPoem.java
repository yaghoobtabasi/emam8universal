package com.emam8.emam8_universal;
import android.Manifest;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;

import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.support.v4.widget.SwipeRefreshLayout;
import android.text.TextUtils;
import android.text.method.ScrollingMovementMethod;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import com.emam8.emam8_universal.Model.Poem_retro;
import com.emam8.emam8_universal.services.ConnectionDetector;
import com.emam8.emam8_universal.services.FileDownloadClient;
import com.emam8.emam8_universal.services.Load_poems;
import com.emam8.emam8_universal.services.RuntimePermissionsActivity;
import com.google.gson.JsonArray;
import com.google.gson.JsonIOException;
import com.google.gson.JsonParseException;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.HashMap;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

import static com.emam8.emam8_universal.MainActivity.app_name;
import static com.emam8.emam8_universal.MainActivity.app_version;


public class ShowPoem extends RuntimePermissionsActivity {
    SeekBar seekBar;
    MediaPlayer mediaPlayer;
    Handler handler;
    Runnable runnable;
    private String sabk_url;
    private Boolean isplay;
    public TextView txt_title,txt_body;
    private  database db;
    public  int pos;

    public String sabk_path;
    public final String Site_url="https://emam8.com/";
    private static final String url_load_poem="https://emam8.com/api/emam8_apps/";
    private String article_id,state,new_body,poet;
    private SwipeRefreshLayout swipeRefreshLayout;
    private String body_response,id,title,body,sabk,sname,cname,sectionid,catid,state_1,poet_id,poet_name;

    ImageView heart_btn,img_play,share_btn,dwonload_img;

    ConnectionDetector connectionDetector;


    private final int Write_External_Request_Code=30;
    private final int Read_External_Request_Code=31;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_poem);
        body_response="در حال بارگیری اطلاعات";

        isplay=false;
        txt_body=(TextView)findViewById(R.id.txt_body);
        share_btn=(ImageView)findViewById(R.id.share_showPoem);
        dwonload_img=(ImageView)findViewById(R.id.download_showPoem);



        Bundle bundle = getIntent().getExtras();

        article_id=bundle.getString("article_id");
        state=bundle.getString("state");
        sabk_path=bundle.getString("sabk");
        poet=bundle.getString("poet");
        title=bundle.getString("title");



        img_play=(ImageView)findViewById(R.id.fab_play);



        if(state.compareTo("8")==0){
            Toast.makeText(getApplicationContext()," "+"شما از نسخه رایگان این برنامه استفاده می کنید برا استفاده از همه امکانات این برنامه آن را خریداری کنید ",Toast.LENGTH_LONG).show();
            Intent i=new Intent(getApplicationContext(),RecyclerPoem.class);
            startActivity(i);
            finish();


        }

        db = new database(ShowPoem.this);
        db.useable();
        db.open();
        heart_btn=(ImageView)findViewById(R.id.like_showPoem);


        if(db.check_fav_content(article_id))
        {
            heart_btn.setImageResource(R.drawable.heart_red);
        }else
        {
            heart_btn.setImageResource(R.drawable.heart);
        }

heart_btn.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View v) {
        Log.d("Emam8",article_id+"");
    }
});

        dwonload_img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getApplicationContext(),"آغاز عملیات دانلود فایل",Toast.LENGTH_SHORT).show();
                check_permission();
                downloadFile(sabk_url);
            }
        });



        final ProgressDialog pDialog;
        pDialog=new ProgressDialog(ShowPoem.this);
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



        swipeRefreshLayout = (SwipeRefreshLayout)findViewById(R.id.swiprefresh_sabk);

        swipeRefreshLayout.setColorSchemeColors(Color.GRAY, Color.GREEN, Color.BLUE,
                Color.RED, Color.CYAN);
        swipeRefreshLayout.setDistanceToTriggerSync(20);// in dips
        swipeRefreshLayout.setSize(SwipeRefreshLayout.DEFAULT);// LARGE also can be used
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {

                load_data();
                pDialog.dismiss();

            }
        });


        load_data();



        new_body="در حال دریافت اطلاعات";
        txt_body.setText(new_body);

        txt_body.setMovementMethod(new ScrollingMovementMethod());
        sabk=sabk_path;


        share_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent myIntent=new Intent(Intent.ACTION_SEND);
                myIntent.setType("text/plain");
                String share_body=txt_body.getText()+System.getProperty("line.separator")+" منبع: سایت امام هشت  "+System.getProperty("line.separator")+" https://emam8.com/article/"+article_id;
                String share_sub="ارسال مطلب به دیگران ";
                myIntent.putExtra(Intent.EXTRA_SUBJECT,share_sub);
                myIntent.putExtra(Intent.EXTRA_TEXT,share_body);
                startActivity(Intent.createChooser(myIntent,"اشتراک گذاری"));

            }
        });


        if(!TextUtils.isEmpty(sabk_path))
        {
            handler=new Handler();

            seekBar=(SeekBar)findViewById(R.id.seekbar_showPoem);


            sabk_url="https://emam8.com/"+sabk_path;

            //check exist sabk on download folder
            if(check_sabk_exist(sabk))
            {

                String fileName = sabk.substring(sabk.lastIndexOf('/')+1, sabk.length());
                File directory = new File(Environment.getDataDirectory()
                        + "/Emam8_apps/audio/");
                sabk_url=directory+"/"+fileName;
                sabk_url=Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_MUSIC)+"/"+fileName;
            }



            mediaPlayer = new MediaPlayer();

            Log.i("info","start Media player");

            mediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);


            img_play.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {


                    Log.i("info","Click on button Media player");
                    Log.d("exist",sabk);
                    connectionDetector=new ConnectionDetector(getApplicationContext());
                    if((!connectionDetector.is_connected()&&(!mediaPlayer.isPlaying()))&&(!check_sabk_exist(sabk)))
                    {
                        Toast.makeText(getApplicationContext(),"لطفا اتصال  اینترنت را چک کنید",Toast.LENGTH_LONG).show();
                        return;
                    }
                    Log.d("exist",sabk_url);


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
                                        Log.i("info","Run Media player");
                                    }
                                };
                                handler.postDelayed(runnable,1000);

                            }
                        }
                mediaPlayer.start();
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
        pDialog.dismiss();

    }

    @Override
    public void onPermissionsGranted(int requestCode) {
if(requestCode==30)
{

    downloadFile(sabk_path);
}
    }

    @Override
    public void onPermissionsDeny(int requestCode) {
if(requestCode==30){
    Toast.makeText(getApplicationContext(),"مجوز نوشتن بر روی حافظه صادر نشد لذا فایل دانلود نمی شود",Toast.LENGTH_SHORT).show();
}else if(requestCode==31){
    Toast.makeText(getApplicationContext(),"مجوز خواندن از حافظه صادر نشد",Toast.LENGTH_SHORT).show();
}
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
            img_play.setImageResource(R.drawable.pause_btn);
        }
    }

    @Override
    protected void onPause() {
        if(!TextUtils.isEmpty(sabk)) {
            super.onPause();
            mediaPlayer.pause();
//            Log.i("info","On Pause Media player");
            img_play.setImageResource(R.drawable.play_btn);
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


    private void downloadFile(String url)
    {
        connectionDetector=new ConnectionDetector(getApplicationContext());

        if(!connectionDetector.is_connected()&&(!mediaPlayer.isPlaying()))
        {
            Toast.makeText(getApplicationContext(),"اتصال اینترنت را چک کنید",Toast.LENGTH_LONG).show();
            return;
        }

        Retrofit.Builder builder=new Retrofit.Builder().baseUrl(Site_url);
        Retrofit retrofit=builder.build();

        FileDownloadClient fileDownloadClient=retrofit.create(FileDownloadClient.class);
        Call<ResponseBody> call=fileDownloadClient.downloadFile(url);

        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                boolean success= false;
                try {
                    success = writeResponseBodyToDisk(response.body());
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                }
                if(success){
                    Toast.makeText(getApplicationContext(),"فایل با موفقیت در پوشه دانلود ها ذخیره شد ",Toast.LENGTH_LONG).show();
                }else{
                    Toast.makeText(getApplicationContext(),"متاسفانه عملیات دانلود با شکست مواجه شد ",Toast.LENGTH_LONG).show();
                }


            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                Toast.makeText(getApplicationContext(),"فایل دانلود نشد ",Toast.LENGTH_LONG).show();
            }
        });


    }


    private boolean writeResponseBodyToDisk(ResponseBody body) throws FileNotFoundException {
try {
    //todo change the file location/name according to your needs
//    Toast.makeText(getApplicationContext(),sabk_path,Toast.LENGTH_SHORT).show();

    String urlStr = sabk_path;
    String fileName = urlStr.substring(urlStr.lastIndexOf('/')+1, urlStr.length());

    File directory = new File(Environment.getDataDirectory()
            + "/Emam8_apps/audio/",fileName);
    if (!directory.exists()) {
        directory.mkdir();}

    File futureStudioIconFile=new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_MUSIC),fileName);
    InputStream inputStream=null;
    OutputStream outputStream=null;

    try {
        byte[] fileReader=new byte[4096];
        long fileSize=body.contentLength();
        long fileSizeDownloaded=0;

        inputStream=body.byteStream();
        outputStream=new FileOutputStream(futureStudioIconFile);
        while (true){
            int read=inputStream.read(fileReader);
            if(read==-1){
                break;
            }

            outputStream.write(fileReader,0,read);
            fileSizeDownloaded+=read;
            Log.d("Future studio","file download"+fileSizeDownloaded+"of "+fileSize);
        }
        outputStream.flush();
        return true;

    } catch (IOException e){
        return false;
    }finally {
        if(inputStream!=null){
            inputStream.close();
        }
        if(outputStream!=null){
            outputStream.close();
        }


    }



}catch (IOException e){
    return false;
}

    }

    void check_permission(){
        ShowPoem.super.requestAppPermissions(new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE,
                Manifest.permission.READ_EXTERNAL_STORAGE
        },Write_External_Request_Code);
    }

    boolean check_sabk_exist(String sabk){
        String fileName = sabk.substring(sabk.lastIndexOf('/')+1, sabk.length());
        File directory = new File(Environment.getDataDirectory()
                + "/Emam8_apps/audio/");
        if (!directory.exists()) {
            directory.mkdir();
        }
        fileName=Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_MUSIC)+"/"+fileName;;
        File sabkFile=new File(fileName);
        if (sabkFile.exists())
        {
            Log.d("File_exist :","exist file "+fileName);
            return true;
        }else{
            Log.d("File_exist ","not file"+fileName);
            return false;
        }
    }


    void load_data(){
        JsonArray array=new JsonArray();

        // Log.w("info",url);

        final ProgressDialog pDialog;
        pDialog=new ProgressDialog(ShowPoem.this);
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

        String article_id="48000";
        String app_name="s";
        String app_version="1.2";
        Call<Poem_retro> call=load_poems.load_article(headerMap,article_id,app_name,app_version,"json");

        call.enqueue(new Callback<Poem_retro>() {
            @Override
            public void onResponse(Call<Poem_retro> call, Response<Poem_retro> response) {
                Log.d(MainActivity.TAG,"onResponse : server response :"+response.toString());

                pDialog.dismiss();
                try {
                    body=response.body().getBody();
                    txt_body.setText(body);
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
                    pDialog.dismiss();
                    swipeRefreshLayout.setRefreshing(false);
                }
            }

            @Override
            public void onFailure(Call<Poem_retro> call, Throwable t) {
                Log.e(MainActivity.TAG,"On Failure :something went wrong ..."+t.getMessage());
                Toast.makeText(ShowPoem.this,"متاسفانه خطایی رخ داد مجددا تلاش کنید",Toast.LENGTH_SHORT).show();
                pDialog.dismiss();
            }
        });

        String text=body_response;

        pDialog.dismiss();
    }

    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase));
    }



}

