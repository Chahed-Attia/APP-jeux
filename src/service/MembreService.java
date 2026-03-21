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
}