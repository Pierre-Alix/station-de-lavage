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

public class PrestationExpress extends Prestation {
    private boolean nettoyageInterieur;

    public PrestationExpress(char categorie, boolean nettoyageInterieur) {
        super(categorie);
        this.nettoyageInterieur = nettoyageInterieur;
    }

    @Override
    public double prelavage() { return 0; } // Il n'y a pas de prélavage en presta express

    @Override
    public double nettoyage() {
        double total = lavage() + sechage();
        if (nettoyageInterieur) {
            total += (categorie == 'C') ? 40 : 30; 
        }
        return total;
    }
}