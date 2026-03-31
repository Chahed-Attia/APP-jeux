import model.JeuVideo;
import model.Membre;
import service.JeuService;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {

        // Charger les données de jeux video  depuis les fichiers
        JeuService gameService = new JeuService();
        gameService.chargerJeuVideo("data/vg_data.csv");
        Membre currentUser = null;

        while (true) {
            if (currentUser == null) {
                menuInvite();
            }
        }


            /*

            MembreService membreService = new MembreService();
            JeuService jeuService = new JeuService();

            Joueur joueur1 = new Joueur("syrine");
            Administrateur admin1 = new Administrateur("admin");

            membreService.ajouterMembre(joueur1);
            membreService.ajouterMembre(admin1);


            System.out.println("=== MEMBRES ===");
            membreService.afficherMembres();

            System.out.println("\n=== JEUX ===");
            jeuService.afficherJeux();
             */
    }
/*
    public static void menuInvite() {
        System.out.println("=== MENU INVITE ===");
        System.out.println("1. S'inscrire");
        System.out.println("2. Consulter les evaluations disponibles");
        int choix = lireEntier("Votre choix : ");

        switch (choix) {
            case 1 -> seConnecter();
            case 2 -> inscrireJoueur();
            case 3 -> rechercherJeu();
            case 4 -> afficherInfosJeu();
            case 0 -> quitter = true;
            default -> System.out.println("Choix invalide.");
        }

        // Lire l'entrée de l'utilisateur et agir en conséquence
    }

    private int lireEntier(String message) {
        Scanner scanner = new Scanner(System.in);
        while (true) {
            try {
                System.out.print(message);
                return Integer.parseInt(scanner.nextLine());
            } catch (NumberFormatException e) {
                System.out.println("Veuillez entrer un nombre valide.");
            }
        }
    }*/
}

