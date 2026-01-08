/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
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

    // Compare deux clients pour le tri par nom (ordre lexicographique) [cite: 39, 40]
    public boolean placerApres(Client autre) {
        return this.nom.compareToIgnoreCase(autre.nom) > 0;
    }

    // Getters
    public String getNom() { return nom; }
    public String getTelephone() { return telephone; }

    @Override
    public String toString() {
        String info = "Client n°" + numero + " : " + nom + " (Tel: " + telephone + ")";
        if (email != null) info += " [Email: " + email + "]";
        return info;
    }
}