package service;

import model.JeuVideo;
import model.Support;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
        // Je charge les jeux depuis le fichier CSV
        
        Map<String, JeuVideoData> jeuxMap = new HashMap<>();
        
        try (BufferedReader reader = new BufferedReader(new FileReader(filename))) {
            String ligne;
            int ligneCourante = 0;
            
            //J'ouvre le fichier
            while ((ligne = reader.readLine()) != null) {
                ligneCourante++;
                
                if (ligneCourante == 1) {
                    continue;
                }
                
                // Lecture de chaque ligne du fichier
                String[] colonnes = ligne.split(",", -1);
                
                if (colonnes.length < 16) {
                    System.out.println("Ligne " + ligneCourante + " invalide (trop peu de colonnes)");
                    continue;
                }
                
                try {
                    // Extraction des données
                    String nomJeu = colonnes[1].trim();
                    String plateformeSupport = colonnes[2].trim();
                    String genre = colonnes[4].trim();
                    String editeur = colonnes[5].trim();
                    String rating = colonnes[16].trim();
                    
                    // Données du support
                    String anneeSortie = colonnes[3].trim();
                    String developpeur = colonnes[15].trim();
                    float nbVentesMondiales = (colonnes[10].isEmpty() || colonnes[10].trim().equalsIgnoreCase("tbd")) ? 0 : Float.parseFloat(colonnes[10].trim());
                    int nbCritiquesTesteurs = (colonnes[12].isEmpty() || colonnes[12].trim().equalsIgnoreCase("tbd")) ? 0 : (int) Float.parseFloat(colonnes[12].trim());
                    float scoreMoyenNormaliseCritiquesTesteurs = (colonnes[11].isEmpty() || colonnes[11].trim().equalsIgnoreCase("tbd")) ? 0 : Float.parseFloat(colonnes[11].trim());
                    float nbEvaluationsJoueurs = (colonnes[14].isEmpty() || colonnes[14].trim().equalsIgnoreCase("tbd")) ? 0 : Float.parseFloat(colonnes[14].trim());
                    float scoreMoyenNormaliseEvaluationsJoueurs = (colonnes[13].isEmpty() || colonnes[13].trim().equalsIgnoreCase("tbd")) ? 0 : Float.parseFloat(colonnes[13].trim());
                    // Je crée l'objet Support
                    Support support = new Support(
                            plateformeSupport,
                            anneeSortie,
                            developpeur,
                            nbVentesMondiales,
                            nbCritiquesTesteurs,
                            scoreMoyenNormaliseCritiquesTesteurs,
                            nbEvaluationsJoueurs,
                            scoreMoyenNormaliseEvaluationsJoueurs
                    );
                    
                    // J'ajoute le support à la liste des supports du jeu
                    if (!jeuxMap.containsKey(nomJeu)) {
                        JeuVideoData jeuData = new JeuVideoData(nomJeu, genre, editeur, rating);
                        jeuData.supports.add(support);
                        jeuxMap.put(nomJeu, jeuData);
                    } else {
                        jeuxMap.get(nomJeu).supports.add(support);
                    }
                    
                } catch (NumberFormatException e) {
                    System.out.println("Erreur de conversion numérique ligne " + ligneCourante + ": " + e.getMessage());
                }
            }
            
            // J e crée les objets JeuVideo avec leurs supports et je les ajoute à la bibliothèque
            for (JeuVideoData jeuData : jeuxMap.values()) {
                JeuVideo jeuVideo = new JeuVideo(
                        jeuData.nom,
                        jeuData.genre,
                        jeuData.editeur,
                        jeuData.rating,
                        jeuData.supports
                );
                bibliothequeDeJeu.add(jeuVideo);
            }
            
            System.out.println("Chargement réussi : " + bibliothequeDeJeu.size() + " jeu(x) chargé(s)");
            
        } catch (IOException e) {
            // Je gére les exceptions potentielles
            System.err.println("Erreur lors de la lecture du fichier '" + filename + "': " + e.getMessage());
        }
    }
    
    // Classe interne pour regrouper les données d'un jeu avant création de JeuVideo
    private static class JeuVideoData {
        String nom;
        String genre;
        String editeur;
        String rating;
        List<Support> supports;
        
        JeuVideoData(String nom, String genre, String editeur, String rating) {
            this.nom = nom;
            this.genre = genre;
            this.editeur = editeur;
            this.rating = rating;
            this.supports = new ArrayList<>();
        }
    }

    public List<JeuVideo> getBibliothequeDeJeu() {
        return bibliothequeDeJeu;
    }

    public JeuVideo rechercherParNom(String nom) {
        for (JeuVideo jeu : bibliothequeDeJeu) {
            if (jeu.getNom().equalsIgnoreCase(nom)) {
                return jeu;
            }
        }
        return null;
    }

    public List<JeuVideo> rechercherParMotCle(String motCle) {
        List<JeuVideo> resultats = new ArrayList<>();
        String rechercheLower = motCle.toLowerCase();
        for (JeuVideo jeu : bibliothequeDeJeu) {
            if (jeu.getNom().toLowerCase().contains(rechercheLower)) {
                resultats.add(jeu);
            }
        }
        return resultats;
    }
}