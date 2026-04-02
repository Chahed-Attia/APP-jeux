package model;

import java.util.List;

public class JeuVideo {
    private String nom;
    private String genre;
    private String editeur;
    private String rating;
    private List<Support> supports;

    public JeuVideo(String nom, String genre, String editeur, String rating, List<Support> supports) {
        this.nom = nom;
        this.genre = genre;
        this.editeur = editeur;
        this.rating = rating;
        this.supports= supports;
    }

    // Getters
    public String getNom() {
        return nom;
    }

    public String getGenre() {
        return genre;
    }

    public String getEditeur() {
        return editeur;
    }

    public String getRating() {
        return rating;
    }

    public List<Support> getSupports() {
        return supports;
    }

    @Override
    public String toString() {
        return "JeuVideo{nom='" + nom + "', genre='" + genre + "', editeur='" + editeur + "', rating='" + rating +"Supports: " + supports+ '}';
    }
}