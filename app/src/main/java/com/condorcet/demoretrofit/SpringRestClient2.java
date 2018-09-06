package com.condorcet.demoretrofit;

//import android.util.Base64;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import org.apache.commons.codec.binary.Base64;


import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


/**
 * Created by Michel on 18-08-18.
 */

public class SpringRestClient2 {
    public static void main(String[] args) {
        String user="my-trusted-client";
        String pass="secret";
        String BASE_URL = "https://springsecuritymipo2.herokuapp.com/";
        WSInterfaceSec ws;

        String base=user+":"+pass;
        final String passHeader = "Basic "+new String(Base64.encodeBase64(base.getBytes()));


        Gson gson = new GsonBuilder()
                .setLenient()
                .create();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();

        ws = retrofit.create(WSInterfaceSec.class);
        System.out.println("clé = "+passHeader);
        final AuthTokenInfo tk=new AuthTokenInfo();
        Call<AuthTokenInfo> callToken = ws.getToken(passHeader,"application/json","toto","papa");

        Callback<AuthTokenInfo> cbtok = new Callback<AuthTokenInfo>() {
            @Override
            public void onResponse(Call<AuthTokenInfo> call, Response<AuthTokenInfo> response) {
                if (response.isSuccessful()) {
                    AuthTokenInfo tkr = response.body();
                    System.out.println(tkr);
                    tk.setAccess_token(tkr.getAccess_token());
                } else System.out.println("requête token infructueuse"+response.headers());
            }
            @Override
            public void onFailure(Call<AuthTokenInfo> call, Throwable t) {
                String err = t.getMessage();
                System.out.println("erreur token  :"+err);
            }
        };
        callToken.enqueue(cbtok);

       try{
            Thread.sleep(4000);
        }
        catch (Exception e){}

        Call<Client> callRechid = ws.getUserById(1,tk.getAccess_token());

        Callback<Client> cbid = new Callback<Client>() {
            @Override
            public void onResponse(Call<Client> call, Response<Client> response) {
                if (response.isSuccessful()) {
                    Client cli = response.body();
                    System.out.println(cli);
                } else System.out.println("requête infructueuse");
            }
            @Override
            public void onFailure(Call<Client> call, Throwable t) {
                String err = t.getMessage();
                System.out.println("erreur requete"+err);
            }
        };
        callRechid.enqueue(cbid);
    }
}
