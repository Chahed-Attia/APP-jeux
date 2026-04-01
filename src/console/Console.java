package console;

import model.*;
import service.JeuService;
import service.MembreService;

import java.util.List;
import java.util.Scanner;

/**
 * Classe Console : gère l'interaction utilisateur en mode console.
 * Affiche les menus selon le profil connecté :
 *   - currentUser == null      → invité (pas de classe, juste non connecté)
 *   - currentUser est Joueur   → menu joueur
 *   - currentUser est Testeur  → menu testeur
 *   - currentUser est Admin    → menu administrateur
 */
public class Console {

    private final Scanner scanner;
    private final JeuService jeuService;
    private final MembreService membreService;
    private Membre currentUser; // null = invité (non connecté)

    public Console(JeuService jeuService, MembreService membreService) {
        this.scanner = new Scanner(System.in);
        this.jeuService = jeuService;
        this.membreService = membreService;
        this.currentUser = null;
    }

    /**
     * Boucle principale de l'application.
     */
    public void lancer() {
        System.out.println("=== Bienvenue sur la plateforme d'évaluation de jeux vidéo ===");
        boolean quitter = false;

        while (!quitter) {
            if (currentUser == null) {
                quitter = menuInvite();
            } else if (currentUser instanceof Administrateur) {
                quitter = menuAdministrateur();
            } else if (currentUser instanceof Testeur) {
                quitter = menuTesteur();
            } else if (currentUser instanceof Joueur) {
                quitter = menuJoueur();
            }
        }

        System.out.println("Merci et à bientôt !");
        scanner.close();
    }

    // ===================== MENUS =====================

    /**
     * Menu invité : utilisateur non connecté (pas de classe associée).
     */
    private boolean menuInvite() {
        System.out.println("\n========== MENU INVITÉ ==========");
        System.out.println("1. Se connecter");
        System.out.println("2. S'inscrire");
        System.out.println("3. Rechercher un jeu");
        System.out.println("4. Consulter les informations d'un jeu");
        System.out.println("0. Quitter");
        System.out.println("=================================");

        int choix = lireEntier("Votre choix : ");

        switch (choix) {
            case 1 -> seConnecter();
            case 2 -> inscrireJoueur();
            case 3 -> rechercherJeu();
            case 4 -> consulterInfosJeu();
            case 0 -> { return true; }
            default -> System.out.println("Choix invalide.");
        }
        return false;
    }

    /**
     * Menu joueur : Membre connecté de type Joueur.
     */
    private boolean menuJoueur() {
        System.out.println("\n========== MENU JOUEUR (" + currentUser.getPseudo() + ") ==========");
        System.out.println("1. Rechercher un jeu");
        System.out.println("2. Consulter les informations d'un jeu");
        System.out.println("3. Consulter le test d'un jeu");
        System.out.println("4. Écrire une évaluation");
        System.out.println("5. Évaluer une évaluation (+/neutre/-)");
        System.out.println("6. Ajouter un jeu à ma liste");
        System.out.println("7. Ajouter du temps de jeu");
        System.out.println("8. Consulter les informations d'un membre");
        System.out.println("9. Placer/retirer un jeton sur un jeu");
        System.out.println("10. Se désinscrire");
        System.out.println("11. Se déconnecter");
        System.out.println("0. Quitter");
        System.out.println("==================================================");

        int choix = lireEntier("Votre choix : ");

        switch (choix) {
            case 1 -> rechercherJeu();
            case 2 -> consulterInfosJeu();
            case 3 -> consulterTestJeu();
            case 4 -> ecrireEvaluation();
            case 5 -> evaluerEvaluation();
            case 6 -> ajouterJeuAMaListe();
            case 7 -> ajouterTempsDeJeu();
            case 8 -> consulterInfosMembre();
            case 9 -> gererJetons();
            case 10 -> seDesinscrire();
            case 11 -> seDeconnecter();
            case 0 -> { return true; }
            default -> System.out.println("Choix invalide.");
        }
        return false;
    }

