package com.altn72.projetasta.modele;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

@Getter
@Setter
@Entity
@Table(name = "evaluation_rapport")
public class EvaluationRapport {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_evaluation")
    private Integer id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "id_personne_tuteur_enseignant", nullable = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    private TuteurEnseignant tuteurEnseignant;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "id_rapport", nullable = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    private Rapport rapport;

    @Column(name = "note_finale")
    private Double noteFinale;

    @Lob
    @Column(name = "commentaire")
    private String commentaire;
}