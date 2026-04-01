package model;

import java.util.HashMap;
import java.util.Map;

public class Joueur extends Membre {

    // Map : nom du jeu → heures jouées
    private Map<String, Integer> jeuxPossedes;

    // Map : nom du jeu → nombre de jetons placés sur ce jeu
    private Map<String, Integer> jetonsPlaces;

    public Joueur(String pseudo) {
        super(pseudo);
        this.jeuxPossedes = new HashMap<>();
        this.jetonsPlaces = new HashMap<>();
    }

    // === Gestion des jeux possédés ===

    public void ajouterJeu(String nomJeu) {
        if (!jeuxPossedes.containsKey(nomJeu)) {
            jeuxPossedes.put(nomJeu, 0);
        }
    }

    public boolean possedeJeu(String nomJeu) {
        return jeuxPossedes.containsKey(nomJeu);
    }

    public void ajouterTempsDeJeu(String nomJeu, int heures) {
        if (jeuxPossedes.containsKey(nomJeu)) {
            jeuxPossedes.put(nomJeu, jeuxPossedes.get(nomJeu) + heures);
        }
    }

    public int getTempsDeJeu(String nomJeu) {
        return jeuxPossedes.getOrDefault(nomJeu, 0);
    }

    public Map<String, Integer> getJeuxPossedes() {
        return jeuxPossedes;
    }

    // === Gestion des jetons placés ===

    public boolean placerJeton(String nomJeu) {
        if (jetons <= 0) {
            return false;
        }
        jetons--;
        jetonsPlaces.put(nomJeu, jetonsPlaces.getOrDefault(nomJeu, 0) + 1);
        return true;
    }

    public boolean retirerJeton(String nomJeu) {
        int jetonsJeu = jetonsPlaces.getOrDefault(nomJeu, 0);
        if (jetonsJeu <= 0) {
            return false;
        }
        jetonsPlaces.put(nomJeu, jetonsJeu - 1);
        jetons++;
        return true;
    }

    public int getJetonsPlacesSur(String nomJeu) {
        return jetonsPlaces.getOrDefault(nomJeu, 0);
    }

    public Map<String, Integer> getJetonsPlaces() {
        return jetonsPlaces;
    }

    @Override
    public String toString() {
        return "Joueur{pseudo='" + pseudo + "', jetons=" + jetons
                + ", jeux=" + jeuxPossedes.size() + "}";
    }
}