    /**
     * Menu testeur : tout ce que Joueur a + écrire tests, signaler évaluations.
     */
    private boolean menuTesteur() {
        System.out.println("\n========== MENU TESTEUR (" + currentUser.getPseudo() + ") ==========");
        System.out.println("1. Rechercher un jeu");
        System.out.println("2. Consulter les informations d'un jeu");
        System.out.println("3. Consulter le test d'un jeu");
        System.out.println("4. Écrire une évaluation");
        System.out.println("5. Évaluer une évaluation (+/neutre/-)");
        System.out.println("6. Ajouter un jeu à ma liste");
        System.out.println("7. Ajouter du temps de jeu");
        System.out.println("8. Consulter les informations d'un membre");
        System.out.println("9. Placer/retirer un jeton sur un jeu");
        System.out.println("10. Écrire un test pour un jeu");
        System.out.println("11. Rechercher un test à réaliser");
        System.out.println("12. Signaler une évaluation problématique");
        System.out.println("13. Se désinscrire");
        System.out.println("14. Se déconnecter");
        System.out.println("0. Quitter");
        System.out.println("==================================================");

        int choix = lireEntier("Votre choix : ");

        switch (choix) {
            case 1 -> rechercherJeu();
            case 2 -> consulterInfosJeu();
            case 3 -> consulterTestJeu();
            case 4 -> ecrireEvaluation();
            case 5 -> evaluerEvaluation();
            case 6 -> ajouterJeuAMaListe();
            case 7 -> ajouterTempsDeJeu();
            case 8 -> consulterInfosMembre();
            case 9 -> gererJetons();
            case 10 -> ecrireTest();
            case 11 -> rechercherTestARealiser();
            case 12 -> signalerEvaluation();
            case 13 -> seDesinscrire();
            case 14 -> seDeconnecter();
            case 0 -> { return true; }
            default -> System.out.println("Choix invalide.");
        }
        return false;
    }

    /**
     * Menu admin : tout ce que Testeur a + promouvoir, bloquer, supprimer.
     */
    private boolean menuAdministrateur() {
        System.out.println("\n========== MENU ADMINISTRATEUR (" + currentUser.getPseudo() + ") ==========");
        System.out.println("1. Rechercher un jeu");
        System.out.println("2. Consulter les informations d'un jeu");
        System.out.println("3. Consulter le test d'un jeu");
        System.out.println("4. Consulter les informations d'un membre");
        System.out.println("5. Promouvoir un membre");
        System.out.println("6. Bloquer/débloquer un membre");
        System.out.println("7. Désinscrire un membre");
        System.out.println("8. Supprimer une évaluation");
        System.out.println("9. Afficher tous les membres");
        System.out.println("10. Se déconnecter");
        System.out.println("0. Quitter");
        System.out.println("=============================================================");

        int choix = lireEntier("Votre choix : ");

        switch (choix) {
            case 1 -> rechercherJeu();
            case 2 -> consulterInfosJeu();
            case 3 -> consulterTestJeu();
            case 4 -> consulterInfosMembre();
            case 5 -> promouvoirMembre();
            case 6 -> bloquerMembre();
            case 7 -> desinscrireMembre();
            case 8 -> supprimerEvaluation();
            case 9 -> membreService.afficherMembres();
            case 10 -> seDeconnecter();
            case 0 -> { return true; }
            default -> System.out.println("Choix invalide.");
        }
        return false;
    }

    // ===================== ACTIONS CONNEXION / INSCRIPTION =====================

    /**
     * Connexion par pseudo.
     */
    private void seConnecter() {
        String pseudo = lireChaine("Entrez votre pseudo : ");
        Membre membre = membreService.rechercherParPseudo(pseudo);

        if (membre == null) {
            System.out.println("Aucun membre trouvé avec le pseudo '" + pseudo + "'.");
            return;
        }

        if (membre.isBloque()) {
            System.out.println("Ce compte est bloqué. Connexion impossible.");
            return;
        }

        currentUser = membre;
        String type;
        if (membre instanceof Administrateur) type = "Administrateur";
        else if (membre instanceof Testeur) type = "Testeur";
        else type = "Joueur";
        System.out.println("Connexion réussie en tant que " + type + " : " + pseudo);
    }

    /**
     * Inscription d'un nouveau joueur.
     */
    private void inscrireJoueur() {
        String pseudo = lireChaine("Choisissez un pseudo : ");

        if (pseudo.isEmpty()) {
            System.out.println("Le pseudo ne peut pas être vide.");
            return;
        }

        if (pseudo.equalsIgnoreCase("admin")) {
            System.out.println("Ce pseudo est réservé.");
            return;
        }

        if (membreService.rechercherParPseudo(pseudo) != null) {
            System.out.println("Ce pseudo est déjà pris.");
            return;
        }

        Joueur nouveauJoueur = new Joueur(pseudo);
        membreService.ajouterMembre(nouveauJoueur);
        System.out.println("Inscription réussie ! Connectez-vous avec '" + pseudo + "'.");
    }

