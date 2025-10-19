package com.altn72.projetasta.modele;

import jakarta.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name = "visite")
public class Visite {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Integer id;

    @Column(name = "date_visite", nullable = false)
    private LocalDate dateVisite;

    @Enumerated(EnumType.STRING)
    @Column(name = "format", nullable = false, length = 20)
    private FormatVisite format;

    @Column(name = "commentaire", columnDefinition = "TEXT")
    private String commentaire;

    @ManyToOne
    @JoinColumn(name = "apprenti_id", nullable = false)
    @com.fasterxml.jackson.annotation.JsonIgnoreProperties("visites")
    private Apprenti apprenti;

//    @ManyToOne
//    @JoinColumn(name = "tuteur_id", nullable = false)
//    private TuteurEnseignant tuteurEnseignant;

    @ManyToOne
    @JoinColumn(name = "maitre_id")
    private MaitreApprentissage maitreApprentissage;

    // Getters & Setters
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public LocalDate getDateVisite() {
        return dateVisite;
    }

    public void setDateVisite(LocalDate dateVisite) {
        this.dateVisite = dateVisite;
    }

    public FormatVisite getFormat() {
        return format;
    }

    public void setFormat(FormatVisite format) {
        this.format = format;
    }

    public String getCommentaire() {
        return commentaire;
    }

    public void setCommentaire(String commentaire) {
        this.commentaire = commentaire;
    }

    public Apprenti getApprenti() {
        return apprenti;
    }

    public void setApprenti(Apprenti apprenti) {
        this.apprenti = apprenti;
    }

//    public TuteurEnseignant getTuteurEnseignant() {
//        return tuteurEnseignant;
//    }
//
//    public void setTuteurEnseignant(TuteurEnseignant tuteurEnseignant) {
//        this.tuteurEnseignant = tuteurEnseignant;
//    }

    public MaitreApprentissage getMaitreApprentissage() {
        return maitreApprentissage;
    }

    public void setMaitreApprentissage(MaitreApprentissage maitreApprentissage) {
        this.maitreApprentissage = maitreApprentissage;
    }
}