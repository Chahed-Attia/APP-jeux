package model;

public class Joueur extends Membre {

    public Joueur(String pseudo) {
        super(pseudo);
    }

    @Override
    public String toString() {
        return "Joueur{pseudo='" + pseudo + "', jetons=" + jetons + "}";
    }
}