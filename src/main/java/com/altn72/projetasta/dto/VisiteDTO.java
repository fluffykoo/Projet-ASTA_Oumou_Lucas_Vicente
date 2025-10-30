package com.altn72.projetasta.dto;

import com.altn72.projetasta.modele.Visite;
import java.time.LocalDate;

public class VisiteDTO {

    private Integer id;
    private LocalDate dateVisite;
    private String formatVisite;
    private String apprentiNomComplet;
    private String entreprise;
    private String tuteurEnseignant;
    private String commentaire;

    public VisiteDTO(Visite visite) {
        this.id = visite.getId();
        this.dateVisite = visite.getDateVisite();
        this.formatVisite = (visite.getFormatVisite() != null)
                ? visite.getFormatVisite().name()
                : "Non précisé";

        if (visite.getApprenti() != null && visite.getApprenti().getPersonne() != null) {
            var p = visite.getApprenti().getPersonne();
            this.apprentiNomComplet = p.getPrenom() + " " + p.getNom();
        } else {
            this.apprentiNomComplet = "Non attribué";
        }

        this.entreprise = (visite.getEntreprise() != null)
                ? visite.getEntreprise().getRaisonSociale()
                : "Non renseignée";

        this.tuteurEnseignant = (visite.getTuteurEnseignant() != null
                && visite.getTuteurEnseignant().getPersonne() != null)
                ? visite.getTuteurEnseignant().getPersonne().getPrenom() + " " +
                visite.getTuteurEnseignant().getPersonne().getNom()
                : "Non défini";

        this.commentaire = (visite.getCommentaire() != null) ? visite.getCommentaire() : "";
    }

    // Getters
    public Integer getId() { return id; }
    public LocalDate getDateVisite() { return dateVisite; }
    public String getFormatVisite() { return formatVisite; }
    public String getApprentiNomComplet() { return apprentiNomComplet; }
    public String getEntreprise() { return entreprise; }
    public String getTuteurEnseignant() { return tuteurEnseignant; }
    public String getCommentaire() { return commentaire; }
}