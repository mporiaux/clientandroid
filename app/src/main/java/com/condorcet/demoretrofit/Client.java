package com.condorcet.demoretrofit;


public class Client {
    private int id;
    private String prenom;
    private String nom;

    public Client() {
    }

    public Client(int idclient, String prenom, String nom) {
        this.setIdclient(idclient);
        this.setPrenom(prenom);
        this.setNom(nom);

    }

    public int getId() {
        return id;
    }

    public void setIdclient(int id) {
        this.id = id;
    }


    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getPrenom() {
        return prenom;
    }

    public void setPrenom(String prenom) {
        this.prenom = prenom;
    }


    @Override
    public String toString() {
        return "Client{" +
                "idclient=" + getId() +
                ", prenom='" + getPrenom() + '\'' +
                ", nom='" + getNom() + '\'' +
                '}';
    }
}
