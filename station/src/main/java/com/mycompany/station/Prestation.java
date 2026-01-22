/**
 * EUGENE Clément
 * BOMET Pierre-Alix
 * Projet Station de Lavage
 */

package com.mycompany.station;

/**
 * On crée la classe mère de toutes les prestations
 */

public abstract class Prestation {
    protected char categorie; // catégorie de véhicule A, B ou C 

    // Constructeur
    public Prestation(char categorie) {
        this.categorie = categorie;
    }

    // Les différents tarifs de lavage
    public double lavage() {
        double prix = 20.0;
        if (categorie == 'B') prix *= 1.5; // +50% pour les vehicules catégorie B
        if (categorie == 'C') prix *= 1.75; // +75% les catégories C
        return prix;
    }

    // Les différents tarifs de séchage
    public double sechage() {
        double prix = 10.0;
        if (categorie == 'B') prix *= 1.05; // +5% pour les véhicules B
        if (categorie == 'C') prix *= 1.10; // +10% pour les C
        return prix;
    }

    // Méthode abstraite car la présence du prélavage dépend du type de prestation choisie
    public abstract double prelavage();

    // Prix total 
    public abstract double nettoyage();

    // Chaque prestation doit savoir s'écrire pour le fichier
    public abstract String versFichier();

}
