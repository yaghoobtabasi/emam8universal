package com.emam8.emam8_universal.App;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.emam8.emam8_universal.BuildConfig;

public class Article_functions {


    public static void inc_counter(String id)
    {
        String url=BuildConfig.Apikey_Inc_Counter+id;
        Response.Listener<String> listener=new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
//                Toast.makeText(getApplicationContext(),response,Toast.LENGTH_SHORT).show();
            }
        };

        Response.ErrorListener errorListener=new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
//                Toast.makeText(getApplicationContext(),"Error",Toast.LENGTH_SHORT).show();
            }
        };

        StringRequest request=new StringRequest(Request.Method.GET,url,listener,errorListener) ;

        AppController.getInstance().addToRequestQueue(request);
    }
}
