/**
 * EUGENE Clément
 * BOMET Pierre-Alix
 * Projet Station de Lavage
 */

package com.mycompany.station;

//import java.time.LocalDate;
//import java.time.LocalDateTime;
//import java.time.LocalTime;
import java.util.Scanner;


/**
 * Classe de test pour la 1ère partie du projet.
 */
public class Main {
    public static void main(String[] args) {
        // 1. Création de l'établissement
        /*Etablissement station = new Etablissement("Efrei Clean Station");

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
        System.out.println("Méthode de recherche prête pour l'interaction utilisateur.");*/

        Etablissement station = new Etablissement("Station Efrei");

        // 1. CHARGEMENT DES DONNÉES AU DÉMARRAGE 
        System.out.println("--- Démarrage ---");
        station.depuisFichierClients();
        station.depuisFichierRDV();

        // 2. MENU UTILISATEUR 
        Scanner sc = new Scanner(System.in);
        int choix = 0;

        while (choix != 4) {
            System.out.println("\nMENU PRINCIPAL");
            System.out.println("1. Planifier un rendez-vous");
            System.out.println("2. Afficher le planning d'un jour"); // Appelle tes méthodes d'affichage
            System.out.println("3. Afficher les infos d'un client");
            System.out.println("4. Quitter et Sauvegarder");
            System.out.print("Choix : ");

            choix = sc.nextInt();

            switch (choix) {
                case 1:
                    station.planifier();
                    break;
                case 2:
                    System.out.println("Quel jour afficher ? (0 = Aujourd'hui, 1 = Demain...)");
                    int j = sc.nextInt();
                    // On affiche le planning du jour demandé
                    station.afficherPlanning(java.time.LocalDate.now().plusDays(j));
                    break;
                case 3:
                    System.out.print("Nom ou téléphone : ");
                    String rech = sc.next();
                    station.afficherClient(rech);
                    break;
                case 4:
                    System.out.println("Fermeture...");
                    break;
                default:
                    System.out.println("Choix invalide.");
            }
        }

        // 3. SAUVEGARDE AVANT DE QUITTER 
        station.versFichierClients();

        // Attention : versFichierRDV est en mode "Ajout" (append),
        // donc si tu testes plusieurs fois, le fichier va grossir.
        // Tu peux effacer le fichier "rdv.txt" manuellement pour remettre à zéro.
        station.versFichierRDV();

        System.out.println("Au revoir !");


    }

}
