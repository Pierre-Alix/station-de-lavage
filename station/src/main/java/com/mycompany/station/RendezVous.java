/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.station;

import java.time.LocalDateTime;

public class RendezVous {
    private Client client;
    private Prestation prestation;
    private LocalDateTime creneau;
    private double prix;

    public RendezVous(Client client, Prestation prestation, LocalDateTime creneau) {
        this.client = client;
        this.prestation = prestation;
        this.creneau = creneau;
        this.prix = prestation.nettoyage(); // Calcul du prix
    }

    @Override
    public String toString() {
        return "RDV le " + creneau + " pour " + client.getNom() + " | Prix: " + prix + "€";
    }
}
