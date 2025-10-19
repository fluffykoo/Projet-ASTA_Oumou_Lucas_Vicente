package com.altn72.projetasta.modele;

import jakarta.persistence.*;

@Entity
@Table(name = "mission")
public class Mission {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Integer id;

    @Column(name = "mots_cles", nullable = false, length = 255)
    private String motsCles;

    @Column(name = "metier_cible", length = 150)
    private String metierCible;

    @Column(name = "commentaires", columnDefinition = "TEXT")
    private String commentaires;

    @OneToOne
    @JoinColumn(name = "apprenti_id", nullable = false)
    @com.fasterxml.jackson.annotation.JsonIgnoreProperties({"entreprise"})
    private Apprenti apprenti;

    public Mission() {}

    // Getters & Setters
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getMotsCles() {
        return motsCles;
    }

    public void setMotsCles(String motsCles) {
        this.motsCles = motsCles;
    }

    public String getMetierCible() {
        return metierCible;
    }

    public void setMetierCible(String metierCible) {
        this.metierCible = metierCible;
    }

    public String getCommentaires() {
        return commentaires;
    }

    public void setCommentaires(String commentaires) {
        this.commentaires = commentaires;
    }

    public Apprenti getApprenti() {
        return apprenti;
    }

    public void setApprenti(Apprenti apprenti) {
        this.apprenti = apprenti;
    }
}