package service;

import model.Evaluation;
import model.Test;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Service pour gérer les évaluations de joueurs et les tests de testeurs.
 */
public class EvaluationService {

    private List<Evaluation> evaluations;
    private List<Test> tests;

    public EvaluationService() {
        evaluations = new ArrayList<>();
        tests = new ArrayList<>();
    }

    // ===================== ÉVALUATIONS =====================

    /**
     * Ajoute une évaluation.
     */
    public void ajouterEvaluation(Evaluation eval) {
        evaluations.add(eval);
    }

    /**
     * Récupère toutes les évaluations d'un jeu (tous supports),
     * triées par meilleure note puis par date la plus ancienne.
     */
    public List<Evaluation> getEvaluationsParJeu(String nomJeu) {
        return evaluations.stream()
                .filter(e -> e.getNomJeu().equalsIgnoreCase(nomJeu))
                .sorted(Comparator.comparingInt(Evaluation::getNoteGlobale).reversed()
                        .thenComparing(Evaluation::getDate))
                .collect(Collectors.toList());
    }

    /**
     * Récupère les évaluations d'un jeu sur un support précis.
     */
    public List<Evaluation> getEvaluationsParJeuEtSupport(String nomJeu, String support) {
        return evaluations.stream()
                .filter(e -> e.getNomJeu().equalsIgnoreCase(nomJeu))
                .filter(e -> e.getSupport().equalsIgnoreCase(support))
                .sorted(Comparator.comparingInt(Evaluation::getNoteGlobale).reversed()
                        .thenComparing(Evaluation::getDate))
                .collect(Collectors.toList());
    }

    /**
     * Compte les évaluations écrites par un membre.
     */
    public int compterEvaluationsParAuteur(String pseudo) {
        return (int) evaluations.stream()
                .filter(e -> e.getAuteurPseudo().equalsIgnoreCase(pseudo))
                .count();
    }

    /**
     * Compte le total de votes positifs reçus par un auteur.
     */
    public int compterVotesPositifsAuteur(String pseudo) {
        return evaluations.stream()
                .filter(e -> e.getAuteurPseudo().equalsIgnoreCase(pseudo))
                .mapToInt(Evaluation::getVotesPositifs)
                .sum();
    }

    /**
     * Supprime une évaluation (admin).
     */
    public boolean supprimerEvaluation(Evaluation eval) {
        return evaluations.remove(eval);
    }

    // ===================== TESTS =====================

    /**
     * Ajoute un test.
     */
    public void ajouterTest(Test test) {
        tests.add(test);
    }

    /**
     * Récupère le test d'un jeu sur un support (au plus un par support).
     */
    public Test getTestParJeuEtSupport(String nomJeu, String support) {
        return tests.stream()
                .filter(t -> t.getNomJeu().equalsIgnoreCase(nomJeu))
                .filter(t -> t.getSupport().equalsIgnoreCase(support))
                .findFirst()
                .orElse(null);
    }

    /**
     * Récupère tous les tests d'un jeu.
     */
    public List<Test> getTestsParJeu(String nomJeu) {
        return tests.stream()
                .filter(t -> t.getNomJeu().equalsIgnoreCase(nomJeu))
                .collect(Collectors.toList());
    }

    /**
     * Compte les tests écrits par un testeur.
     */
    public int compterTestsParAuteur(String pseudo) {
        return (int) tests.stream()
                .filter(t -> t.getAuteurPseudo().equalsIgnoreCase(pseudo))
                .count();
    }
}