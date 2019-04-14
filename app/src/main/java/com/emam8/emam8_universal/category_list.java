package com.emam8.emam8_universal;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.emam8.emam8_universal.Adapter.CategoriesAdapter;
import com.emam8.emam8_universal.App.AppController;
import com.emam8.emam8_universal.Model.Categories;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class category_list extends AppCompatActivity {
    private List<Categories> categories=new ArrayList<>();
    private RecyclerView recyclerView;
    private CategoriesAdapter adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_category_list);
        Bundle bundle = getIntent().getExtras();
        final String sectionid= bundle.getString("sectionid");
//        Toast.makeText(getApplicationContext(),sectionid,Toast.LENGTH_LONG).show();

        recyclerView=(RecyclerView)findViewById(R.id.cat_recycler);
        recyclerView.setVisibility(View.GONE);
        adapter=new CategoriesAdapter(categories,getApplicationContext());
        recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        recyclerView.setAdapter(adapter);
        setCats(sectionid);
    }

    void setCats(String sectionid){
        final String url=BuildConfig.Apikey_CatList+sectionid;
        final ProgressDialog pDialog;
        pDialog=new ProgressDialog(category_list.this);
        pDialog.setMessage("در حال بارگیری اطلاعات ...");
        pDialog.setCancelable(false);
        pDialog.show();

        Response.Listener<JSONArray> listener=new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
//                Toast.makeText(getApplicationContext(),BuildConfig.ApiKey_LoadPoem,Toast.LENGTH_LONG).show();
                try {
                    for(int i=0;i<response.length();i++){
                        JSONObject jsonObject=response.getJSONObject(i);
                        String title=jsonObject.getString("title");
                        String catid=jsonObject.getString("id");
                        String count=jsonObject.getString("count");
                        categories.add(new Categories(title,catid,count));


                    }

                }catch (Exception e){
                    e.printStackTrace();

                }
                pDialog.dismiss();
                recyclerView.setVisibility(View.VISIBLE);
            }
        };

        Response.ErrorListener errorListener=new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
//                Toast.makeText(getApplicationContext(),"Error",Toast.LENGTH_SHORT).show();
                pDialog.dismiss();
            }
        };

        JsonArrayRequest request=new JsonArrayRequest(Request.Method.POST,url, (JSONArray) null,listener,errorListener);
        AppController.getInstance().addToRequestQueue(request);


    }

}
