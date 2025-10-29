package com.altn72.projetasta.modele;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@Entity
@Table(name = "apprenti")
public class Apprenti {

    @Id
    @Column(name = "Id_personne", nullable = false)
    private Integer id;

    @OneToOne(fetch = FetchType.LAZY)
    @MapsId // clé primaire partagée avec Personne
    @JoinColumn(name = "Id_personne", nullable = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    private Personne personne;

    @Column(name = "programme", length = 45)
    private String programme;

    @Column(name = "annee_academique", length = 45)
    private String anneeAcademique;

    @Column(name = "majeure", length = 45)
    private String majeure;

    @Column(name = "metier_cible", length = 45)
    private String metierCible;

    @Lob
    @Column(name = "commentaire_mission")
    private String commentaireMission;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "Id_entreprise", nullable = true)
    @OnDelete(action = OnDeleteAction.CASCADE)
    private Entreprise entreprise; // renommé

    //Un apprenti peut avoir plusieurs rapports
    @OneToMany(mappedBy = "apprenti", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Rapport> rapports;

    // Un apprenti participe à plusieurs soutenances.
    @OneToMany(mappedBy = "apprenti", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Soutenance> soutenances;

    // Un apprenti peut être associé à plusieurs mots-clés et un même mot-clé peut appartenir à plusieurs apprentis.
    @ManyToMany
    @JoinTable(
            name = "posseder_mots_clefs",
            joinColumns = @JoinColumn(name = "Id_personne"),
            inverseJoinColumns = @JoinColumn(name = "Nom_mot_clef")
    )
    private List<MotsClef> motsClefs;

    //relation apprenti visite
    @OneToMany(mappedBy = "apprenti", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Visite> visites = new ArrayList<>();

    //relation avec son tuteur enseignant
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "Id_tuteur_enseignant")
    private TuteurEnseignant tuteurEnseignant;
}
