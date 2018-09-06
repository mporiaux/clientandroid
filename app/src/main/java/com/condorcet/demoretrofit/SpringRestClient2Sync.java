package com.condorcet.demoretrofit;

//import android.util.Base64;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import org.apache.commons.codec.binary.Base64;

import retrofit2.Call;
import retrofit2.Response;
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
        AuthTokenInfo atk=null;

        Call<AuthTokenInfo> call = ws.getToken(passHeader,"application/json","toto","papa");
        try {
            Response<AuthTokenInfo> reptk= call.execute();
            if(reptk.isSuccessful()) {
                atk = reptk.body();
            }
            else throw new Exception("status :"+reptk.code());
        }
        catch (Exception e){
            System.out.println("erreur "+e);
        }



        Call<Client> callRechid = ws.getUserById(1,atk.getAccess_token());
        try {
            Response<Client> rep= callRechid.execute();
            if(rep.isSuccessful()) {
                Client cli = rep.body();
                System.out.println(cli);
            }
            else throw new Exception("status :"+rep.code());
        }
        catch (Exception e){
            System.out.println("erreur "+e);
        }

    }
}
