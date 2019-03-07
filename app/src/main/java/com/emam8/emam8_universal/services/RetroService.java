package com.emam8.emam8_universal.services;


import com.emam8.emam8_universal.Model.AuthenticationResponseModel;
import com.emam8.emam8_universal.Model.RefreshTokenRequestModel;
import com.emam8.emam8_universal.Model.SignInRequestModel;
import com.emam8.emam8_universal.Model.SignUpRequestModel;
import com.emam8.emam8_universal.Model.TokenModel;
import com.emam8.emam8_universal.Model.UserModel;
import com.emam8.emam8_universal.Model.User_info;

import java.util.List;
import java.util.Map;

import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.HeaderMap;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.PartMap;
import retrofit2.http.Path;
import retrofit2.http.Query;

/**
 * the interface implements REST API routes
 */
public interface RetroService {

//    @POST("tweet")
//    Call<TweetModel> createNewTweet(@Body TweetModel tweetModel);
//
//    @GET("tweet")
//    Call<List<TweetModel>> getTweets();
//
//    @GET("tweet/{id}")
//    Call<TweetModel> getTweetById(@Path("id") String tweetId);
//
//    @PUT("tweet/{id}")
//    Call<TweetModel> updateTweetById(@Path("id") String tweetId, @Body TweetModel tweetModel);
//
//    @DELETE("tweet/{id}")
//    Call<OperationResultModel> deleteTweetById(@Path("id") String tweetId);

    @POST("signup")
    Call<AuthenticationResponseModel> signUp(
            @HeaderMap Map<String , String> headers,
            @Query("mobile")String mobile,
            @Query("password")String password
    );

    @POST("login")
    Call<AuthenticationResponseModel> signIn(
            @HeaderMap Map<String , String> headers,
            @Query("username")String username,
            @Query("password")String password,
            @Query("app_name")String app_name,
            @Query("api-type")String type

    );

    @GET("auth/user")
    Call<User_info> user_info(
            @HeaderMap Map<String , String> headers

    );



    @PUT("user/profile")
    Call<UserModel> updateUserProfile(@Body UserModel userModel);

    @Multipart
    @POST("user/profile/image")
    Call<UserModel> uploadUserProfileImage(@Header("Authorization") String authHeader, @PartMap Map<String, RequestBody> map);


    @POST("refreshtoken")
    Call<TokenModel> getRefreshToken(@Body RefreshTokenRequestModel refreshTokenRequestModel);

//    @GET("tweet")
//    Call<List<TweetModel>> getTweetsByFeel(@Query("feel") String feel);
}
