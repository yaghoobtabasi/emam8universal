package com.emam8.emam8_universal;

import android.app.ProgressDialog;
import android.content.Context;
import android.database.Cursor;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.emam8.emam8_universal.Adapter.CategoryFarsiAdapter;
import com.emam8.emam8_universal.App.AppController;
import com.emam8.emam8_universal.Model.CatFarsiPoem;
import com.emam8.emam8_universal.Model.SecFarsiPoem;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

public class Cat_Farsi_Poems extends AppCompatActivity {

    private ArrayList<CatFarsiPoem> catF = new ArrayList<>();
    private RecyclerView recyclerView;
    private CategoryFarsiAdapter adapter;
    private LinearLayoutManager layoutManager;
    private Database database;
    private Cursor categoryCursor;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cat__farsi__poems);

        Bundle bundle = getIntent().getExtras();
        final String sectionid = bundle.getString("sectionid");

        recyclerView = findViewById(R.id.recyclerView_cat_poemFarsi);
        adapter = new CategoryFarsiAdapter(catF, Cat_Farsi_Poems.this);
        layoutManager = new LinearLayoutManager(Cat_Farsi_Poems.this);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(adapter);

        database = new Database(getApplicationContext());
        database.useable();
        database.open();

        if(database.check_category_added(sectionid)){
            setDatafromDb(sectionid);
            database.close();
        }
        else {
            setData(sectionid);
            database.close();
        }

    }

    private void setDatafromDb(String sectionId) {

        database = new Database(getApplicationContext());
        database.useable();
        database.open();

        categoryCursor = database.load_from_category();

        if (categoryCursor.getCount() == 0) {
            Toast.makeText(getApplicationContext(), "No Data Is Show", Toast.LENGTH_LONG).show();
        } else {
            while (categoryCursor.moveToNext()) {
                catF.add(new CatFarsiPoem(categoryCursor.getString(0),
                        categoryCursor.getString(1),
                        categoryCursor.getString(2),
                        categoryCursor.getString(3)));
            }
        }


        adapter.notifyDataSetChanged();
        database.close();
    }

    private void setData(String sectionid) {
        final String Url = BuildConfig.Apikey_CatList+sectionid;

        final ProgressDialog progressDialog;
        progressDialog = new ProgressDialog(Cat_Farsi_Poems.this);
        progressDialog.setMessage("در حال بارگیری اطلاعات ...");
        progressDialog.setCancelable(false);
        progressDialog.show();

        Response.Listener<JSONArray> listener = new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                try {
                    database = new Database(getApplicationContext());
                    database.writable();
                    database.open();

                    for (int i = 0; i < response.length(); i++) {

                        JSONObject jsonObject = response.getJSONObject(i);
                        String id = jsonObject.getString("id");
                        String title = jsonObject.getString("title");
                        String count = jsonObject.getString("count");
                        String ordering = jsonObject.getString("ordering");

                        if (!database.check_category_exist(id)) {
                            database.add_to_category(id, title, count,ordering,getApplicationContext());
                        }

                        catF.add(new CatFarsiPoem(id,title,count,ordering));
                    }
                    adapter.notifyDataSetChanged();
                    database.close();

                } catch (JSONException e) {
                    e.printStackTrace();
                }
                    progressDialog.dismiss();
            }
        };

        JSONArray array = new JSONArray();
        JSONObject obj = new JSONObject();

        try {
            String app_name = MainActivity.app_name;
            String version = MainActivity.app_version;
            obj.put("catid", sectionid);
            obj.put("mode", "all");
            obj.put("gid", "0");
            obj.put("version", version);
            obj.put("app_name", app_name);

        } catch (JSONException e) {
            e.printStackTrace();

        }

        array.put(obj);

        Log.d("info","array=>"+array.toString());

        Response.ErrorListener errorListener = new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                Toast.makeText(getApplicationContext(),"متاسفانه ارتباط با سرور برقرار نشد ممکن است مشکل از قطعی اینترنت شما باشد یا شلوغ بودن سرور",Toast.LENGTH_LONG).show();
                progressDialog.dismiss();
            }
        };

        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Request.Method.POST,Url,listener,errorListener);

        AppController.getInstance().addToRequestQueue(jsonArrayRequest);
    }

    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase));
    }
}
