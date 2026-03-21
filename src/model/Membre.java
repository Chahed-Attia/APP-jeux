package model;

public class Membre {
    protected String pseudo;
    protected int jetons;
    protected boolean bloque;

    public Membre(String pseudo) {
        this.pseudo = pseudo;
        this.jetons = 3;
        this.bloque = false;
    }

    public String getPseudo() {
        return pseudo;
    }

    @Override
    public String toString() {
        return "Membre{pseudo='" + pseudo + "', jetons=" + jetons + ", bloque=" + bloque + "}";
    }
}
