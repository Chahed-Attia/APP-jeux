package model;

public class JeuVideo {
    private String nom;
    private String genre;
    private String editeur;
    private String rating;

    public JeuVideo(String nom, String genre, String editeur, String rating) {
        this.nom = nom;
        this.genre = genre;
        this.editeur = editeur;
        this.rating = rating;
    }

    @Override
    public String toString() {
        return "JeuVideo{nom='" + nom + "', genre='" + genre + "', editeur='" + editeur + "', rating='" + rating + "'}";
    }
}