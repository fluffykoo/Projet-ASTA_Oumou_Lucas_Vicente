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
@Table(name = "mots_clefs")
public class MotsClef {

    @Id
    @Column(name = "Nom", length = 45)
    private String nom;

    // Relation ManyToMany avec Apprenti via la table d’association "posseder_mots_clefs"
    @ManyToMany(mappedBy = "motsClefs")
    private List<Apprenti> apprentis;
}