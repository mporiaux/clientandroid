package com.condorcet.demoretrofit;

/**
 * Created by Michel on 04-02-18.
 */

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;


public interface WSInterface {

    @POST("Client/")
    Call<Client> postClient(@Body Client cli);

    @GET("Client/{id}")
    Call<Client> getClientsById(@Path("id") int id);

    @GET("Client/")
    Call<List<Client>> getAllClients();

    @PUT("Client/")
    Call<Client> update(@Body Client cli);

    @DELETE("Client/{id}")
    Call<Client> delClientsById(@Path("id") int id);

    @DELETE("Client/}")
    Call<Void> delAllClients(@Path("id") int id);


}
