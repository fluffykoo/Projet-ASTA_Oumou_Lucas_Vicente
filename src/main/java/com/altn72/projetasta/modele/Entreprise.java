package com.altn72.projetasta.modele;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import java.util.List;

@Getter
@Setter
@Entity
@Table(name = "entreprise")
public class Entreprise {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "Id", nullable = false)
    private Integer id;

    @Column(name = "raison_sociale", length = 100, nullable = false)
    private String raisonSociale; //nom de l’entreprise

    @Column(name = "adresse", length = 150)
    private String adresse;

    // Une entreprise peut avoir plusieurs apprentis
    @OneToMany(mappedBy = "entreprise", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Apprenti> apprentis;

    // Une entreprise peut être visitée plusieurs fois par différents tuteurs
    @OneToMany(mappedBy = "entreprise", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Visite> visites;
}