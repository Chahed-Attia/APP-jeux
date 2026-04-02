package service;

import model.JeuVideo;
import model.Support;
import java.util.List;

public class TestService {
    
    public static void main(String[] args) {
        System.out.println("╔════════════════════════════════════════════════════════════╗");
        System.out.println("║          TESTS DE LA CLASSE JeuService                    ║");
        System.out.println("╚════════════════════════════════════════════════════════════╝\n");
        
        // Cas 1: Ajouter un jeu manuellement
        test1_AjouterJeuManuel();
        
        // Cas 2: Afficher un jeu
        test2_AfficherJeu();
        
        // Cas 3: Charger depuis le fichier CSV
        test3_ChargerDepuisCSV();
        
        // Cas 4: Vérifier le nombre de jeux chargés
        test4_VerifierNombreJeux();
        
        // Cas 5: Rechercher un jeu par nom
        test5_RechercherJeuParNom();
        
        // Cas 6: Compter les jeux par genre
        test6_CompterParGenre();
        
        // Cas 7: Trouver le jeu avec le plus de ventes
        test7_JeuPlusVentes();
        
        // Cas 8: Afficher les jeux multi-supports
        test8_JeuxMultiSupports();
    }
    
    // ═══════════════════════════════════════════════════════════════════════
    // CAS 1: Ajouter un jeu manuellement avec supports multiples
    // ═══════════════════════════════════════════════════════════════════════
    public static void test1_AjouterJeuManuel() {
        System.out.println("🧪 CAS 1: AJOUTER UN JEU MANUELLEMENT");
        System.out.println("─────────────────────────────────────");
        
        JeuService service = new JeuService();
        
        // Créer des supports
        Support supportWii = new Support("Wii", "2006", "Nintendo", 82.53f, 51, 76, 322, 8);
        Support supportPS3 = new Support("PlayStation 3", "2008", "Nintendo", 15.2f, 45, 75, 200, 7.5f);
        
        // Créer un jeu avec plusieurs supports
        List<Support> supports = List.of(supportWii, supportPS3);
        JeuVideo jeu = new JeuVideo("Wii Sports", "Sports", "Nintendo", "E", supports);
        
        // Ajouter le jeu
        service.ajouterJeu(jeu);
        
        System.out.println("✓ Jeu ajouté avec succès!");
        System.out.println("  " + jeu);
        System.out.println("  Nombre de supports: " + supports.size() + "\n");
    }
    
    // ═══════════════════════════════════════════════════════════════════════
    // CAS 2: Afficher les jeux dans la bibliothèque
    // ═══════════════════════════════════════════════════════════════════════
    public static void test2_AfficherJeu() {
        System.out.println("🧪 CAS 2: AFFICHER LES JEUX");
        System.out.println("───────────────────────────");
        
        JeuService service = new JeuService();
        
        // Ajouter quelques jeux de test
        Support sup1 = new Support("Nintendo 64", "1996", "Nintendo", 5.2f, 30, 70, 150, 7.8f);
        Support sup2 = new Support("GameCube", "2001", "Nintendo", 2.1f, 20, 75, 100, 8.0f);
        
        JeuVideo jeu1 = new JeuVideo("Super Mario 64", "Platform", "Nintendo", "E", List.of(sup1));
        JeuVideo jeu2 = new JeuVideo("Mario Kart: Double Dash", "Racing", "Nintendo", "E", List.of(sup2));
        
        service.ajouterJeu(jeu1);
        service.ajouterJeu(jeu2);
        
        System.out.println("✓ Affichage de " + service.getBibliothequeDeJeu().size() + " jeu(x):");
        service.afficherJeux();
        System.out.println();
    }
    
    // ═══════════════════════════════════════════════════════════════════════
    // CAS 3: Charger depuis le fichier CSV
    // ═══════════════════════════════════════════════════════════════════════
    public static void test3_ChargerDepuisCSV() {
        System.out.println("🧪 CAS 3: CHARGER DEPUIS LE FICHIER CSV");
        System.out.println("───────────────────────────────────────");
        
        JeuService service = new JeuService();
        String cheminFichier = "data/vg_data.csv";
        
        System.out.println("📂 Chargement du fichier: " + cheminFichier);
        service.chargerJeuVideo(cheminFichier);
        
        System.out.println();
    }
    
    // ═══════════════════════════════════════════════════════════════════════
    // CAS 4: Vérifier le nombre de jeux chargés
    // ═══════════════════════════════════════════════════════════════════════
    public static void test4_VerifierNombreJeux() {
        System.out.println("🧪 CAS 4: VÉRIFIER LE NOMBRE DE JEUX");
        System.out.println("────────────────────────────────────");
        
        JeuService service = new JeuService();
        service.chargerJeuVideo("data/vg_data.csv");
        
        int total = service.getBibliothequeDeJeu().size();
        System.out.println("✓ Nombre total de jeux: " + total);
        
        if (total > 0) {
            System.out.println("  Premier jeu: " + service.getBibliothequeDeJeu().get(0));
            System.out.println("  Dernier jeu: " + service.getBibliothequeDeJeu().get(total - 1));
        }
        System.out.println();
    }
    
