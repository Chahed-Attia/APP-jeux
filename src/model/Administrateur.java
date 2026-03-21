package model;

public class Administrateur extends Joueur {

    public Administrateur(String pseudo) {
        super(pseudo);
    }

    @Override
    public String toString() {
        return "Administrateur{pseudo='" + pseudo + "', jetons=" + jetons + "}";
    }
}