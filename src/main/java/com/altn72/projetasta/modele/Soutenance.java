package com.altn72.projetasta.modele;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

@Getter
@Setter
@Entity
@Table(name = "soutenance", schema = "ASTA")
public class Soutenance {
    @EmbeddedId
    private SoutenanceId id;

    @MapsId("idPersonneTuteurEnseignant")
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JoinColumn(name = "Id_personne_tuteur_enseignant", nullable = false)
    private TuteurEnseignant idPersonneTuteurEnseignant;

    @MapsId("idPersonneMaitreApprentissage")
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JoinColumn(name = "Id_personne_maitre_apprentissage", nullable = false)
    private MaitreApprentissage idPersonneMaitreApprentissage;

    @MapsId("idPersonneApprenti")
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JoinColumn(name = "Id_personne_apprenti", nullable = false)
    private Apprenti idPersonneApprenti;

    @Column(name = "Note_finale")
    private Double noteFinale;

}