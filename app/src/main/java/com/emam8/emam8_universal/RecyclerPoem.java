package com.emam8.emam8_universal;


import android.app.ProgressDialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.Toolbar;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.emam8.emam8_universal.Adapter.PoemsAdapter;
import com.emam8.emam8_universal.App.AppController;
import com.emam8.emam8_universal.Model.Poems;
import com.miguelcatalan.materialsearchview.MaterialSearchView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

public class RecyclerPoem extends AppCompatActivity {
    public  List<Poems> poem = new ArrayList<>();
    private String catid,mode,gid,poet_id,allow_private,lang;
    private RecyclerView recyclerView;
    private PoemsAdapter adapter;
    android.support.v7.widget.Toolbar toolbar;
    MaterialSearchView materialSearchView;
    private LinearLayoutManager layoutManager;
    TextView title;

    //variables for pagination
    private Boolean isLoading=true;
    private int pastVisibleItems,visibleItemCount,totalItemCount,previoustotal=0;
    private int view_threshold=10;
    private int page_number=1;
    private SwipeRefreshLayout swipeRefreshLayout;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recycler_poem);
        Bundle bundle = getIntent().getExtras();
        catid = "-1";
        mode = "all";
        gid = "";
        poet_id = "";
        allow_private ="";
        lang="torki";


        recyclerView = (RecyclerView) findViewById(R.id.poem_recycler);
        adapter = new PoemsAdapter(poem,catid,gid,poet_id, mode,RecyclerPoem.this);
        layoutManager=new LinearLayoutManager(RecyclerPoem.this);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(adapter);

        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                visibleItemCount=layoutManager.getChildCount();
                totalItemCount=layoutManager.getItemCount();
                pastVisibleItems=layoutManager.findFirstVisibleItemPosition();

                if(isLoading){
                    if(totalItemCount>previoustotal){
                        isLoading=false;
                        previoustotal=totalItemCount;
                    }

                }
            if(!isLoading&&(totalItemCount-visibleItemCount)<=(pastVisibleItems+view_threshold)){
                    page_number++;
                    setData(catid);
                    isLoading=true;
            }
            }

        });


        swipeRefreshLayout = (SwipeRefreshLayout)findViewById(R.id.swiprefresh_recpoem);

        swipeRefreshLayout.setColorSchemeColors(Color.GRAY, Color.GREEN, Color.BLUE,
                Color.RED, Color.CYAN);
        swipeRefreshLayout.setDistanceToTriggerSync(20);// in dips
        swipeRefreshLayout.setSize(SwipeRefreshLayout.DEFAULT);// LARGE also can be used
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {

                setData(catid);

            }
        });


        setupToolbar();
        setData(catid);




    }


    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase));
    }



    private void setupToolbar(){
        toolbar = (android.support.v7.widget.Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle(" فهرست مطالب ");

    }


    private void setData(final String catid) {


        final String url="https://emam8.com/api/emam8_apps/poem_list"+"?page="+page_number;
       // Log.w("info",url);

        final ProgressDialog pDialog;
        pDialog=new ProgressDialog(RecyclerPoem.this);
        pDialog.setMessage("در حال بارگیری اطلاعات ...");
        pDialog.setCancelable(false);
        pDialog.show();

        Response.Listener<JSONArray> listener=new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                try {
//                    Log.w("info","Ya Zahra ...");
                    for(int i=0;i<response.length();i++){
                        JSONObject jsonObject=response.getJSONObject(i);
                        String title=jsonObject.getString("title");
                        String article_id=jsonObject.getString("id");
                        String poet=jsonObject.getString("full_name");
                        String sabk=jsonObject.getString("sabk");
                        String state=jsonObject.getString("state");
                        String profile=jsonObject.getString("profile");
                        String poet_id=jsonObject.getString("poet_id");

                        poem.add(new Poems(title, sabk, poet, article_id, state,profile,poet_id));
//                        poem.add(new Poems("شعر تولد", "سبک", "شاعر", "2525", "1"));
//                        Log.w("info","Ya Ali ..."+poet);
                    }
                    adapter.notifyDataSetChanged();

                }catch (Exception e){
                    e.printStackTrace();

                }
                pDialog.dismiss();
//                recyclerView.setVisibility(View.VISIBLE);
            }
        };


        JSONArray array=new JSONArray();
        JSONObject obj=new JSONObject();

        try {
            String app_name=MainActivity.app_name;
            String version=MainActivity.app_version;
            obj.put("catid",catid);
            obj.put("mode",mode);
            obj.put("gid",gid);
            obj.put("version",version);
            obj.put("app_name",app_name);

            swipeRefreshLayout.setRefreshing(false);
        } catch (JSONException e) {
            e.printStackTrace();
            swipeRefreshLayout.setRefreshing(false);
        }

        array.put(obj);

//        Log.d("info","array=>"+array.toString());

        JsonArrayRequest request=new JsonArrayRequest(Request.Method.POST,url,  array ,listener,new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getApplicationContext(),"متاسفانه ارتباط با سرور برقرار نشد ممکن است مشکل از قطعی اینترنت شما باشد یا شلوغ بودن سرور",Toast.LENGTH_LONG).show();
                pDialog.dismiss();
                swipeRefreshLayout.setRefreshing(false);
            }
        });

        AppController.getInstance().addToRequestQueue(request);
      }









}
