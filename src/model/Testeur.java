package model;

public class Testeur extends Joueur {

    public Testeur(String pseudo) {
        super(pseudo);
    }

    @Override
    public String toString() {
        return "Testeur{pseudo='" + pseudo + "', jetons=" + jetons + "}";
    }
}