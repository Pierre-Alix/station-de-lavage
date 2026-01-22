/**
 * EUGENE Clément
 * BOMET Pierre-Alix
 * Projet Station de Lavage
 */

package com.mycompany.station;

public class PrestationTresSale extends Prestation {
    private int typeSalissure; // 1: nourriture, 2: boue

    public PrestationTresSale(char categorie, int typeSalissure) {
        super(categorie);
        this.typeSalissure = typeSalissure;
    }

    @Override
    public double prelavage() {
        // Prix de base + surcoût produit (ici ce sera 2€ par niveau de salissure) 
        double prix = 5.0;
        if (categorie == 'B') prix *= 1.5;
        if (categorie == 'C') prix *= 1.75;
        return prix + (typeSalissure * 2.0);
    }

    @Override
    public double nettoyage() {
        double prixInterieur = (categorie == 'C') ? 40 : 30;
        return prelavage() + (lavage() + typeSalissure * 3.0) + sechage() + prixInterieur;
    }

    @Override
    public String versFichier() {
        return categorie + ":" + typeSalissure + ":" + (int)nettoyage();
    }
}
