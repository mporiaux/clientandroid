package com.condorcet.demoretrofit;

import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import org.apache.commons.codec.binary.Base64;
import retrofit2.Call;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class Main2Activity extends AppCompatActivity {
    private TextView tv;

    private ProgressDialog pgd=null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        tv=(TextView) findViewById(R.id.idres);
    }


    public void goMethod(View v){

         MyWS mws = new MyWS(Main2Activity.this);
        pgd=new ProgressDialog(Main2Activity.this);
        pgd.setMessage("connexion en cours");//à internatioanliser
        pgd.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        pgd.setIndeterminate(true);
        pgd.show();

        mws.execute(1);

    }



    class MyWS extends AsyncTask<Integer,Integer,Boolean> {//types de doinbackground(arg0), onProgressUpdate(v),retour de doInBackground
        private String resultat=null;

        public MyWS(Main2Activity pActivity) {

            link(pActivity);
            // TODO Auto-generated constructor stub
        }

        private void link(Main2Activity pActivity) {
            // TODO Auto-generated method stub


        }

        protected void onPreExecute(){
            super.onPreExecute();
        }

        @Override
        protected Boolean doInBackground(Integer... arg0) {

            int idrech = arg0[0];
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

           // Call<AuthTokenInfo> call = ws.getToken(passHeader,"application/json","toto","papa");
            Call<AuthTokenInfo> call = ws.getToken(passHeader,"application/json","lili","maman");
            try {
                Response<AuthTokenInfo> reptk= call.execute();
                if(reptk.isSuccessful()) {
                    atk = reptk.body();
                }
                else throw new Exception("status :"+reptk.code());
            }
            catch (Exception e){
                resultat ="erreur "+e;
                return false;
            }



            Call<Client> callRechid = ws.getUserById(idrech,atk.getAccess_token());
            try {

                Response<Client> rep= callRechid.execute();
                if(rep.isSuccessful()) {
                    Client cli = rep.body();
                    resultat = cli.toString();
                }
                else throw new Exception("status :"+rep.code());
            }
            catch (Exception e){
                resultat="erreur "+e;
                return false;
            }

            return true;
        }

        protected  void onProgressUpdate(Integer ... v){

        }


        protected void onPostExecute(Boolean result){
            pgd.dismiss();
            super.onPostExecute(result);
            if (result) {
                 tv.setText(resultat);
            }
            else Toast.makeText(Main2Activity.this, resultat,Toast.LENGTH_SHORT).show();
        }

    }



}
