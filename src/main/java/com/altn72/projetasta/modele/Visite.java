package com.altn72.projetasta.modele;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "visite", schema = "ASTA")
public class Visite {
    @EmbeddedId
    private VisiteId id;

    @Lob
    @Column(name = "format_visite")
    private String formatVisite;

    @Lob
    @Column(name = "commentaire")
    private String commentaire;

}