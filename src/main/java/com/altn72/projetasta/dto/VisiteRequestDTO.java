package com.altn72.projetasta.dto;

import com.altn72.projetasta.modele.Visite;
import java.time.LocalDate;

public class VisiteRequestDTO {

    private Integer idApprenti;
    private Integer idEntreprise;
    private Integer idTuteurEnseignant;
    private LocalDate dateVisite;
    private String formatVisite; // "visio" ou "presentiel"
    private String commentaire;

    // Getters / Setters
    public Integer getIdApprenti() { return idApprenti; }
    public void setIdApprenti(Integer idApprenti) { this.idApprenti = idApprenti; }

    public Integer getIdEntreprise() { return idEntreprise; }
    public void setIdEntreprise(Integer idEntreprise) { this.idEntreprise = idEntreprise; }

    public Integer getIdTuteurEnseignant() { return idTuteurEnseignant; }
    public void setIdTuteurEnseignant(Integer idTuteurEnseignant) { this.idTuteurEnseignant = idTuteurEnseignant; }

    public LocalDate getDateVisite() { return dateVisite; }
    public void setDateVisite(LocalDate dateVisite) { this.dateVisite = dateVisite; }

    public String getFormatVisite() { return formatVisite; }
    public void setFormatVisite(String formatVisite) { this.formatVisite = formatVisite; }

    public String getCommentaire() { return commentaire; }
    public void setCommentaire(String commentaire) { this.commentaire = commentaire; }

    // Conversion vers entité
    public Visite toEntity() {
        Visite v = new Visite();
        v.setDateVisite(this.dateVisite);
        v.setFormatVisite(Visite.FormatVisite.valueOf(this.formatVisite));
        v.setCommentaire(this.commentaire);
        return v;
    }
}