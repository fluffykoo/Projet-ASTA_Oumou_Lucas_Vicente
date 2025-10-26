package com.altn72.projetasta.modele;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "mots_clefs", schema = "ASTA")
public class MotsClef {
    @Id
    @Column(name = "Nom", nullable = false, length = 45)
    private String nom;

    //TODO [Reverse Engineering] generate columns from DB
}