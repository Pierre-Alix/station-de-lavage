package com.mycompany.station;



/**
 * EUGENE Clément
 * BOMET Pierre-Alix
 * Projet Station de Lavage
 */

public class Client {
    private int numero;
    private String nom;
    private String telephone;
    private String email;

    // Constructeur (sans spécifier le mail)
    public Client(int numero, String nom, String telephone) {
        this.numero = numero;
        this.nom = nom;
        this.telephone = telephone;
    }

    // Constructeur (avec le mail)
    public Client(int numero, String nom, String telephone, String email) {
        this(numero, nom, telephone);
        this.email = email;
    }

    // Compare deux clients pour le tri par nom (ordre lexicographique)
    public boolean placerApres(Client autre) {
        return this.nom.compareToIgnoreCase(autre.nom) > 0;
    }

    // Getters
    public String getNom() { return nom; }
    public String getTelephone() { return telephone; }
    public int getNumero() { return numero;  }

    @Override
    public String toString() {
        String info = "Client n°" + numero + " : " + nom + " (Tel: " + telephone + ")";
        if (email != null) info += " [Email: " + email + "]";
        return info;
    }

    // Méthode pour le format fichier : numero:nom:tel:email
    public String versFichier() {
        String ligne = numero + ":" + nom + ":" + telephone;
        if (email != null) {
            ligne += ":" + email;
        }
        return ligne;
    }

}
