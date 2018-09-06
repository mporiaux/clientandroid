package com.condorcet.demoretrofit;

/**
 * Created by Michel on 04-02-18.
 */

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;


public interface WSInterfaceSec {


    @POST("oauth/token?grant_type=password")
    Call<AuthTokenInfo> getToken(@Header("Authorization") String header, @Header("Accept") String accept, @Query("username") String username, @Query("password") String password);

    @GET("Client/{id}")
    Call<Client> getUserById(@Path("id") int id, @Query("access_token") String atk);
}
