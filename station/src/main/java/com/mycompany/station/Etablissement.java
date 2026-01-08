/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

/**
 * EUGENE Clément
 * BOMET Pierre-Alix
 * Projet Station de Lavage
 */

package com.mycompany.station;

import java.time.*;
import java.util.Scanner;

public class Etablissement {
    private String nom;
    private Client[] clients;
    private int nbClients;
    private RendezVous[][] planning; // Lignes: créneaux (16), Colonnes: jours (7)

    public Etablissement(String nom) {
        this.nom = nom;
        this.clients = new Client[100]; // Limite fixée à 100 clients 
        this.nbClients = 0;
        
        // 8h de service (10h-18h) --> 16 créneaux de 30min par jour
        this.planning = new RendezVous[16][7];
    }

    // Méthode pour rechercher un client existant 
    public Client rechercher(String nom, String tel) {
        for (int i = 0; i < nbClients; i++) {
            // on n'a pas vu equalsIgnoreCase en TD, mais on l'a rajouté pour comparer les noms sans se soucier des majuscules/minuscules
            // cela permet d'éviter les erreurs
            if (clients[i].getNom().equalsIgnoreCase(nom) && clients[i].getTelephone().equals(tel)) {
                return clients[i];
            }
        }
        return null;
    }

    // Ajouter un client avec tri automatique 
    public Client ajouter(String nom, String tel, String email) {
        if (nbClients >= 100) return null;
        Client nouveau = (email == null) ? new Client(nbClients+1, nom, tel) : new Client(nbClients+1, nom, tel, email);
        
        // Insertion triée 
        int i = nbClients - 1;
        while (i >= 0 && nouveau.placerApres(clients[i]) == false) {
            clients[i + 1] = clients[i];
            i--;
        }
        clients[i + 1] = nouveau;
        nbClients++;
        return nouveau;
    }

    // Recherche de créneau par jour 
    public LocalDateTime rechercherCreneauParJour(LocalDate jour) {
        Scanner sc = new Scanner(System.in);
        System.out.println("Disponibilités pour " + jour + " (10h-18h) :");
        for (int i = 0; i < 16; i++) {
            LocalTime heure = LocalTime.of(10, 0).plusMinutes(i * 30);
            System.out.println(i + " : " + heure);
        }
        System.out.print("Choisissez un numéro de créneau : ");
        int choix = sc.nextInt();
        return LocalDateTime.of(jour, LocalTime.of(10, 0).plusMinutes(choix * 30));
    }

    // Ajout un RDV pour Prestation Express
    public RendezVous ajouterRDVExpress(Client c, LocalDateTime dt, char cat, boolean interieur) {
        Prestation p = new PrestationExpress(cat, interieur);
        RendezVous rdv = new RendezVous(c, p, dt);
        
        // On range le RDV dans le planning (Logique de rangement à adapter selon vos besoins)
        int ligne = calculerLigne(dt.toLocalTime());
        int colonne = dt.getDayOfWeek().getValue() - 1; // 0 pour Lundi, etc.
        planning[ligne][colonne] = rdv;
        
        return rdv;
    }

    // Ajout un RDV pour Prestation Véhicule Sale
    public RendezVous ajouterRDVSale(Client c, LocalDateTime dt, char cat) {
        Prestation p = new PrestationSale(cat);
        RendezVous rdv = new RendezVous(c, p, dt);
        
        int ligne = calculerLigne(dt.toLocalTime());
        int colonne = dt.getDayOfWeek().getValue() - 1;
        planning[ligne][colonne] = rdv;
        
        return rdv;
    }

    // Ajouter un RDV pour Prestation Véhicule Très Sale
    public RendezVous ajouterRDVTresSale(Client c, LocalDateTime dt, char cat, int typeSalissure) {
        Prestation p = new PrestationTresSale(cat, typeSalissure);
        RendezVous rdv = new RendezVous(c, p, dt);
        
        int ligne = calculerLigne(dt.toLocalTime());
        int colonne = dt.getDayOfWeek().getValue() - 1;
        planning[ligne][colonne] = rdv;
        
        return rdv;
    }
    
    /**
     * Méthode utilitaire pour trouver l'indice du tableau (0 à 15) 
     * en fonction de l'heure (10h00 = 0, 10h30 = 1, etc.)
     */
    private int calculerLigne(LocalTime heure) {
        long minutesDepuisDebut = java.time.Duration.between(LocalTime.of(10, 0), heure).toMinutes();
        return (int) (minutesDepuisDebut / 30);
    }
}
