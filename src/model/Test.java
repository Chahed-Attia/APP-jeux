
package model;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Test {
    private String auteurPseudo;
    private String nomJeu;
    private String support;
    private String texte;
    private String version;
    private LocalDate date;

    private Map<String, Integer> notesParCategorie;

    private List<String> pointsForts;
    private List<String> pointsFaibles;

    private String conditionsTest;

    public Test(String auteurPseudo, String nomJeu, String support, String texte, String version) {
        this.auteurPseudo = auteurPseudo;
        this.nomJeu = nomJeu;
        this.support = support;
        this.texte = texte;
        this.version = version;
        this.date = LocalDate.now();
        this.notesParCategorie = new HashMap<>();
        this.pointsForts = new ArrayList<>();
        this.pointsFaibles = new ArrayList<>();
        this.conditionsTest = "";
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

    public LocalDate getDate() {
        return date;
    }

    public Map<String, Integer> getNotesParCategorie() {
        return notesParCategorie;
    }

    public List<String> getPointsForts() {
        return pointsForts;
    }

    public List<String> getPointsFaibles() {
        return pointsFaibles;
    }

    public String getConditionsTest() {
        return conditionsTest;
    }

    // === Actions ===

    public void ajouterNote(String categorie, int note) {
        notesParCategorie.put(categorie, note);
    }

    public void ajouterPointFort(String point) {
        pointsForts.add(point);
    }

    public void ajouterPointFaible(String point) {
        pointsFaibles.add(point);
    }

    public void setConditionsTest(String conditions) {
        this.conditionsTest = conditions;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("[").append(date).append("] Test par ").append(auteurPseudo);
        sb.append(" - ").append(nomJeu).append(" (").append(support).append(")");
        sb.append(" v").append(version).append("\n");
        sb.append("  ").append(texte).append("\n");

        if (!notesParCategorie.isEmpty()) {
            sb.append("  Notes: ");
            notesParCategorie.forEach((cat, note) ->
                    sb.append(cat).append("=").append(note).append("/20 "));
            sb.append("\n");
        }

        if (!pointsForts.isEmpty()) {
            sb.append("  Points forts: ").append(String.join(", ", pointsForts)).append("\n");
        }

        if (!pointsFaibles.isEmpty()) {
            sb.append("  Points faibles: ").append(String.join(", ", pointsFaibles)).append("\n");
        }

        if (!conditionsTest.isEmpty()) {
            sb.append("  Conditions: ").append(conditionsTest).append("\n");
        }

        return sb.toString();
    }
}