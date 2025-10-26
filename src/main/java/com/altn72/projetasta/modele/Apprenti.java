package com.altn72.projetasta.modele;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;
import org.springframework.data.jpa.repository.JpaRepository;

@Getter
@Setter
@Entity
@Table(name = "apprenti", schema = "ASTA")
public class Apprenti {
    @Id
    @Column(name = "Id_personne", nullable = false)
    private Integer id;

    @MapsId
    @OneToOne(fetch = FetchType.LAZY, optional = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JoinColumn(name = "Id_personne", nullable = false)
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
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JoinColumn(name = "Id_entreprise")
    private Entreprise idEntreprise;

}