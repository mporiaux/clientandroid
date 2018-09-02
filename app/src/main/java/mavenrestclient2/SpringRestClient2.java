package mavenrestclient2;

import android.util.Base64;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

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
        String BASE_URL = "https://springbootmipo2.herokuapp.com/";
        WSInterface ws;

        String base=user+":"+pass;
       String passHeader="Basic "+ Base64.encodeToString(base.getBytes(),Base64.NO_WRAP);
      // String passHeader="Basic "+ new String(Base64.encodeBase64(base.getBytes()));

        Gson gson = new GsonBuilder()
                .setLenient()
                .create();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();

        ws = retrofit.create(WSInterface.class);

        Call<User> callRechid = ws.getUserById(passHeader,1);

        Callback<User> cbid = new Callback<User>() {
            @Override
            public void onResponse(Call<User> call, Response<User> response) {
                User cli = response.body();
                System.out.println(cli);
               }

            @Override
            public void onFailure(Call<User> call, Throwable t) {
                String err = t.getMessage();
                System.out.println(err);
            }
        };
        callRechid.enqueue(cbid);
    }
}
