package console;

import model.*;
import service.EvaluationService;
import service.JeuService;
import service.MembreService;

import java.util.List;
import java.util.Scanner;

/**
 * Classe Console : gère l'interaction utilisateur en mode console.
 * - currentUser == null         → invité (pas de classe, non connecté)
 * - currentUser est Joueur      → menu joueur
 * - currentUser est Testeur     → menu testeur
 * - currentUser est Administrateur → menu administrateur
 */
public class Console {

    private final Scanner scanner;
    private final JeuService jeuService;
    private final MembreService membreService;
    private final EvaluationService evaluationService;
    private Membre currentUser;

    public Console(JeuService jeuService, MembreService membreService, EvaluationService evaluationService) {
        this.scanner = new Scanner(System.in);
        this.jeuService = jeuService;
        this.membreService = membreService;
        this.evaluationService = evaluationService;
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

    // ===================== CONNEXION / INSCRIPTION =====================

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

    private void seDeconnecter() {
        System.out.println("Déconnexion de " + currentUser.getPseudo() + ".");
        currentUser = null;
    }

    // ===================== ACTIONS JEUX =====================

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

        // Afficher les évaluations s'il y en a
        List<Evaluation> evals = evaluationService.getEvaluationsParJeu(nomJeu);
        if (!evals.isEmpty()) {
            System.out.println("\n--- Évaluations ---");
            for (Evaluation e : evals) {
                System.out.println(e);
            }
        }
    }

    private void consulterTestJeu() {
        String nomJeu = lireChaine("Nom du jeu : ");
        JeuVideo jeu = jeuService.rechercherParNom(nomJeu);

        if (jeu == null) {
            System.out.println("Jeu introuvable.");
            return;
        }

        List<Test> testsJeu = evaluationService.getTestsParJeu(nomJeu);
        if (testsJeu.isEmpty()) {
            System.out.println("Aucun test disponible pour '" + jeu.getNom() + "'.");
        } else {
            System.out.println("\n--- Tests pour " + jeu.getNom() + " ---");
            for (Test t : testsJeu) {
                System.out.println(t);
            }
        }
    }

    private void ajouterJeuAMaListe() {
        String nomJeu = lireChaine("Nom du jeu à ajouter : ");
        JeuVideo jeu = jeuService.rechercherParNom(nomJeu);

        if (jeu == null) {
            System.out.println("Jeu introuvable dans la bibliothèque.");
            return;
        }

        Joueur joueur = (Joueur) currentUser;

        if (joueur.possedeJeu(nomJeu)) {
            System.out.println("Vous possédez déjà ce jeu.");
            return;
        }

        joueur.ajouterJeu(nomJeu);
        System.out.println("Jeu '" + jeu.getNom() + "' ajouté à votre liste.");
    }

    private void ajouterTempsDeJeu() {
        String nomJeu = lireChaine("Nom du jeu : ");
        JeuVideo jeu = jeuService.rechercherParNom(nomJeu);

        if (jeu == null) {
            System.out.println("Jeu introuvable.");
            return;
        }

        Joueur joueur = (Joueur) currentUser;

        if (!joueur.possedeJeu(nomJeu)) {
            System.out.println("Vous ne possédez pas ce jeu. Ajoutez-le d'abord.");
            return;
        }

        int heures = lireEntier("Nombre d'heures jouées à ajouter : ");
        if (heures <= 0) {
            System.out.println("Le nombre d'heures doit être positif.");
            return;
        }

        joueur.ajouterTempsDeJeu(nomJeu, heures);
        System.out.println(heures + "h ajoutées. Total pour '" + jeu.getNom() + "' : " + joueur.getTempsDeJeu(nomJeu) + "h.");
    }

    // ===================== ACTIONS ÉVALUATIONS =====================

    private void ecrireEvaluation() {
        String nomJeu = lireChaine("Nom du jeu à évaluer : ");
        JeuVideo jeu = jeuService.rechercherParNom(nomJeu);

        if (jeu == null) {
            System.out.println("Jeu introuvable.");
            return;
        }

        Joueur joueur = (Joueur) currentUser;

        if (!joueur.possedeJeu(nomJeu)) {
            System.out.println("Vous devez posséder ce jeu pour l'évaluer.");
            return;
        }

        if (joueur.getTempsDeJeu(nomJeu) < 1) {
            System.out.println("Vous devez avoir joué au moins 1h pour évaluer ce jeu.");
            return;
        }

        // Choisir le support
        System.out.println("Supports disponibles :");
        List<Support> supports = jeu.getSupports();
        for (int i = 0; i < supports.size(); i++) {
            System.out.println((i + 1) + ". " + supports.get(i).getNom());
        }
        int choixSupport = lireEntier("Choisissez un support : ") - 1;
        if (choixSupport < 0 || choixSupport >= supports.size()) {
            System.out.println("Choix invalide.");
            return;
        }
        String support = supports.get(choixSupport).getNom();

        String texte = lireChaine("Texte de l'évaluation : ");
        String version = lireChaine("Numéro de version/build : ");
        int note = lireEntier("Note globale (1-10) : ");

        if (note < 1 || note > 10) {
            System.out.println("La note doit être entre 1 et 10.");
            return;
        }

        Evaluation eval = new Evaluation(joueur.getPseudo(), nomJeu, support, texte, version, note);
        evaluationService.ajouterEvaluation(eval);
        System.out.println("Évaluation enregistrée pour '" + jeu.getNom() + "' (" + support + ").");
    }

