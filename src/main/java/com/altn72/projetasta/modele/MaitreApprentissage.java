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
@Table(name = "maitre_apprentissage")
public class MaitreApprentissage {

    @Id
    @Column(name = "Id_personne", nullable = false)
    private Integer id;

    @OneToOne(fetch = FetchType.LAZY)
    @MapsId // partage la même clé primaire que Personne
    @JoinColumn(name = "Id_personne", nullable = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    private Personne personne;

    @Column(name = "Poste", length = 45)
    private String poste;

    // Relation inverse : un maître d’apprentissage peut avoir plusieurs soutenances
    @OneToMany(mappedBy = "maitreApprentissage", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Soutenance> soutenances; // liste des soutenances liées à ce maître
}
