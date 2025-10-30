package com.altn72.projetasta.modele;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@Entity
@Table(name = "rapport")
public class Rapport {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "Id_rapport")
    private Integer id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "Id_apprenti", nullable = false)
    @OnDelete(action = OnDeleteAction.CASCADE)//si l'apprenti est supprimé, ses rapports le sont aussi
    private Apprenti apprenti;

    @Column(name = "Sujet", length = 100)
    private String sujet;

    //relation avec apprenti
    @OneToMany(mappedBy = "rapport", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<EvaluationRapport> evaluations = new ArrayList<>();

    // Relation avec EvaluationRapport
    @OneToMany(mappedBy = "rapport", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<EvaluationRapport> evaluationsRapport = new ArrayList<>();
}
