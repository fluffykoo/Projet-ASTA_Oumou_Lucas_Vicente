package com.altn72.projetasta.modele;

import jakarta.persistence.*;

@Entity
@Table(name = "formation")
public class Formation {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Integer id;

    @Column(name = "intitule", nullable = false, length = 100)
    private String intitule;

    @Column(name = "description", length = 255)
    private String description;

    @Column(name = "duree")
    private Integer duree; // en heures

    // Constructeurs
    public Formation() {}

    public Formation(String intitule, String description, Integer duree) {
        this.intitule = intitule;
        this.description = description;
        this.duree = duree;
    }

    //Getters & Setters
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getIntitule() {
        return intitule;
    }

    public void setIntitule(String intitule) {
        this.intitule = intitule;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Integer getDuree() {
        return duree;
    }

    public void setDuree(Integer duree) {
        this.duree = duree;
    }
}