    private void evaluerEvaluation() {
        String nomJeu = lireChaine("Nom du jeu : ");
        List<Evaluation> evals = evaluationService.getEvaluationsParJeu(nomJeu);

        if (evals.isEmpty()) {
            System.out.println("Aucune évaluation disponible pour ce jeu.");
            return;
        }

        System.out.println("\n--- Évaluations ---");
        for (int i = 0; i < evals.size(); i++) {
            System.out.println((i + 1) + ". " + evals.get(i));
        }

        int choix = lireEntier("Numéro de l'évaluation à évaluer : ") - 1;
        if (choix < 0 || choix >= evals.size()) {
            System.out.println("Choix invalide.");
            return;
        }

        Evaluation eval = evals.get(choix);

        if (eval.getAuteurPseudo().equalsIgnoreCase(currentUser.getPseudo())) {
            System.out.println("Vous ne pouvez pas évaluer votre propre évaluation.");
            return;
        }

        System.out.println("1. Positif (+)");
        System.out.println("2. Neutre");
        System.out.println("3. Négatif (-)");
        int vote = lireEntier("Votre vote : ");

        switch (vote) {
            case 1 -> { eval.ajouterVotePositif(); System.out.println("Vote positif enregistré."); }
            case 2 -> { eval.ajouterVoteNeutre(); System.out.println("Vote neutre enregistré."); }
            case 3 -> { eval.ajouterVoteNegatif(); System.out.println("Vote négatif enregistré."); }
            default -> System.out.println("Choix invalide.");
        }
    }

    private void signalerEvaluation() {
        String nomJeu = lireChaine("Nom du jeu : ");
        List<Evaluation> evals = evaluationService.getEvaluationsParJeu(nomJeu);

        if (evals.isEmpty()) {
            System.out.println("Aucune évaluation pour ce jeu.");
            return;
        }

        System.out.println("\n--- Évaluations ---");
        for (int i = 0; i < evals.size(); i++) {
            System.out.println((i + 1) + ". " + evals.get(i));
        }

        int choix = lireEntier("Numéro de l'évaluation à signaler : ") - 1;
        if (choix < 0 || choix >= evals.size()) {
            System.out.println("Choix invalide.");
            return;
        }

        evals.get(choix).signaler();
        System.out.println("Évaluation signalée avec succès.");
    }

    private void supprimerEvaluation() {
        String nomJeu = lireChaine("Nom du jeu : ");
        List<Evaluation> evals = evaluationService.getEvaluationsParJeu(nomJeu);

        if (evals.isEmpty()) {
            System.out.println("Aucune évaluation pour ce jeu.");
            return;
        }

        System.out.println("\n--- Évaluations ---");
        for (int i = 0; i < evals.size(); i++) {
            System.out.println((i + 1) + ". " + evals.get(i));
        }

        int choix = lireEntier("Numéro de l'évaluation à supprimer : ") - 1;
        if (choix < 0 || choix >= evals.size()) {
            System.out.println("Choix invalide.");
            return;
        }

        evaluationService.supprimerEvaluation(evals.get(choix));
        System.out.println("Évaluation supprimée.");
    }

    // ===================== ACTIONS TESTS (TESTEUR) =====================

