# Plateforme d'évaluation collaborative de jeux vidéo

Application Java en mode console pour la gestion et l'évaluation de jeux vidéo.  
Projet POO — Polytech Paris-Saclay · 2025-2026

---

## Lancement

**Prérequis :** Java JDK 11+, aucune dépendance externe.

```bash
# Compiler
javac -d out -sourcepath src src/Main.java

# Lancer
java -cp out Main
```

Au démarrage, charger le catalogue via l'option **Charger CSV** (`data/vg_data.csv`).  
Un compte `admin` est créé automatiquement.

---

## Structure

```
src/
├── Main.java
├── console/Console.java          # Interface utilisateur
├── model/                        # Entités métier
│   ├── Membre / Joueur / Testeur / Administrateur
│   ├── JeuVideo / Support
│   └── Evaluation / Test
├── service/                      # Logique applicative
│   ├── JeuService.java           # Catalogue + chargement CSV
│   ├── MembreService.java
│   └── EvaluationService.java
└── data/vg_data.csv
```

---

## Rôles

| Rôle | Accès |
|---|---|
| **Invité** | Consulter le catalogue, s'inscrire |
| **Joueur** | Écrire des évaluations, gérer ses jetons |
| **Testeur** | Publier des tests officiels (+5 jetons) |
| **Administrateur** | Modération, promotion, blocage |

Hiérarchie : `Membre → Joueur → Testeur → Administrateur`

---

## Règles métier clés

- Inscription : **3 jetons** offerts automatiquement
- Un joueur ne peut évaluer que les jeux qu'il **possède**
- Un testeur doit avoir suffisamment de **temps de jeu** pour publier un test
- **1 seul test** autorisé par support (PC, PS, Xbox…)
- Les jetons placés sur un jeu sont **restitués** à la publication du test

---

> Les données ne persistent pas entre deux exécutions (pas de base de données).
