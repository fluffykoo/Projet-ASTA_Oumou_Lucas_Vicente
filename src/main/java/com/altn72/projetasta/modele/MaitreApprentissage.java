package com.altn72.projetasta.modele;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

@Getter
@Setter
@Entity
@Table(name = "maitre_apprentissage", schema = "ASTA")
public class MaitreApprentissage {
    @Id
    @Column(name = "Id_personne", nullable = false)
    private Integer id;

    @MapsId
    @OneToOne(fetch = FetchType.LAZY, optional = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JoinColumn(name = "Id_personne", nullable = false)
    private Personne personne;

    @Column(name = "Poste", length = 45)
    private String poste;

}