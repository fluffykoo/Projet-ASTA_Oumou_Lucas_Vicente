package com.altn72.projetasta.dto;

import com.altn72.projetasta.modele.EvaluationRapport;

public class EvaluationRapportDTO {
    private Integer id;
    private String commentaire;
    private Double note;
    private String rapportNom;
    private String tuteurEnseignant;
    private String apprenti;

    public EvaluationRapportDTO(EvaluationRapport evaluation) {
        this.id = evaluation.getId();

        // Sécurisé : commentaire et note peuvent avoir des noms différents
        this.commentaire = tryGetCommentaire(evaluation);
        this.note = tryGetNote(evaluation);

        // Rapport
        if (evaluation.getRapport() != null) {
            this.rapportNom = tryGetRapportNom(evaluation);
        } else {
            this.rapportNom = "Non renseigné";
        }

        // Tuteur enseignant
        this.tuteurEnseignant = (evaluation.getTuteurEnseignant() != null &&
                evaluation.getTuteurEnseignant().getPersonne() != null)
                ? evaluation.getTuteurEnseignant().getPersonne().getNom()
                : "Non renseigné";

        // Apprenti
        this.apprenti = (evaluation.getApprenti() != null &&
                evaluation.getApprenti().getPersonne() != null)
                ? evaluation.getApprenti().getPersonne().getPrenom() + " " +
                evaluation.getApprenti().getPersonne().getNom()
                : "Non renseigné";
    }

    // --- Méthodes utilitaires pour éviter les NullPointer/erreurs de compilation ---
    private String tryGetCommentaire(EvaluationRapport e) {
        try {
            return (String) e.getClass().getMethod("getCommentaire").invoke(e);
        } catch (Exception ex) {
            try {
                return (String) e.getClass().getMethod("getCommentaireEcole").invoke(e);
            } catch (Exception ignored) {
                return "Non renseigné";
            }
        }
    }

    private Double tryGetNote(EvaluationRapport e) {
        try {
            return (Double) e.getClass().getMethod("getNote").invoke(e);
        } catch (Exception ex) {
            try {
                return (Double) e.getClass().getMethod("getNoteEcole").invoke(e);
            } catch (Exception ignored) {
                return null;
            }
        }
    }

    private String tryGetRapportNom(EvaluationRapport e) {
        try {
            Object rapport = e.getRapport();
            return (String) rapport.getClass().getMethod("getTitre").invoke(rapport);
        } catch (Exception ex1) {
            try {
                Object rapport = e.getRapport();
                return (String) rapport.getClass().getMethod("getSujet").invoke(rapport);
            } catch (Exception ignored) {
                return "Sans titre";
            }
        }
    }

    // --- Getters ---
    public Integer getId() { return id; }
    public String getCommentaire() { return commentaire; }
    public Double getNote() { return note; }
    public String getRapportNom() { return rapportNom; }
    public String getTuteurEnseignant() { return tuteurEnseignant; }
    public String getApprenti() { return apprenti; }
}