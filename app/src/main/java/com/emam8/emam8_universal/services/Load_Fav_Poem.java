package com.emam8.emam8_universal.services;

import com.emam8.emam8_universal.BuildConfig;
import com.emam8.emam8_universal.Model.Poem_fav;
import com.emam8.emam8_universal.Model.Poem_retro;

import java.util.Map;

import retrofit2.Call;
import retrofit2.http.DELETE;
import retrofit2.http.HeaderMap;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Query;

public interface Load_Fav_Poem {
    String Base_Url= BuildConfig.ApiKey_baseUrl_Apps;
    @Headers("Content-type: application/json")
    @POST(BuildConfig.ApiKey_LoadFav)
    Call<Poem_fav> fav_article(
            @HeaderMap Map<String, String> headers,
            @Query("article_id") String article_id,
            @Query("user_id") String user_id,
            @Query("mode") String mode,
            @Query("app_name") String app_name,
            @Query("version") String version,
            @Query("api-type") String type

    );

    @POST(BuildConfig.ApiKey_LoadFav)
    Call<Poem_fav> del_fav_article(
            @HeaderMap Map<String, String> headers,

            @Query("article_id") String article_id,
            @Query("user_id") String user_id,
            @Query("mode") String mode,
            @Query("app_name") String app_name,
            @Query("version") String version,
            @Query("api-type") String type

    );

}