    /**
     * Désinscription du joueur connecté.
     */
    private void seDesinscrire() {
        String confirmation = lireChaine("Êtes-vous sûr de vouloir vous désinscrire ? (oui/non) : ");
        if (confirmation.equalsIgnoreCase("oui")) {
            membreService.supprimerMembre(currentUser.getPseudo());
            System.out.println("Désinscription réussie. Au revoir " + currentUser.getPseudo() + " !");
            currentUser = null;
        } else {
            System.out.println("Désinscription annulée.");
        }
    }

    /**
     * Déconnexion.
     */
    private void seDeconnecter() {
        System.out.println("Déconnexion de " + currentUser.getPseudo() + ".");
        currentUser = null;
    }

    // ===================== ACTIONS JEUX =====================

    /**
     * Rechercher un jeu par mot-clé.
     */
    private void rechercherJeu() {
        String recherche = lireChaine("Entrez le nom du jeu à rechercher : ");
        List<JeuVideo> resultats = jeuService.rechercherParMotCle(recherche);

        if (resultats.isEmpty()) {
            System.out.println("Aucun jeu trouvé pour '" + recherche + "'.");
        } else {
            System.out.println("\n--- " + resultats.size() + " jeu(x) trouvé(s) ---");
            for (int i = 0; i < resultats.size(); i++) {
                JeuVideo jeu = resultats.get(i);
                System.out.println((i + 1) + ". " + jeu.getNom() + " [" + jeu.getGenre() + "] - " + jeu.getEditeur());
            }
        }
    }

    /**
     * Consulter les informations détaillées d'un jeu.
     */
    private void consulterInfosJeu() {
        String nomJeu = lireChaine("Nom du jeu : ");
        JeuVideo jeu = jeuService.rechercherParNom(nomJeu);

        if (jeu == null) {
            System.out.println("Jeu introuvable.");
            return;
        }

        System.out.println("\n--- Informations du jeu ---");
        System.out.println("Nom       : " + jeu.getNom());
        System.out.println("Genre     : " + jeu.getGenre());
        System.out.println("Éditeur   : " + jeu.getEditeur());
        System.out.println("Rating    : " + jeu.getRating());
        System.out.println("Supports  :");
        for (Support s : jeu.getSupports()) {
            System.out.println("  - " + s.getNom() + " (" + s.getAnneeSortie() + ") "
                    + "Dev: " + s.getDeveloppeur()
                    + " | Ventes: " + s.getNbVentesMondiales() + "M"
                    + " | Score critiques: " + s.getScoreMoyenNormaliseCritiquesTesteurs() + "/100"
                    + " | Score joueurs: " + s.getScoreMoyenNormaliseEvaluationsJoueurs() + "/10");
        }
    }

    /**
     * Consulter le test d'un jeu.
     */
    private void consulterTestJeu() {
        String nomJeu = lireChaine("Nom du jeu : ");
        JeuVideo jeu = jeuService.rechercherParNom(nomJeu);

        if (jeu == null) {
            System.out.println("Jeu introuvable.");
            return;
        }

        // TODO: afficher le test du jeu quand la classe Test sera créée
        System.out.println("Aucun test disponible pour '" + jeu.getNom() + "' pour le moment.");
    }

    /**
     * Ajouter un jeu à la liste du joueur.
     */
    private void ajouterJeuAMaListe() {
        String nomJeu = lireChaine("Nom du jeu à ajouter : ");
        JeuVideo jeu = jeuService.rechercherParNom(nomJeu);

        if (jeu == null) {
            System.out.println("Jeu introuvable dans la bibliothèque.");
            return;
        }

        // TODO: ajouter le jeu à la liste des jeux du joueur
        System.out.println("Jeu '" + jeu.getNom() + "' ajouté à votre liste.");
    }