    private void ecrireTest() {
        String nomJeu = lireChaine("Nom du jeu à tester : ");
        JeuVideo jeu = jeuService.rechercherParNom(nomJeu);

        if (jeu == null) {
            System.out.println("Jeu introuvable.");
            return;
        }

        Joueur joueur = (Joueur) currentUser;

        if (!joueur.possedeJeu(nomJeu)) {
            System.out.println("Vous devez posséder ce jeu pour le tester.");
            return;
        }

        if (joueur.getTempsDeJeu(nomJeu) < 5) {
            System.out.println("Vous devez avoir joué au moins 5h pour tester ce jeu.");
            return;
        }

        // Choisir le support
        System.out.println("Supports disponibles :");
        List<Support> supports = jeu.getSupports();
        for (int i = 0; i < supports.size(); i++) {
            System.out.println((i + 1) + ". " + supports.get(i).getNom());
        }
        int choixSupport = lireEntier("Choisissez un support : ") - 1;
        if (choixSupport < 0 || choixSupport >= supports.size()) {
            System.out.println("Choix invalide.");
            return;
        }
        String support = supports.get(choixSupport).getNom();

        // Vérifier qu'il n'existe pas déjà un test pour ce support
        if (evaluationService.getTestParJeuEtSupport(nomJeu, support) != null) {
            System.out.println("Un test existe déjà pour ce jeu sur ce support.");
            return;
        }

        String texte = lireChaine("Texte du test : ");
        String version = lireChaine("Numéro de version/build : ");

        Test test = new Test(currentUser.getPseudo(), nomJeu, support, texte, version);

        // Notes par catégorie
        System.out.println("Notes par catégorie (0-20). Laissez le nom vide pour terminer.");
        while (true) {
            String categorie = lireChaine("Catégorie (ex: gameplay, interface, optimisation) : ");
            if (categorie.isEmpty()) break;
            int note = lireEntier("Note pour " + categorie + " (0-20) : ");
            test.ajouterNote(categorie, note);
        }

        // Points forts (optionnel)
        String pointFort = lireChaine("Point fort (laisser vide pour passer) : ");
        while (!pointFort.isEmpty()) {
            test.ajouterPointFort(pointFort);
            pointFort = lireChaine("Autre point fort (laisser vide pour terminer) : ");
        }

        // Points faibles (optionnel)
        String pointFaible = lireChaine("Point faible (laisser vide pour passer) : ");
        while (!pointFaible.isEmpty()) {
            test.ajouterPointFaible(pointFaible);
            pointFaible = lireChaine("Autre point faible (laisser vide pour terminer) : ");
        }

        evaluationService.ajouterTest(test);

        // Le testeur gagne 5 jetons pour chaque test publié
        currentUser.setJetons(currentUser.getJetons() + 5);

        System.out.println("Test publié pour '" + jeu.getNom() + "' (" + support + "). +5 jetons !");
    }

    private void rechercherTestARealiser() {
        Joueur joueur = (Joueur) currentUser;

        System.out.println("\n--- Jeux en attente de test (que vous possédez avec 5h+ de jeu) ---");
        List<JeuVideo> tousLesJeux = jeuService.getBibliothequeDeJeu();
        boolean aucunResultat = true;

        for (JeuVideo jeu : tousLesJeux) {
            if (joueur.possedeJeu(jeu.getNom()) && joueur.getTempsDeJeu(jeu.getNom()) >= 5) {
                for (Support s : jeu.getSupports()) {
                    if (evaluationService.getTestParJeuEtSupport(jeu.getNom(), s.getNom()) == null) {
                        System.out.println("- " + jeu.getNom() + " (" + s.getNom() + ") [" + jeu.getGenre() + "]");
                        aucunResultat = false;
                    }
                }
            }
        }

        if (aucunResultat) {
            System.out.println("Aucun jeu éligible pour un test.");
        }
    }

    // ===================== ACTIONS JETONS =====================

    private void gererJetons() {
        Joueur joueur = (Joueur) currentUser;
        System.out.println("Jetons disponibles : " + joueur.getJetons());
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
                if (joueur.placerJeton(nomJeu)) {
                    System.out.println("Jeton placé sur '" + jeu.getNom() + "'. "
                            + "Jetons restants : " + joueur.getJetons()
                            + " | Jetons sur ce jeu : " + joueur.getJetonsPlacesSur(nomJeu));
                } else {
                    System.out.println("Vous n'avez plus de jetons disponibles.");
                }
            }
            case 2 -> {
                if (joueur.retirerJeton(nomJeu)) {
                    System.out.println("Jeton retiré de '" + jeu.getNom() + "'. "
                            + "Jetons restants : " + joueur.getJetons()
                            + " | Jetons sur ce jeu : " + joueur.getJetonsPlacesSur(nomJeu));
                } else {
                    System.out.println("Vous n'avez aucun jeton placé sur ce jeu.");
                }
            }
            default -> System.out.println("Choix invalide.");
        }
    }

    // ===================== ACTIONS ADMIN =====================

    private void consulterInfosMembre() {
        String pseudo = lireChaine("Pseudo du membre à consulter : ");
        Membre membre = membreService.rechercherParPseudo(pseudo);

        if (membre == null) {
            System.out.println("Membre introuvable.");
            return;
        }

        System.out.println("\n--- Informations du membre ---");
        System.out.println(membre);

        if (membre instanceof Joueur joueur) {
            System.out.println("Jeux possédés : " + joueur.getJeuxPossedes().size());
            int totalHeures = joueur.getJeuxPossedes().values().stream().mapToInt(Integer::intValue).sum();
            System.out.println("Durée de jeu globale : " + totalHeures + "h");
            System.out.println("Évaluations écrites : " + evaluationService.compterEvaluationsParAuteur(pseudo));
        }
    }

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
        }
    }

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

    private String lireChaine(String message) {
        System.out.print(message);
        return scanner.nextLine().trim();
    }
}