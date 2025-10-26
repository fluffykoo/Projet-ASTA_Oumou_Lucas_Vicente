package com.altn72.projetasta.modele.tmp;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.Hibernate;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.Objects;

@Getter
@Setter
@Embeddable
public class VisiteId implements Serializable {
    private static final long serialVersionUID = 1700879208282621992L;
    @Column(name = "Id_personne_tuteur_enseignant", nullable = false)
    private Integer idPersonneTuteurEnseignant;

    @Column(name = "Id_entreprise", nullable = false)
    private Integer idEntreprise;

    @Column(name = "Date_de_visite", nullable = false)
    private LocalDate dateDeVisite;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        VisiteId entity = (VisiteId) o;
        return Objects.equals(this.idPersonneTuteurEnseignant, entity.idPersonneTuteurEnseignant) &&
                Objects.equals(this.idEntreprise, entity.idEntreprise) &&
                Objects.equals(this.dateDeVisite, entity.dateDeVisite);
    }

    @Override
    public int hashCode() {
        return Objects.hash(idPersonneTuteurEnseignant, idEntreprise, dateDeVisite);
    }

}