package com.condorcet.demoretrofit;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity {
    private EditText edprenom, ednom, ednumcli;
    private Button add;
    static final String BASE_URL = "https://demomipo.herokuapp.com/";

    WSInterface ws;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ednumcli = (EditText) findViewById(R.id.idnum);
        ednom = (EditText) findViewById(R.id.ednom);
        edprenom = (EditText) findViewById(R.id.edprenom);
        add=(Button)findViewById(R.id.idadd);


        Gson gson = new GsonBuilder()
                .setLenient()
                .create();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();

        ws = retrofit.create(WSInterface.class);

    }

    public void liste(View v) {

      Call<List<Client>> callAllclis = ws.getAllClients();
        ;
        Callback<List<Client>> cbliste = new Callback<List<Client>>() {
            @Override
            public void onResponse(Call<List<Client>> call, Response<List<Client>> response) {
                if (response.isSuccessful()) {
                    List<Client> lc = response.body();
                    StringBuilder infos = new StringBuilder("");
                    for (Client cl : lc) {
                        infos.append(cl.toString() + "\n");
                    }
                    Toast.makeText(getApplicationContext(), infos, Toast.LENGTH_LONG).show();
                } else {
                    StringBuilder error = new StringBuilder("erreur \n");
                    try {

                        error.append(response.errorBody().string());
                    } catch (Exception e) {
                        error.append("récupération impossible");
                    }
                    Toast.makeText(getApplicationContext(), error, Toast.LENGTH_LONG).show();

                }
            }

            @Override
            public void onFailure(Call<List<Client>> call, Throwable t) {
                String err = t.getMessage();
                Toast.makeText(getApplicationContext(), err, Toast.LENGTH_LONG).show();
            }
        };
        callAllclis.enqueue(cbliste);
    }

    public void add(View v) {
        add.setEnabled(false);
       String prenom = edprenom.getText().toString();
        String nom = ednom.getText().toString();
        final Client cli = new Client(0, prenom, nom);



        Call<Client> callAddcli = ws.postClient(cli);
        Callback<Client> cbadd = new Callback<Client>() {
            @Override
            public void onResponse(Call<Client> call, Response<Client> response) {

                if(response.isSuccessful()) {
                    int nid = 0;
                    Client cl = response.body();
                    nid = cl.getId();
                    ednumcli.setText(""+nid);

                 }
                else  Toast.makeText(getApplicationContext(), "erreur lors de la création du client", Toast.LENGTH_LONG).show();
                add.setEnabled(true);
            }

            @Override
            public void onFailure(Call<Client> call, Throwable t) {
                String err = t.getMessage();
                Toast.makeText(getApplicationContext(), err, Toast.LENGTH_LONG).show();

            }
        };

        callAddcli.enqueue(cbadd);
    }


    public void rechId(View v) {
       int id = Integer.parseInt(ednumcli.getText().toString());
        Call<Client> callRechid = ws.getClientsById(id);

        Callback<Client> cbid = new Callback<Client>() {
            @Override
            public void onResponse(Call<Client> call, Response<Client> response) {
                if(response.isSuccessful()) {
                    Client cli = response.body();
                    edprenom.setText(cli.getPrenom());
                    ednom.setText(cli.getNom());
                }
                else  Toast.makeText(getApplicationContext(),"client introuvable", Toast.LENGTH_LONG).show();
            }

            @Override
            public void onFailure(Call<Client> call, Throwable t) {
                String err = t.getMessage();
                Toast.makeText(getApplicationContext(), err, Toast.LENGTH_LONG).show();
            }
        };
        callRechid.enqueue(cbid);

    }

    public void gosec(View v){
        Intent i = new Intent(MainActivity.this,Main2Activity.class);
        startActivity(i);
    }


}
