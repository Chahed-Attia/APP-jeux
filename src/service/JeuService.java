package service;

import model.JeuVideo;
import java.util.ArrayList;

public class JeuService {
    private ArrayList<JeuVideo> jeux;

    public JeuService() {
        jeux = new ArrayList<>();
    }

    public void ajouterJeu(JeuVideo jeu) {
        jeux.add(jeu);
    }

    public void afficherJeux() {
        for (JeuVideo jeu : jeux) {
            System.out.println(jeu);
        }
    }
}