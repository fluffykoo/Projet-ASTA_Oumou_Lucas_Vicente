package com.altn72.projetasta.modele;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import java.time.LocalDate;

@Getter
@Setter
@Entity
@Table(name = "annee_academique")
public class AnneeAcademique {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(unique = true, nullable = false, length = 15)
    private String code; // ex: "2025-2026"

    @Column(length = 100)
    private String libelle; // ex: "Année universitaire 2025-2026"

    @Column(nullable = false)
    private boolean enCours = true;

    private LocalDate dateDebut;
    private LocalDate dateFin;

    // Constructeur  pour JPA
    public AnneeAcademique() {}

    // Constructeur
    public AnneeAcademique(String code, boolean enCours) {
        this.code = code;
        this.libelle = "Année universitaire " + code;
        this.enCours = enCours;
    }

    @Override
    public String toString() {
        return libelle + (enCours ? " (en cours)" : " (archivée)");
    }
}