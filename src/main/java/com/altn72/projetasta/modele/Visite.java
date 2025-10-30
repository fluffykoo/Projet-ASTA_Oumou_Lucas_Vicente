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
@Table(name = "visite")
public class Visite {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne(fetch = FetchType.LAZY)//pour éviter de surcharger la memoire et charger toutes les infos d'un coup
    @JoinColumn(name = "id_personne_tuteur_enseignant", nullable = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    private TuteurEnseignant tuteurEnseignant;

    @ManyToOne(fetch = FetchType.LAZY)//pour éviter de surcharger la memoire et charger toutes les infos d'un coup
    @JoinColumn(name = "id_apprenti", nullable = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    private Apprenti apprenti;

    @ManyToOne(fetch = FetchType.LAZY)//pour éviter de surcharger la memoire et charger toutes les infos d'un coup
    @JoinColumn(name = "id_entreprise", nullable = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    private Entreprise entreprise;

    @Column(name = "date_visite", nullable = false)
    private LocalDate dateVisite;

    @Enumerated(EnumType.STRING)
    @Column(name = "format_visite", nullable = false)
    private FormatVisite formatVisite;

    @Lob
    private String commentaire;

    public enum FormatVisite {
        visio, presentiel
    }
}
