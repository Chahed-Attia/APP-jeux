package service;

import model.JeuVideo;
import java.util.ArrayList;
import java.util.List;

public class JeuService {

    private List<JeuVideo> bibliothequeDeJeu;

    public JeuService() {
        bibliothequeDeJeu = new ArrayList<>();
    }

    public void ajouterJeu(JeuVideo jeu) {
        bibliothequeDeJeu.add(jeu);
    }

    public void afficherJeux() {
        for (JeuVideo jeu : bibliothequeDeJeu) {
            System.out.println(jeu);
        }
    }

    public void chargerJeuVideo(String filename) {
        // Implémentation pour charger les jeux depuis un fichier
        // (par exemple, en utilisant BufferedReader pour lire le fichier ligne par ligne)
       // 1 - Ouvrir le fichier
       // 2 - Lire chaque ligne du fichier
       // 3 - Pour chaque ligne, créer un objet JeuVideo et l'ajouter à la BibliothequeDeJeux
       // 4 - Gérer les exceptions potentielles (par exemple, FileNotFoundException, IOException)
    }

    public List<JeuVideo> getBibliothequeDeJeu() {
        return bibliothequeDeJeu;
    }
}