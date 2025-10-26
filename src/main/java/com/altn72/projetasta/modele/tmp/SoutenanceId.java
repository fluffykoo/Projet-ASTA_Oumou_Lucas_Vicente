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
public class SoutenanceId implements Serializable {
    private static final long serialVersionUID = 6459669203303853330L;
    @Column(name = "Id_soutenance", nullable = false)
    private Integer idSoutenance;

    @Column(name = "Id_personne_tuteur_enseignant", nullable = false)
    private Integer idPersonneTuteurEnseignant;

    @Column(name = "Id_personne_maitre_apprentissage", nullable = false)
    private Integer idPersonneMaitreApprentissage;

    @Column(name = "Id_personne_apprenti", nullable = false)
    private Integer idPersonneApprenti;

    @Column(name = "Date_soutenance", nullable = false)
    private LocalDate dateSoutenance;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        SoutenanceId entity = (SoutenanceId) o;
        return Objects.equals(this.idPersonneTuteurEnseignant, entity.idPersonneTuteurEnseignant) &&
                Objects.equals(this.idPersonneMaitreApprentissage, entity.idPersonneMaitreApprentissage) &&
                Objects.equals(this.idPersonneApprenti, entity.idPersonneApprenti) &&
                Objects.equals(this.idSoutenance, entity.idSoutenance) &&
                Objects.equals(this.dateSoutenance, entity.dateSoutenance);
    }

    @Override
    public int hashCode() {
        return Objects.hash(idPersonneTuteurEnseignant, idPersonneMaitreApprentissage, idPersonneApprenti, idSoutenance, dateSoutenance);
    }

}