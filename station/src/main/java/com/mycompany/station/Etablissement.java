
/**
 * EUGENE Clément
 * BOMET Pierre-Alix
 * Projet Station de Lavage
 */

package com.mycompany.station;

import java.time.*;
import java.util.Scanner;
import java.io.*;
import java.time.temporal.ChronoUnit;

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

        int ligne = calculerLigne(dt.toLocalTime());

        // On calcule l'écart en jours par rapport à aujourd'hui
        long diff = LocalDate.now().until(dt.toLocalDate(), java.time.temporal.ChronoUnit.DAYS);
        int colonne = (int) diff;

        // Sécurité pour ne pas planter si la date est hors du tableau
        if (colonne >= 0 && colonne < 7) {
            planning[ligne][colonne] = rdv;
        }

        return rdv;
    }

    // Ajout un RDV pour Prestation Véhicule Sale
    public RendezVous ajouterRDVSale(Client c, LocalDateTime dt, char cat) {
        Prestation p = new PrestationSale(cat);
        RendezVous rdv = new RendezVous(c, p, dt);

        int ligne = calculerLigne(dt.toLocalTime());

        // CORRECTION ICI
        long diff = LocalDate.now().until(dt.toLocalDate(), java.time.temporal.ChronoUnit.DAYS);
        int colonne = (int) diff;

        if (colonne >= 0 && colonne < 7) {
            planning[ligne][colonne] = rdv;
        }

        return rdv;
    }

    // Ajouter un RDV pour Prestation Véhicule Très Sale
    public RendezVous ajouterRDVTresSale(Client c, LocalDateTime dt, char cat, int typeSalissure) {
        Prestation p = new PrestationTresSale(cat, typeSalissure);
        RendezVous rdv = new RendezVous(c, p, dt);

        int ligne = calculerLigne(dt.toLocalTime());

        // CORRECTION ICI
        long diff = LocalDate.now().until(dt.toLocalDate(), java.time.temporal.ChronoUnit.DAYS);
        int colonne = (int) diff;

        if (colonne >= 0 && colonne < 7) {
            planning[ligne][colonne] = rdv;
        }

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

    // Méthode demandée en Partie 2 - Question 1
    public void planifier() {
        Scanner sc = new Scanner(System.in);

        System.out.println("--- Planification ---");

        // 1. Identification du client
        System.out.print("Nom du client : ");
        String nom = sc.next();
        System.out.print("Telephone : ");
        String tel = sc.next();

        Client c = rechercher(nom, tel); // Utilise la méthode existante

        if (c == null) {
            System.out.println("Nouveau client détecté.");
            System.out.print("Email (tapez 'null' si aucun) : ");
            String email = sc.next();

            if (email.equals("null")) {
                email = null;
            }
            // Utilise la méthode ajouter existante
            c = ajouter(nom, tel, email);
            System.out.println("Client ajouté : " + c);
        } else {
            System.out.println("Client existant : " + c);
        }

        //2. Choix du créneau
        System.out.println("Pour quel jour souhaitez-vous le RDV ?");
        System.out.println("0 = Aujourd'hui");
        System.out.println("1 = Demain");
        System.out.println("...");
        System.out.print("Votre choix (0-6) : ");
        int decalageJours = sc.nextInt();

        LocalDate jourChoisi = LocalDate.now().plusDays(decalageJours);

        // Appel de lA méthode qui affiche les heures et demande le choix à l'utilisateur
        // Elle retourne un LocalDateTime prêt à l'emploi
        LocalDateTime dateHeure = rechercherCreneauParJour(jourChoisi);

        // 3. Choix de la prestation et ajout du RDV
        System.out.print("Catégorie du véhicule (A, B, C) : ");
        char cat = sc.next().toUpperCase().charAt(0); // Récupère le premier caractère

        System.out.println("Type de prestation : 1-Express, 2-Sale, 3-Tres Sale");
        int type = sc.nextInt();

        RendezVous rdv = null;

        if (type == 1) {
            System.out.print("Nettoyage intérieur (true/false) ? ");
            boolean interieur = sc.nextBoolean();
            // Appel de la méthode partie 1
            rdv = ajouterRDVExpress(c, dateHeure, cat, interieur);

        } else if (type == 2) {
            // Appel de la méthode partie 1
            rdv = ajouterRDVSale(c, dateHeure, cat);

        } else if (type == 3) {
            System.out.print("Type de salissure (entier) : ");
            int salissure = sc.nextInt();
            // Appel de la méthode partie 1
            rdv = ajouterRDVTresSale(c, dateHeure, cat, salissure);
        }

        // 4. Confirmation
        if (rdv != null) {
            System.out.println("Rendez-vous confirmé !");
            System.out.println(rdv.toString()); // Affiche le prix calculé
        } else {
            System.out.println("Erreur : impossible de créer le rendez-vous.");
        }
    }


    // ---------------------------------------------------------
    // PARTIE 2 - Question 2 : Méthodes d'affichage
    // ---------------------------------------------------------

    // 1. Afficher le planning pour un jour donné
    public void afficherPlanning(LocalDate jour) {
        LocalDate aujourdhui = LocalDate.now();
        // Calcule le nombre de jours d'écart (0 pour ajd, 1 pour demain, etc.)
        long diff = java.time.temporal.ChronoUnit.DAYS.between(aujourdhui, jour);

        if (diff < 0 || diff > 6) {
            System.out.println("Date hors du planning (doit être dans les 7 jours).");
            return;
        }

        int colonne = (int) diff;
        System.out.println("Planning du " + jour + " :");
        boolean vide = true;

        for (int ligne = 0; ligne < 16; ligne++) {
            if (planning[ligne][colonne] != null) {
                System.out.println(planning[ligne][colonne]);
                vide = false;
            }
        }

        if (vide) {
            System.out.println("Aucun rendez-vous pour ce jour.");
        }
    }

    // 2. Afficher le(s) client(s) par nom ou téléphone
    public void afficherClient(String saisie) {
        System.out.println("Recherche client pour : " + saisie);
        boolean trouve = false;

        for (int i = 0; i < nbClients; i++) {
            Client c = clients[i];
            // On vérifie si ça correspond au nom OU au téléphone
            if (c.getNom().equalsIgnoreCase(saisie) || c.getTelephone().equals(saisie)) {
                System.out.println(c);
                trouve = true;
            }
        }

        if (!trouve) {
            System.out.println("Aucun client trouvé.");
        }
    }

    // 3. Afficher les rendez-vous d'un client (par son numéro)
    public void afficherRDV(int numeroClient) {
        System.out.println("Rendez-vous pour le client n°" + numeroClient + " :");
        boolean trouve = false;

        // On parcourt tout le planning (toutes les heures, tous les jours)
        for (int j = 0; j < 7; j++) {         // Jours
            for (int i = 0; i < 16; i++) {    // Heures
                RendezVous rdv = planning[i][j];
                // Si la case n'est pas vide ET que c'est le bon client
                if (rdv != null && rdv.getClient().getNumero() == numeroClient) {
                    System.out.println(rdv);
                    trouve = true;
                }
            }
        }

        if (!trouve) {
            System.out.println("Aucun rendez-vous trouvé pour ce client.");
        }
    }

    // ---------------------------------------------------------
    // PARTIE 3 : Fichiers CLIENTS
    // ---------------------------------------------------------

    public void versFichierClients() {
        try {
            // false = on écrase le fichier pour le remettre à jour proprement
            FileWriter fw = new FileWriter("clients.txt", false);

            for (int i = 0; i < nbClients; i++) {
                fw.write(clients[i].versFichier() + System.lineSeparator());
            }

            fw.close();
            System.out.println("Sauvegarde clients terminée.");
        } catch (IOException e) {
            System.out.println("Erreur sauvegarde clients : " + e.getMessage());
        }
    }

    public void depuisFichierClients() {
        try {
            FileReader fr = new FileReader("clients.txt");
            BufferedReader br = new BufferedReader(fr);
            String ligne;

            // On remet le compteur à 0 pour recharger proprement
            nbClients = 0;

            while ((ligne = br.readLine()) != null) {
                String[] parts = ligne.split(":"); // Découpe la ligne 

                int num = Integer.parseInt(parts[0]);
                String nom = parts[1];
                String tel = parts[2];
                String email = (parts.length > 3) ? parts[3] : null;

                // On crée le client manuellement pour garder son numéro d'origine
                Client c = (email == null) ? new Client(num, nom, tel) : new Client(num, nom, tel, email);

                // On l'ajoute au tableau manuellement
                clients[nbClients] = c;
                nbClients++;
            }
            br.close();
            fr.close();
            System.out.println("Chargement clients terminé (" + nbClients + " clients).");
        } catch (IOException e) {
            System.out.println("Aucun fichier clients trouvé (ou erreur lecture).");
        }
    }

    // ---------------------------------------------------------
    // PARTIE 3 : Fichiers RENDEZ-VOUS
    // ---------------------------------------------------------

    public void versFichierRDV() {
        try {
            // true = mode AJOUT (append) demandé par le sujet 
            FileWriter fw = new FileWriter("rdv.txt", true);

            // On parcourt tout le planning
            for (int j = 0; j < 7; j++) {
                for (int i = 0; i < 16; i++) {
                    if (planning[i][j] != null) {
                        fw.write(planning[i][j].versFichier() + System.lineSeparator());
                    }
                }
            }
            fw.close();
            System.out.println("Sauvegarde RDV terminée.");
        } catch (IOException e) {
            System.out.println("Erreur sauvegarde RDV : " + e.getMessage());
        }
    }

    public void depuisFichierRDV() {
        try {
            FileReader fr = new FileReader("rdv.txt");
            BufferedReader br = new BufferedReader(fr);
            String ligneDate;

            // Le format est sur 3 lignes par RDV
            while ((ligneDate = br.readLine()) != null) {
                String ligneClient = br.readLine();
                String lignePresta = br.readLine();

                if (ligneClient == null || lignePresta == null) break;

                // 1. Reconstitution de la Date
                LocalDateTime dt = LocalDateTime.parse(ligneDate);

                // 2. Vérification : est-ce dans les 7 jours ?
                LocalDate aujourdhui = LocalDate.now();
                long diff = aujourdhui.until(dt.toLocalDate(), ChronoUnit.DAYS);

                if (diff >= 0 && diff < 7) {
                    // 3. Retrouver le Client
                    int numClient = Integer.parseInt(ligneClient);
                    Client leClient = null;
                    for(int i=0; i<nbClients; i++) {
                        if(clients[i].getNumero() == numClient) leClient = clients[i];
                    }

                    if (leClient != null) {
                        // 4. Reconstitution de la Prestation
                        String[] pParts = lignePresta.split(":");
                        char cat = pParts[0].charAt(0);

                        RendezVous rdv = null;

                        // Logique pour deviner le type de prestation selon le nombre d'infos
                        if (pParts.length == 2) {
                            // Ex: "B:78" -> C'est SALE
                            rdv = ajouterRDVSale(leClient, dt, cat);

                        } else if (pParts.length == 3) {
                            // C'est soit Express (true/false au milieu), soit Très Sale (chiffre au milieu)
                            if (pParts[1].equals("true") || pParts[1].equals("false")) {
                                boolean interieur = Boolean.parseBoolean(pParts[1]);
                                rdv = ajouterRDVExpress(leClient, dt, cat, interieur);
                            } else {
                                int salissure = Integer.parseInt(pParts[1]);
                                rdv = ajouterRDVTresSale(leClient, dt, cat, salissure);
                            }
                        }
                    }
                }
            }
            br.close();
            fr.close();
            System.out.println("Chargement RDV terminé.");
        } catch (IOException e) {
            System.out.println("Aucun fichier RDV trouvé.");
        }
    }


}
