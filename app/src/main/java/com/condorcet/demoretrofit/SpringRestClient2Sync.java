package com.condorcet.demoretrofit;

//import android.util.Base64;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import org.apache.commons.codec.binary.Base64;

import restclient2.AuthTokenInfo;
import restclient2.Client;
import restclient2.WSInterfaceSec;
import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


/**
 * Created by Michel on 18-08-18.
 */

public class SpringRestClient2Sync {
    public static void main(String[] args) {
        String user="my-trusted-client";
        String pass="secret";
        String BASE_URL = "https://springsecuritymipo2.herokuapp.com/";
        restclient2.WSInterfaceSec ws;

        String base=user+":"+pass;
        final String passHeader = "Basic "+new String(Base64.encodeBase64(base.getBytes()));
       //String passHeader="Basic "+ Base64.encodeToString(base.getBytes(),Base64);
      //String passHeader="Basic "+ new String(Base64.encodeBase64(base.getBytes()));

        Gson gson = new GsonBuilder()
                .setLenient()
                .create();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();

        ws = retrofit.create(WSInterfaceSec.class);
        System.out.println("clé = "+passHeader);
        AuthTokenInfo atk=null;

        Call<AuthTokenInfo> call = ws.getToken(passHeader,"application/json","toto","papa");
        try {
             atk = call.execute().body();
        }
        catch (Exception e){
            System.out.println("erreur "+e);
        }



        Call<Client> callRechid = ws.getUserById(1,atk.getAccess_token());
        try {
            Client cli = callRechid.execute().body();
            System.out.println(cli);
        }
        catch (Exception e){
            System.out.println("erreur "+e);
        }

    }
}
