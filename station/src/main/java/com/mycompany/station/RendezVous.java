/**
 * EUGENE Clément
 * BOMET Pierre-Alix
 * Projet Station de Lavage
 */


package com.mycompany.station;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

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
        // On définit le format souhaité : Jour-Mois-Année Heure:Minutes
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy - HH:mm");
        String dateFormatee = creneau.format(formatter);
        return "RDV le " + dateFormatee + " pour " + client.getNom() + " | Prix: " + prix + " euros";
    }

    public Client getClient() {
        return client;
    }

    public String versFichier() {
        // On utilise System.lineSeparator() pour le saut de ligne
        return creneau.toString() + System.lineSeparator() +
                client.getNumero() + System.lineSeparator() +
                prestation.versFichier();
    }
}
