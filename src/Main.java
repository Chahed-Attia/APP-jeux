import console.Console;
import model.Administrateur;
import service.EvaluationService;
import service.JeuService;
import service.MembreService;

public class Main {
    public static void main(String[] args) {

        // Initialiser les 3 services
        JeuService jeuService = new JeuService();
        MembreService membreService = new MembreService();
        EvaluationService evaluationService = new EvaluationService();

        // Charger les jeux depuis le CSV
        jeuService.chargerJeuVideo("data/vg_data.csv");

        // Créer l'administrateur par défaut
        Administrateur admin = new Administrateur("admin");
        membreService.ajouterMembre(admin);

        // Lancer la console avec les 3 services
        Console console = new Console(jeuService, membreService, evaluationService);
        console.lancer();
    }
}