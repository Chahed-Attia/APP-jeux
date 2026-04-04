package service;

import model.Membre;
import java.util.ArrayList;

public class MembreService {
    private ArrayList<Membre> membres;

    public MembreService() {
        membres = new ArrayList<>();
    }

    public void ajouterMembre(Membre membre) {
        membres.add(membre);
    }

    public void afficherMembres() {
        for (Membre membre : membres) {
            System.out.println(membre);
        }
    }


    public Membre rechercherParPseudo(String pseudo) {
        for (Membre membre : membres) {
            if (membre.getPseudo().equalsIgnoreCase(pseudo)) {
                return membre;
            }
        }
        return null;
    }

    public boolean supprimerMembre(String pseudo) {
        return membres.removeIf(m -> m.getPseudo().equalsIgnoreCase(pseudo));
    }

    public ArrayList<Membre> getMembres() {
        return membres;
    }
}