    /**
     * Ajouter du temps de jeu.
     */
    private void ajouterTempsDeJeu() {
        String nomJeu = lireChaine("Nom du jeu : ");
        JeuVideo jeu = jeuService.rechercherParNom(nomJeu);

        if (jeu == null) {
            System.out.println("Jeu introuvable.");
            return;
        }

        int heures = lireEntier("Nombre d'heures jouées à ajouter : ");
        if (heures <= 0) {
            System.out.println("Le nombre d'heures doit être positif.");
            return;
        }

        // TODO: ajouter les heures au jeu du joueur
        System.out.println(heures + "h ajoutées pour '" + jeu.getNom() + "'.");
    }

    // ===================== ACTIONS ÉVALUATIONS =====================

    /**
     * Écrire une évaluation pour un jeu possédé.
     */
    private void ecrireEvaluation() {
        String nomJeu = lireChaine("Nom du jeu à évaluer : ");
        JeuVideo jeu = jeuService.rechercherParNom(nomJeu);

        if (jeu == null) {
            System.out.println("Jeu introuvable.");
            return;
        }

        // TODO: vérifier que le joueur possède le jeu et y a joué assez longtemps
        String texte = lireChaine("Texte de l'évaluation : ");
        int note = lireEntier("Note globale (1-10) : ");

        // TODO: créer l'objet Evaluation et l'associer au jeu
        System.out.println("Évaluation enregistrée pour '" + jeu.getNom() + "'. (note: " + note + ")");
    }

    /**
     * Évaluer une évaluation existante (+/neutre/-).
     */
    private void evaluerEvaluation() {
        String nomJeu = lireChaine("Nom du jeu : ");
        JeuVideo jeu = jeuService.rechercherParNom(nomJeu);

        if (jeu == null) {
            System.out.println("Jeu introuvable.");
            return;
        }

        // TODO: afficher les évaluations et permettre de voter
        System.out.println("Aucune évaluation disponible pour '" + jeu.getNom() + "' pour le moment.");
    }

    /**
     * Signaler une évaluation problématique (testeur).
     */
    private void signalerEvaluation() {
        String nomJeu = lireChaine("Nom du jeu : ");
        JeuVideo jeu = jeuService.rechercherParNom(nomJeu);

        if (jeu == null) {
            System.out.println("Jeu introuvable.");
            return;
        }

        // TODO: afficher les évaluations et permettre de signaler
        System.out.println("Aucune évaluation à signaler pour '" + jeu.getNom() + "' pour le moment.");
    }

    /**
     * Supprimer une évaluation problématique (administrateur).
     */
    private void supprimerEvaluation() {
        String nomJeu = lireChaine("Nom du jeu : ");
        JeuVideo jeu = jeuService.rechercherParNom(nomJeu);

        if (jeu == null) {
            System.out.println("Jeu introuvable.");
            return;
        }

        // TODO: afficher les évaluations et permettre de supprimer
        System.out.println("Aucune évaluation à supprimer pour '" + jeu.getNom() + "' pour le moment.");
    }

    // ===================== ACTIONS TESTS (TESTEUR) =====================

    /**
     * Écrire un test pour un jeu (testeur).
     */
    private void ecrireTest() {
        String nomJeu = lireChaine("Nom du jeu à tester : ");
        JeuVideo jeu = jeuService.rechercherParNom(nomJeu);

        if (jeu == null) {
            System.out.println("Jeu introuvable.");
            return;
        }

        // TODO: vérifier que le testeur possède le jeu et y a joué un temps minimal
        String texte = lireChaine("Texte du test : ");
        String version = lireChaine("Numéro de version/build : ");

        // TODO: créer l'objet Test et libérer les jetons placés sur ce jeu
        System.out.println("Test publié pour '" + jeu.getNom() + "'.");
    }

    /**
     * Rechercher un test à réaliser (par jetons décroissants).
     */
    private void rechercherTestARealiser() {
        // TODO: afficher les jeux par nombre de jetons décroissant
        // TODO: le testeur doit posséder le jeu et y avoir joué un temps minimal
        System.out.println("Fonctionnalité à compléter (nécessite le système de jetons par jeu).");
    }

    // ===================== ACTIONS JETONS =====================

