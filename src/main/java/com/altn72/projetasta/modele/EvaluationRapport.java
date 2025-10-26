package com.altn72.projetasta.modele;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

@Getter
@Setter
@Entity
@Table(name = "evaluation_rapport", schema = "ASTA")
public class EvaluationRapport {
    @EmbeddedId
    private EvaluationRapportId id;

    @MapsId("idPersonneTuteurEnseignant")
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JoinColumn(name = "Id_personne_tuteur_enseignant", nullable = false)
    private TuteurEnseignant idPersonneTuteurEnseignant;

    @MapsId("idRapport")
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JoinColumn(name = "Id_rapport", nullable = false, referencedColumnName = "Id_rapport")
    private Rapport idRapport;

    @Column(name = "note_final")
    private Double noteFinal;

    @Lob
    @Column(name = "commentaire")
    private String commentaire;

}