    // ═══════════════════════════════════════════════════════════════════════
    // CAS 5: Rechercher un jeu par nom
    // ═══════════════════════════════════════════════════════════════════════
    public static void test5_RechercherJeuParNom() {
        System.out.println("🧪 CAS 5: RECHERCHER UN JEU PAR NOM");
        System.out.println("───────────────────────────────────");
        
        JeuService service = new JeuService();
        service.chargerJeuVideo("data/vg_data.csv");
        
        String nomRecherche = "Wii Sports";
        JeuVideo resultat = null;
        
        for (JeuVideo jeu : service.getBibliothequeDeJeu()) {
            if (jeu.getNom().equals(nomRecherche)) {
                resultat = jeu;
                break;
            }
        }
        
        if (resultat != null) {
            System.out.println("✓ Jeu trouvé: " + resultat);
        } else {
            System.out.println("✗ Jeu non trouvé: " + nomRecherche);
        }
        System.out.println();
    }
    
    // ═══════════════════════════════════════════════════════════════════════
    // CAS 6: Compter les jeux par genre
    // ═══════════════════════════════════════════════════════════════════════
    public static void test6_CompterParGenre() {
        System.out.println("🧪 CAS 6: COMPTER LES JEUX PAR GENRE");
        System.out.println("────────────────────────────────────");
        
        JeuService service = new JeuService();
        service.chargerJeuVideo("data/vg_data.csv");
        
        // Créer une Map pour compter par genre
        java.util.Map<String, Integer> genreCount = new java.util.HashMap<>();
        
        for (JeuVideo jeu : service.getBibliothequeDeJeu()) {
            String genre = jeu.getGenre();
            genreCount.put(genre, genreCount.getOrDefault(genre, 0) + 1);
        }
        
        System.out.println("✓ Répartition par genre:");
        genreCount.entrySet().stream()
            .sorted((a, b) -> b.getValue().compareTo(a.getValue()))
            .limit(5)
            .forEach(entry -> 
                System.out.println("  • " + entry.getKey() + ": " + entry.getValue() + " jeu(x)")
            );
        System.out.println();
    }
    
    // ═══════════════════════════════════════════════════════════════════════
    // CAS 7: Trouver le jeu avec le plus de ventes mondiales
    // ═══════════════════════════════════════════════════════════════════════
    public static void test7_JeuPlusVentes() {
        System.out.println("🧪 CAS 7: JEU AVEC LE PLUS DE VENTES");
        System.out.println("─────────────────────────────────────");
        
        JeuService service = new JeuService();
        service.chargerJeuVideo("data/vg_data.csv");
        
        JeuVideo jeuMaxVentes = null;
        float maxVentes = 0;
        
        for (JeuVideo jeu : service.getBibliothequeDeJeu()) {
            for (Support support : jeu.getSupports()) {
                if (support.getNbVentesMondiales() > maxVentes) {
                    maxVentes = support.getNbVentesMondiales();
                    jeuMaxVentes = jeu;
                }
            }
        }
        
        if (jeuMaxVentes != null) {
            System.out.println("✓ Jeu avec le plus de ventes: " + jeuMaxVentes.getNom());
            System.out.println("  Ventes mondiales: " + maxVentes + "M");
            System.out.println("  Genre: " + jeuMaxVentes.getGenre());
            System.out.println("  Éditeur: " + jeuMaxVentes.getEditeur());
        }
        System.out.println();
    }
    
    // ═══════════════════════════════════════════════════════════════════════
    // CAS 8: Afficher les jeux disponibles sur plusieurs supports
    // ═══════════════════════════════════════════════════════════════════════
    public static void test8_JeuxMultiSupports() {
        System.out.println("🧪 CAS 8: JEUX MULTI-SUPPORTS");
        System.out.println("──────────────────────────────");
        
        JeuService service = new JeuService();
        service.chargerJeuVideo("data/vg_data.csv");
        
        System.out.println("✓ Jeux disponibles sur plusieurs supports:");
        int count = 0;
        for (JeuVideo jeu : service.getBibliothequeDeJeu()) {
            if (jeu.getSupports().size() > 1) {
                System.out.println("  • " + jeu.getNom() + " (" + jeu.getSupports().size() + " supports)");
                for (Support support : jeu.getSupports()) {
                    System.out.println("    └─ " + support.getNom() + " (" + support.getAnneeSortie() + ")");
                }
                count++;
                if (count >= 5) {
                    System.out.println("  ... et d'autres");
                    break;
                }
            }
        }
        System.out.println();
    }
}
