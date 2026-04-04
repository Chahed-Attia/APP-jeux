package model;

public class Support {
    private String nom;
    private String anneeSortie;
    private String developpeur;
    private float nbVentesMondiales;
    private int nbCritiquesTesteurs;
    private float scoreMoyenNormaliseCritiquesTesteurs;
    private float nbEvaluationsJoueurs;
    private float scoreMoyenNormaliseEvaluationsJoueurs;

    public Support(String nom, String anneeSortie, String developpeur, float nbVentesMondiales, int nbCritiquesTesteurs, float scoreMoyenNormaliseCritiquesTesteurs, float nbEvaluationsJoueurs, float scoreMoyenNormaliseEvaluationsJoueurs) {
        this.nom = nom;
        this.anneeSortie = anneeSortie;
        this.developpeur = developpeur;
        this.nbVentesMondiales = nbVentesMondiales;
        this.nbCritiquesTesteurs = nbCritiquesTesteurs;
        this.scoreMoyenNormaliseCritiquesTesteurs = scoreMoyenNormaliseCritiquesTesteurs;
        this.nbEvaluationsJoueurs = nbEvaluationsJoueurs;
        this.scoreMoyenNormaliseEvaluationsJoueurs = scoreMoyenNormaliseEvaluationsJoueurs;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getAnneeSortie() {
        return anneeSortie;
    }

    public void setAnneeSortie(String anneeSortie) {
        this.anneeSortie = anneeSortie;
    }

    public String getDeveloppeur() {
        return developpeur;
    }

    public void setDeveloppeur(String developpeur) {
        this.developpeur = developpeur;
    }

    public float getNbVentesMondiales() {
        return nbVentesMondiales;
    }

    public void setNbVentesMondiales(float nbVentesMondiales) {
        this.nbVentesMondiales = nbVentesMondiales;
    }

    public int getNbCritiquesTesteurs() {
        return nbCritiquesTesteurs;
    }

    public void setNbCritiquesTesteurs(int nbCritiquesTesteurs) {
        this.nbCritiquesTesteurs = nbCritiquesTesteurs;
    }

    public float getScoreMoyenNormaliseCritiquesTesteurs() {
        return scoreMoyenNormaliseCritiquesTesteurs;
    }

    public void setScoreMoyenNormaliseCritiquesTesteurs(float scoreMoyenNormaliseCritiquesTesteurs) {
        this.scoreMoyenNormaliseCritiquesTesteurs = scoreMoyenNormaliseCritiquesTesteurs;
    }

    public float getNbEvaluationsJoueurs() {
        return nbEvaluationsJoueurs;
    }

    public void setNbEvaluationsJoueurs(float nbEvaluationsJoueurs) {
        this.nbEvaluationsJoueurs = nbEvaluationsJoueurs;
    }

    public float getScoreMoyenNormaliseEvaluationsJoueurs() {
        return scoreMoyenNormaliseEvaluationsJoueurs;
    }

    public void setScoreMoyenNormaliseEvaluationsJoueurs(float scoreMoyenNormaliseEvaluationsJoueurs) {
        this.scoreMoyenNormaliseEvaluationsJoueurs = scoreMoyenNormaliseEvaluationsJoueurs;
    }

    @Override
    public String toString() {
        return "Support{" + nom + ", " + anneeSortie + ", ventes=" + nbVentesMondiales + "M}";
    }
}
