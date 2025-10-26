package com.altn72.projetasta.modele;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.Hibernate;

import java.io.Serializable;
import java.util.Objects;

@Getter
@Setter
@Embeddable
public class EvaluationRapportId implements Serializable {
    private static final long serialVersionUID = -546973126583287126L;
    @Column(name = "Id_personne_tuteur_enseignant", nullable = false)
    private Integer idPersonneTuteurEnseignant;

    @Column(name = "Id_rapport", nullable = false)
    private Integer idRapport;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        EvaluationRapportId entity = (EvaluationRapportId) o;
        return Objects.equals(this.idPersonneTuteurEnseignant, entity.idPersonneTuteurEnseignant) &&
                Objects.equals(this.idRapport, entity.idRapport);
    }

    @Override
    public int hashCode() {
        return Objects.hash(idPersonneTuteurEnseignant, idRapport);
    }

}