    /**
     * Placer ou retirer un jeton sur un jeu.
     */
    private void gererJetons() {
        System.out.println("Jetons disponibles : " + currentUser.getJetons());
        System.out.println("1. Placer un jeton");
        System.out.println("2. Retirer un jeton");
        int choix = lireEntier("Votre choix : ");

        String nomJeu = lireChaine("Nom du jeu : ");
        JeuVideo jeu = jeuService.rechercherParNom(nomJeu);

        if (jeu == null) {
            System.out.println("Jeu introuvable.");
            return;
        }

        switch (choix) {
            case 1 -> {
                if (currentUser.getJetons() <= 0) {
                    System.out.println("Vous n'avez plus de jetons disponibles.");
                } else {
                    currentUser.setJetons(currentUser.getJetons() - 1);
                    System.out.println("Jeton placé sur '" + jeu.getNom() + "'. Jetons restants : " + currentUser.getJetons());
                }
            }
            case 2 -> {
                currentUser.setJetons(currentUser.getJetons() + 1);
                System.out.println("Jeton retiré de '" + jeu.getNom() + "'. Jetons restants : " + currentUser.getJetons());
            }
            default -> System.out.println("Choix invalide.");
        }
    }

    // ===================== ACTIONS ADMIN =====================

    /**
     * Consulter les informations d'un membre.
     */
    private void consulterInfosMembre() {
        String pseudo = lireChaine("Pseudo du membre à consulter : ");
        Membre membre = membreService.rechercherParPseudo(pseudo);

        if (membre == null) {
            System.out.println("Membre introuvable.");
            return;
        }

        System.out.println("\n--- Informations du membre ---");
        System.out.println(membre);
    }

    /**
     * Promouvoir un membre : Joueur → Testeur → Administrateur.
     */
    private void promouvoirMembre() {
        String pseudo = lireChaine("Pseudo du membre à promouvoir : ");
        Membre membre = membreService.rechercherParPseudo(pseudo);

        if (membre == null) {
            System.out.println("Membre introuvable.");
            return;
        }

        if (membre instanceof Administrateur) {
            System.out.println("Ce membre est déjà administrateur.");
            return;
        }

        membreService.supprimerMembre(pseudo);

        if (membre instanceof Testeur) {
            Administrateur admin = new Administrateur(pseudo);
            membreService.ajouterMembre(admin);
            System.out.println("'" + pseudo + "' promu de Testeur à Administrateur.");
        } else if (membre instanceof Joueur) {
            Testeur testeur = new Testeur(pseudo);
            membreService.ajouterMembre(testeur);
            System.out.println("'" + pseudo + "' promu de Joueur à Testeur.");
        } else {
            System.out.println("Seuls les joueurs et testeurs peuvent être promus.");
        }
    }

    /**
     * Bloquer/débloquer un membre.
     */
    private void bloquerMembre() {
        String pseudo = lireChaine("Pseudo du membre à bloquer/débloquer : ");
        Membre membre = membreService.rechercherParPseudo(pseudo);

        if (membre == null) {
            System.out.println("Membre introuvable.");
            return;
        }

        if (membre instanceof Administrateur) {
            System.out.println("Impossible de bloquer un administrateur.");
            return;
        }

        membre.setBloque(!membre.isBloque());
        String statut = membre.isBloque() ? "bloqué" : "débloqué";
        System.out.println("Le membre '" + pseudo + "' a été " + statut + ".");
    }

    /**
     * Désinscrire un membre (par un administrateur).
     */
    private void desinscrireMembre() {
        String pseudo = lireChaine("Pseudo du membre à désinscrire : ");
        Membre membre = membreService.rechercherParPseudo(pseudo);

        if (membre == null) {
            System.out.println("Membre introuvable.");
            return;
        }

        if (membre instanceof Administrateur) {
            System.out.println("Impossible de désinscrire un administrateur.");
            return;
        }

        membreService.supprimerMembre(pseudo);
        System.out.println("Membre '" + pseudo + "' désinscrit avec succès.");
    }

    // ===================== UTILITAIRES =====================

    /**
     * Lit un entier depuis la console avec gestion d'erreur.
     * @param message le message affiché à l'utilisateur
     * @return l'entier saisi
     */
    private int lireEntier(String message) {
        while (true) {
            try {
                System.out.print(message);
                return Integer.parseInt(scanner.nextLine().trim());
            } catch (NumberFormatException e) {
                System.out.println("Veuillez entrer un nombre valide.");
            }
        }
    }

    /**
     * Lit une chaîne de caractères depuis la console.
     * @param message le message affiché à l'utilisateur
     * @return la chaîne saisie
     */
    private String lireChaine(String message) {
        System.out.print(message);
        return scanner.nextLine().trim();
    }
}