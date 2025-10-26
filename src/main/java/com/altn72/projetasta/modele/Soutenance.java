package com.altn72.projetasta.modele;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import java.time.LocalDate;

@Getter
@Setter
@Entity
@Table(name = "soutenance")
public class Soutenance {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) // auto-incrémentation de l'id de soutenance
    @Column(name = "Id_soutenance", nullable = false)
    private Integer id;

    // Une soutenance est toujours liée à un tuteur enseignant
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "Id_personne_tuteur_enseignant", nullable = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    private TuteurEnseignant tuteurEnseignant;

    //  Une soutenance est toujours liée à un maître d’apprentissage
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "Id_personne_maitre_apprentissage", nullable = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    private MaitreApprentissage maitreApprentissage;

    // Une soutenance est toujours liée à un apprenti
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "Id_personne_apprenti", nullable = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    private Apprenti apprenti;

    @Column(name = "Date_soutenance", nullable = false)
    private LocalDate dateSoutenance;

    @Column(name = "Note_finale")
    private Double noteFinale;
}