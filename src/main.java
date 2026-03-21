import model.Administrateur;
import model.JeuVideo;
import model.Joueur;
import service.JeuService;
import service.MembreService;

    public class Main {
        public static void main(String[] args) {

            MembreService membreService = new MembreService();
            JeuService jeuService = new JeuService();

            Joueur joueur1 = new Joueur("syrine");
            Administrateur admin1 = new Administrateur("admin");

            membreService.ajouterMembre(joueur1);
            membreService.ajouterMembre(admin1);

            JeuVideo jeu1 = new JeuVideo("FIFA 23", "Sport", "EA Sports", "E");
            JeuVideo jeu2 = new JeuVideo("Minecraft", "Sandbox", "Mojang", "E10+");

            jeuService.ajouterJeu(jeu1);
            jeuService.ajouterJeu(jeu2);

            System.out.println("=== MEMBRES ===");
            membreService.afficherMembres();

            System.out.println("\n=== JEUX ===");
            jeuService.afficherJeux();
        }
    }

