package com.emam8.emam8_universal.services;

import com.emam8.emam8_universal.Model.Poem_retro;

import java.util.Map;

import retrofit2.Call;
import retrofit2.http.HeaderMap;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface Load_poems {
    String Base_Url="https://emam8.com/api/emama8_apps/";
    @Headers("Content-type: application/json")
    @POST("load_poem")
    Call<Poem_retro> load_article(
            @HeaderMap Map<String, String> headers,
//            @Path("load_poem")String load_poem,
            @Query("article_id") String article_id,
            @Query("app_name") String app_name,
            @Query("version") String version,
            @Query("api-type") String type

    );
}
