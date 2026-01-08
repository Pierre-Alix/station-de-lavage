package com.mycompany.station;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;



/**
 * Classe de test pour la 1ère partie du projet.
 */
public class Main {
    public static void main(String[] args) {
        // 1. Création de l'établissement
        Etablissement station = new Etablissement("Efrei Clean Station");

        // 2. Test de la gestion des clients
        System.out.println("--- Test Clients ---");
        // Ajout d'un client sans email
        Client c1 = station.ajouter("Dupont", "0601020304", null);
        // Ajout d'un client avec email
        Client c2 = station.ajouter("Martin", "0708091011", "martin@email.com");
        // Ajout d'un client pour vérifier le tri (Abeille avant Dupont)
        Client c3 = station.ajouter("Abeille", "0102030405", null);

        System.out.println("Client trouvé : " + station.rechercher("Dupont", "0601020304"));

        // 3. Test des prestations et calcul des prix
        System.out.println("\n--- Test Prix Prestations ---");
        
        // Test Express - Catégorie A - Avec nettoyage intérieur (20 lav + 10 séch + 30 int = 60€)
        Prestation p1 = new PrestationExpress('A', true);
        System.out.println("Prix Express A (avec intérieur) : " + p1.nettoyage() + "€");

        // Test Sale - Catégorie B (Lavage +50%, Séchage +5%, Prélavage +50% + Intérieur inclus)
        Prestation p2 = new PrestationSale('B');
        System.out.println("Prix Sale B : " + p2.nettoyage() + "€"); 

        // 4. Test des rendez-vous
        System.out.println("\n--- Test Rendez-vous ---");
        
        // Création d'un créneau : demain à 14h30
        LocalDate demain = LocalDate.now().plusDays(1); 
        LocalDateTime créneau = LocalDateTime.of(demain, LocalTime.of(14, 30)); 

        // Ajout d'un RDV pour un véhicule Très Sale (catégorie C, salissure type 2)
        RendezVous rdv = station.ajouterRDVTresSale(c2, créneau, 'C', 2); 
        
        if (rdv != null) {
            System.out.println("Rendez-vous enregistré avec succès :");
            System.out.println(rdv.toString());
        }

        // 5. Simulation de la recherche de créneau par jour
        System.out.println("\n--- Test Recherche Créneau ---");
        // Cette méthode affichera la liste des heures et demandera une saisie
        // LocalDateTime rdvChoisi = station.rechercherCreneauParJour(demain);
        System.out.println("Méthode de recherche prête pour l'interaction utilisateur.");
    }
}