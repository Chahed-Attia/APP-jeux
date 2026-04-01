package console;

import model.*;
import service.JeuService;
import service.MembreService;

import java.util.List;
import java.util.Scanner;

/**
 * Classe Console : gère l'interaction utilisateur en mode console.
 * Affiche les menus selon le profil connecté (invité, joueur, administrateur).
 */
public class Console {

    private final Scanner scanner;
    private final JeuService jeuService;
    private final MembreService membreService;
    private Membre currentUser;

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
            } else if (currentUser instanceof Joueur) {
                quitter = menuJoueur();
            }
        }

        System.out.println("Merci et à bientôt !");
        scanner.close();
    }

    // ===================== MENUS =====================

    /**
     * Menu pour un utilisateur non connecté (invité).
     * @return true si l'utilisateur veut quitter
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
     * Menu pour un joueur connecté.
     * @return true si l'utilisateur veut quitter
     */
    private boolean menuJoueur() {
        System.out.println("\n========== MENU JOUEUR (" + currentUser.getPseudo() + ") ==========");
        System.out.println("1. Rechercher un jeu");
        System.out.println("2. Consulter les informations d'un jeu");
        System.out.println("3. Ajouter un jeu à ma liste");
        System.out.println("4. Ajouter du temps de jeu");
        System.out.println("5. Consulter les informations d'un membre");
        System.out.println("6. Placer/retirer un jeton sur un jeu");
        System.out.println("7. Se désinscrire");
        System.out.println("8. Se déconnecter");
        System.out.println("0. Quitter");
        System.out.println("==================================================");

        int choix = lireEntier("Votre choix : ");

        switch (choix) {
            case 1 -> rechercherJeu();
            case 2 -> consulterInfosJeu();
            case 3 -> ajouterJeuAMaListe();
            case 4 -> ajouterTempsDeJeu();
            case 5 -> consulterInfosMembre();
            case 6 -> gererJetons();
            case 7 -> seDesinscrire();
            case 8 -> seDeconnecter();
            case 0 -> { return true; }
            default -> System.out.println("Choix invalide.");
        }
        return false;
    }

    /**
     * Menu pour un administrateur connecté.
     * @return true si l'utilisateur veut quitter
     */
    private boolean menuAdministrateur() {
        System.out.println("\n========== MENU ADMINISTRATEUR (" + currentUser.getPseudo() + ") ==========");
        System.out.println("1. Rechercher un jeu");
        System.out.println("2. Consulter les informations d'un jeu");
        System.out.println("3. Consulter les informations d'un membre");
        System.out.println("4. Promouvoir un joueur");
        System.out.println("5. Bloquer/débloquer un membre");
        System.out.println("6. Désinscrire un membre");
        System.out.println("7. Afficher tous les membres");
        System.out.println("8. Se déconnecter");
        System.out.println("0. Quitter");
        System.out.println("=============================================================");

        int choix = lireEntier("Votre choix : ");

        switch (choix) {
            case 1 -> rechercherJeu();
            case 2 -> consulterInfosJeu();
            case 3 -> consulterInfosMembre();
            case 4 -> promouvoirJoueur();
            case 5 -> bloquerMembre();
            case 6 -> desinscrireMembre();
            case 7 -> membreService.afficherMembres();
            case 8 -> seDeconnecter();
            case 0 -> { return true; }
            default -> System.out.println("Choix invalide.");
        }
        return false;
    }

    // ===================== ACTIONS =====================

    /**
     * Action 1 : Connexion par pseudo.
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
        String type = (membre instanceof Administrateur) ? "Administrateur" : "Joueur";
        System.out.println("Connexion réussie en tant que " + type + " : " + pseudo);
    }

    /**
     * Action 2 : Inscription d'un nouveau joueur.
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
        System.out.println("Inscription réussie ! Vous pouvez maintenant vous connecter avec '" + pseudo + "'.");
    }

    /**
     * Action 3 : Désinscription du joueur connecté.
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
     * Action 3 (admin) : Désinscrire un membre par pseudo.
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

    /**
     * Action 4 (admin) : Promouvoir un joueur → administrateur.
     */
    private void promouvoirJoueur() {
        String pseudo = lireChaine("Pseudo du joueur à promouvoir : ");
        Membre membre = membreService.rechercherParPseudo(pseudo);

        if (membre == null) {
            System.out.println("Membre introuvable.");
            return;
        }

        if (membre instanceof Administrateur) {
            System.out.println("Ce membre est déjà administrateur.");
            return;
        }

        if (!(membre instanceof Joueur)) {
            System.out.println("Seuls les joueurs peuvent être promus.");
            return;
        }

        // Remplacer le joueur par un administrateur
        membreService.supprimerMembre(pseudo);
        Administrateur admin = new Administrateur(pseudo);
        membreService.ajouterMembre(admin);
        System.out.println("'" + pseudo + "' a été promu Administrateur.");
    }

    /**
     * Action 5 : Consulter les informations d'un membre.
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
     * Action 5 (admin) : Bloquer/débloquer un membre.
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
     * Action 6 : Placer ou retirer un jeton.
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

    /**
     * Action 7 : Ajouter un jeu à la liste du joueur.
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
     * Action 7 : Ajouter du temps de jeu.
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

    /**
     * Action 3 : Rechercher un jeu par nom.
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
     * Action 8 : Consulter les informations d'un jeu.
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
     * Déconnexion.
     */
    private void seDeconnecter() {
        System.out.println("Déconnexion de " + currentUser.getPseudo() + ".");
        currentUser = null;
    }

    // ===================== UTILITAIRES =====================

    /**
     * Lit un entier depuis la console avec gestion d'erreur.
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
     */
    private String lireChaine(String message) {
        System.out.print(message);
        return scanner.nextLine().trim();
    }
}