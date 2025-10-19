/**
 * Classe Entity "bindée" avec la table "evaluation_ecole"
 * Deux sous-blocs métier :
 *  - Mémoire/Rapport : thème, note finale, commentaires
 *  - Soutenance     : date, note finale, commentaires
 */
package com.altn72.projetasta.modele;

import jakarta.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name = "evaluation_ecole")
public class EvaluationEcole {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Integer id;

    //Bloc 1 : Mémoire / Rapport
    @Column(name = "memoire_theme", length = 200, nullable = false)
    private String memoireTheme;

    @Column(name = "memoire_note_finale")
    private Double memoireNoteFinale;

    @Column(name = "memoire_commentaires", columnDefinition = "TEXT")
    private String memoireCommentaires;

    // Bloc 2 : Soutenance
    @Column(name = "soutenance_date")
    private LocalDate soutenanceDate;

    @Column(name = "soutenance_note_finale")
    private Double soutenanceNoteFinale;

    @Column(name = "soutenance_commentaires", columnDefinition = "TEXT")
    private String soutenanceCommentaires;

    // Relations
    @ManyToOne
    @JoinColumn(name = "apprenti_id", nullable = false)
    @com.fasterxml.jackson.annotation.JsonIgnoreProperties({"mission","visites","entreprise"})
    private Apprenti apprenti;

    @ManyToOne
    @JoinColumn(name = "tuteur_id")
    private TuteurEnseignant tuteurEnseignant;

    // Getters / Setters
    public Integer getId() { return id; }
    public void setId(Integer id) { this.id = id; }

    public String getMemoireTheme() { return memoireTheme; }
    public void setMemoireTheme(String memoireTheme) { this.memoireTheme = memoireTheme; }

    public Double getMemoireNoteFinale() { return memoireNoteFinale; }
    public void setMemoireNoteFinale(Double memoireNoteFinale) { this.memoireNoteFinale = memoireNoteFinale; }

    public String getMemoireCommentaires() { return memoireCommentaires; }
    public void setMemoireCommentaires(String memoireCommentaires) { this.memoireCommentaires = memoireCommentaires; }

    public LocalDate getSoutenanceDate() { return soutenanceDate; }
    public void setSoutenanceDate(LocalDate soutenanceDate) { this.soutenanceDate = soutenanceDate; }

    public Double getSoutenanceNoteFinale() { return soutenanceNoteFinale; }
    public void setSoutenanceNoteFinale(Double soutenanceNoteFinale) { this.soutenanceNoteFinale = soutenanceNoteFinale; }

    public String getSoutenanceCommentaires() { return soutenanceCommentaires; }
    public void setSoutenanceCommentaires(String soutenanceCommentaires) { this.soutenanceCommentaires = soutenanceCommentaires; }

    public Apprenti getApprenti() { return apprenti; }
    public void setApprenti(Apprenti apprenti) { this.apprenti = apprenti; }

    public TuteurEnseignant getTuteurEnseignant() { return tuteurEnseignant; }
    public void setTuteurEnseignant(TuteurEnseignant tuteurEnseignant) { this.tuteurEnseignant = tuteurEnseignant; }
}