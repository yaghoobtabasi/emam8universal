package com.emam8.emam8_universal;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.pm.ActivityInfo;
import android.database.Cursor;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
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
    private Database database;
    private CardView cardView;
    private Cursor sectionCursor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sec_farsi_poems);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        cardView = findViewById(R.id.cardView_secFarsi);

        recyclerView = findViewById(R.id.recyclerView_sec_poemFarsi);
        adapter = new SectionFarsiAdapter(secF, Sec_Farsi_poems.this);
        layoutManager = new LinearLayoutManager(Sec_Farsi_poems.this);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(adapter);

        database = new Database(getApplicationContext());
        database.useable();
        database.open();

        if (database.check_section_added()) {
            database.close();
            setDatafromDb();
        } else {
            database.close();
            setData();
        }


    }

    private void setDatafromDb() {

        final ProgressDialog pDialog;
        pDialog = new ProgressDialog(Sec_Farsi_poems.this);
        pDialog.setMessage("در حال بارگیری اطلاعات ...");
        pDialog.setCancelable(false);
        pDialog.show();

        database = new Database(getApplicationContext());
        database.useable();
        database.open();

        sectionCursor = database.load_from_section();

        if (sectionCursor.getCount() == 0) {
            Toast.makeText(getApplicationContext(), "No Data Is Show", Toast.LENGTH_LONG).show();
        } else {
            while (sectionCursor.moveToNext()) {
                secF.add(new SecFarsiPoem(sectionCursor.getString(0),
                        sectionCursor.getString(1),
                        sectionCursor.getString(2),
                        sectionCursor.getString(3)));
            }
        }


        adapter.notifyDataSetChanged();


        pDialog.dismiss();


    }


    private void setData() {
        final String url = BuildConfig.Apikey_SectionList;

        final ProgressDialog pDialog;
        pDialog = new ProgressDialog(Sec_Farsi_poems.this);
        pDialog.setMessage("در حال بارگیری اطلاعات ...");
        pDialog.setCancelable(false);
        pDialog.show();

        Response.Listener<JSONArray> listener = new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {

                try {
                    Database db = new Database(getApplicationContext());
                    db.writable();
                    db.open();

                    for (int i = 0; i < response.length(); i++) {

                        JSONObject jsonObject = response.getJSONObject(i);
                        String id = jsonObject.getString("id");
                        String title = jsonObject.getString("title");
                        String count = jsonObject.getString("count");
                        String ordering = jsonObject.getString("ordering");
                        if (!db.check_section_exist(id)) {
                            db.add_to_section(id, title, count, ordering, getApplicationContext());
                        }

                        secF.add(new SecFarsiPoem(id, title, count, ordering));
                    }
                    adapter.notifyDataSetChanged();
                    db.close();

                } catch (JSONException e) {
                    e.printStackTrace();
                }
                pDialog.dismiss();
            }
        };

        Response.ErrorListener errorListener = new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
//                Toast.makeText(getApplicationContext(), "متاسفانه ارتباط با سرور برقرار نشد ممکن است مشکل از قطعی اینترنت شما باشد یا شلوغ بودن سرور", Toast.LENGTH_LONG).show();
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
