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

public class PrestationSale extends Prestation {
    public PrestationSale(char categorie) {
        super(categorie);
    }

    @Override
    public double prelavage() {
        double prix = 5.0; 
        if (categorie == 'B') prix *= 1.5;
        if (categorie == 'C') prix *= 1.75;
        return prix;
    }

    @Override
    public double nettoyage() {
        // Prélavage + Lavage + Séchage + Nettoyage intérieur toujours inclus dans cette prestation
        // On a préféré utiliser une opérarion ternaire (? :) au lieu de if...else pour diminuer le nombre de lignes et pour la lisibilité
        
        double prixInterieur = (categorie == 'C') ? 40 : 30;
        return prelavage() + lavage() + sechage() + prixInterieur;
    }
}
