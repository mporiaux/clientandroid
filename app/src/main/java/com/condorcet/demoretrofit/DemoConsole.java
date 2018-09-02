package com.condorcet.demoretrofit;

/**
 * Created by Michel on 07-02-18.
 */

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.List;
import java.util.Scanner;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by Michel on 07-02-18.
 */

public class DemoConsole {
    static String BASE_URL = "https://demomipo.herokuapp.com/";
    static WSInterface ws;
    static Scanner sc = new Scanner(System.in);

    public static void rechid() {
        Scanner sc = new Scanner(System.in);
        System.out.println("numéro de client : ");
        int nc = sc.nextInt();

        Call<Client> callRechid = ws.getClientsById(nc);

        Callback<Client> cbid = new Callback<Client>() {
            @Override
            public void onResponse(Call<Client> call, Response<Client> response) {
                if(response.isSuccessful()) {
                    Client cli = response.body();
                    System.out.println(cli.getPrenom());
                    System.out.println(cli.getNom());
                }
                else System.out.println("client introuvable");
            }

            @Override
            public void onFailure(Call<Client> call, Throwable t) {
                String err = t.getMessage();
                System.out.println(err);
            }
        };
        callRechid.enqueue(cbid);

    }



    public static void liste() {
        Call<List<Client>> callAllclis = ws.getAllClients();

        Callback<List<Client>> cbliste = new Callback<List<Client>>() {
            @Override
            public void onResponse(Call<List<Client>> call, Response<List<Client>> response) {
                if (response.isSuccessful()) {
                    List<Client> lc = response.body();
                    for (Client cl : lc) {
                        System.out.println(cl);
                    }
                } else {
                    StringBuilder error = new StringBuilder("erreur \n");
                    try {

                        error.append(response.errorBody().string());
                    } catch (Exception e) {
                        error.append("récupération impossible");
                    }
                    System.out.println(error);

                }
            }

            @Override
            public void onFailure(Call<List<Client>> call, Throwable t) {
                String err = t.getMessage();
                System.out.println(err);
            }
        };

        callAllclis.enqueue(cbliste);
    }

    public static void nouvcli() {
        System.out.println("prenom :");
        String prenom = sc.nextLine();
        System.out.println("nom :");
        String nom = sc.nextLine();
        System.out.println("id :");
        int id = sc.nextInt();
        Client cli = new Client(id, prenom, nom);

        Call<Client> callAddcli = ws.postClient(cli);
        Callback<Client> cbadd = new Callback<Client>() {
            @Override
            public void onResponse(Call<Client> call, Response<Client> response) {
                if(response.isSuccessful()) {
                    int nid = 0;
                    Client cl = response.body();
                    nid = cl.getId();
                    System.out.println("idclient = " + nid);
                }
                else System.out.println("erreur lors de la création du client");
            }

            @Override
            public void onFailure(Call<Client> call, Throwable t) {
                String err = t.getMessage();
                System.out.println(err);

            }
        };

        callAddcli.enqueue(cbadd);

    }

    private static void maj() {
    }

    private static void delcli() {
    }

    private static void dellall() {
    }


    public static void main(String[] args) {


        Gson gson = new GsonBuilder()
                .setLenient()
                .create();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();

        ws = retrofit.create(WSInterface.class);
        int ch;

        do {
            System.out.println("1.création\n2.recherche sur id\n3.liste des clients\n4.mise à jour\n5.effacer un client\n6.effacer tous les clients\n7.fin\n");
            System.out.println("choix :");
            ch = sc.nextInt();
            sc.skip("\n");
            switch (ch) {
                case 1:
                    nouvcli();
                    break;
                case 2:
                    rechid();
                    break;
                case 3:
                    liste();
                    break;
                case 4:
                    maj();
                    break;
                case 5:
                    delcli();
                    break;
                case 6:
                    dellall();
                    break;
                case 7:break;
                default:
                    System.out.println("choix incorrect");
            }

        } while (ch != 7);
    }




}

