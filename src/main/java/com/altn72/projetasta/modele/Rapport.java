package com.altn72.projetasta.modele;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

@Getter
@Setter
@Entity
@Table(name = "rapport", schema = "ASTA")
public class Rapport {
    @EmbeddedId
    private RapportId id;

    @MapsId("idApprenti")
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JoinColumn(name = "Id_apprenti", nullable = false)
    private Apprenti idApprenti;

    @Column(name = "Sujet", length = 45)
    private String sujet;

}