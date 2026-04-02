package model;

import java.time.LocalDate;

/**
 * Représente une évaluation d'un joueur pour un jeu vidéo sur un support donné.
 */
public class Evaluation {
    private String auteurPseudo;
    private String nomJeu;
    private String support;
    private String texte;
    private String version;
    private int noteGlobale; // de 1 à 10
    private LocalDate date;

    // Votes d'utilité par d'autres joueurs
    private int votesPositifs;
    private int votesNeutres;
    private int votesNegatifs;

    // Signalée par un testeur
    private boolean signalee;

    public Evaluation(String auteurPseudo, String nomJeu, String support, String texte, String version, int noteGlobale) {
        this.auteurPseudo = auteurPseudo;
        this.nomJeu = nomJeu;
        this.support = support;
        this.texte = texte;
        this.version = version;
        this.noteGlobale = noteGlobale;
        this.date = LocalDate.now();
        this.votesPositifs = 0;
        this.votesNeutres = 0;
        this.votesNegatifs = 0;
        this.signalee = false;
    }

    // === Getters ===

    public String getAuteurPseudo() {
        return auteurPseudo;
    }

    public String getNomJeu() {
        return nomJeu;
    }

    public String getSupport() {
        return support;
    }

    public String getTexte() {
        return texte;
    }

    public String getVersion() {
        return version;
    }

    public int getNoteGlobale() {
        return noteGlobale;
    }

    public LocalDate getDate() {
        return date;
    }

    public int getVotesPositifs() {
        return votesPositifs;
    }

    public int getVotesNeutres() {
        return votesNeutres;
    }

    public int getVotesNegatifs() {
        return votesNegatifs;
    }

    public boolean isSignalee() {
        return signalee;
    }

    // === Actions ===

    public void ajouterVotePositif() {
        votesPositifs++;
    }

    public void ajouterVoteNeutre() {
        votesNeutres++;
    }

    public void ajouterVoteNegatif() {
        votesNegatifs++;
    }

    public void signaler() {
        signalee = true;
    }

    @Override
    public String toString() {
        return "[" + date + "] " + auteurPseudo + " - Note: " + noteGlobale + "/10"
                + " | Votes: +" + votesPositifs + " /" + votesNeutres + " -" + votesNegatifs
                + (signalee ? " [SIGNALÉE]" : "")
                + "\n  \"" + texte + "\"";
    }
}