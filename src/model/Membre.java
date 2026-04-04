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

    public int getJetons() {
        return jetons;
    }

    public void setJetons(int jetons) {
        this.jetons = jetons;
    }

    public boolean isBloque() {
        return bloque;
    }

    public void setBloque(boolean bloque) {
        this.bloque = bloque;
    }

    @Override
    public String toString() {
        return "Membre{pseudo='" + pseudo + "', jetons=" + jetons + ", bloque=" + bloque + "}";
    }
}