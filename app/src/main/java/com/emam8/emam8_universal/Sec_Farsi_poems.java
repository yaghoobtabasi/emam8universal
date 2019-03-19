package com.emam8.emam8_universal;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.emam8.emam8_universal.Adapter.SectionFarsiAdapter;
import com.emam8.emam8_universal.App.AppController;
import com.emam8.emam8_universal.Model.SecFarsiPoem;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

public class Sec_Farsi_poems extends AppCompatActivity {

    private ArrayList<SecFarsiPoem> secF = new ArrayList<>();
    private RecyclerView recyclerView;
    private SectionFarsiAdapter adapter;
    private LinearLayoutManager layoutManager;

    private CardView cardView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sec_farsi_poems);

        cardView = findViewById(R.id.cardView_secFarsi);

        recyclerView = findViewById(R.id.recyclerView_sec_poemFarsi);
        adapter = new SectionFarsiAdapter(secF, Sec_Farsi_poems.this);
        layoutManager = new LinearLayoutManager(Sec_Farsi_poems.this);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(adapter);

        setData();


    }

    private void setData() {
        final String url = "https://emam8.com/api/emam8_apps/emam8_universal/section_list";

        final ProgressDialog pDialog;
        pDialog = new ProgressDialog(Sec_Farsi_poems.this);
        pDialog.setMessage("در حال بارگیری اطلاعات ...");
        pDialog.setCancelable(false);
        pDialog.show();

        Response.Listener<JSONArray> listener = new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {

                try {
                    for (int i = 0; i < response.length(); i++) {

                        JSONObject jsonObject = response.getJSONObject(i);
                        String id = jsonObject.getString("id");
                        String title = jsonObject.getString("title");
                        String count = jsonObject.getString("count");

                        secF.add(new SecFarsiPoem(id, title, count));
                    }
                    adapter.notifyDataSetChanged();

                } catch (JSONException e) {
                    e.printStackTrace();
                }
                pDialog.dismiss();
            }
        };

        Response.ErrorListener errorListener = new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getApplicationContext(),"متاسفانه ارتباط با سرور برقرار نشد ممکن است مشکل از قطعی اینترنت شما باشد یا شلوغ بودن سرور",Toast.LENGTH_LONG).show();
                pDialog.dismiss();
            }
        };

        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Request.Method.POST, url, listener, errorListener);

        AppController.getInstance().addToRequestQueue(jsonArrayRequest);

    }

    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase));
    }
}
