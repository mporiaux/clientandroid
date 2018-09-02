package mavenrestclient2;

/**
 * Created by Michel on 04-02-18.
 */

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Path;


public interface WSInterface {


    @GET("user/{id}")
    Call<User> getUserById(@Header("header") String header, @Path("id") int id);


}
