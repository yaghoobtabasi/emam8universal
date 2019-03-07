package com.emam8.emam8_universal.services;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Url;


public interface FileDownloadClient {


    @GET
    Call<ResponseBody> downloadFile(@Url String url);
}
