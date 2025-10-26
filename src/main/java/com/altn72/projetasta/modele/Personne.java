package com.altn72.projetasta.modele;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "personne", schema = "ASTA")
public class Personne {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "Id", nullable = false)
    private Integer id;

    @Column(name = "Prenom", length = 45)
    private String prenom;

    @Column(name = "Nom", length = 45)
    private String nom;

    @Column(name = "adresse_electronique", length = 100)
    private String adresseElectronique;

    @Column(name = "telephone", length = 20)
    private String telephone;

}