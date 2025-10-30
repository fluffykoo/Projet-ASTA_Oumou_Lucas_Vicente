package com.altn72.projetasta.modele;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;


import java.util.List;

@Getter
@Setter
@Entity
@Table(name = "tuteur_enseignant")
public class TuteurEnseignant {

    @Id
    @Column(name = "Id_personne")
    private Integer id; // même clé que Personne

    @OneToOne(fetch = FetchType.LAZY)
    @MapsId // partage la clé primaire avec Personne
    @JoinColumn(name = "Id_personne", nullable = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    private Personne personne;

    @Column(name = "identifiant", length = 50)
    private String identifiant;

    @Column(name = "password", length = 255)
    private String password;

    @Column(name = "enabled", nullable = false)
    private boolean enabled;

    // Relations vers autres tables

    // Un tuteur enseignant peut effectuer plusieurs visites
    @OneToMany(mappedBy = "tuteurEnseignant", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Visite> visites; // liste des visites faites par ce tuteur

    // Un tuteur enseignant peut participer à plusieurs soutenances
    @OneToMany(mappedBy = "tuteurEnseignant", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Soutenance> soutenances; // liste des soutenances encadrées par ce tuteur
}