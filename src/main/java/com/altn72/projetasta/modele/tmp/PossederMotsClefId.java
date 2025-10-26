package com.altn72.projetasta.modele.tmp;

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
public class PossederMotsClefId implements Serializable {
    private static final long serialVersionUID = -9092898847457397977L;
    @Column(name = "Id_personne", nullable = false)
    private Integer idPersonne;

    @Column(name = "Nom_mot_clef", nullable = false, length = 45)
    private String nomMotClef;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        PossederMotsClefId entity = (PossederMotsClefId) o;
        return Objects.equals(this.nomMotClef, entity.nomMotClef) &&
                Objects.equals(this.idPersonne, entity.idPersonne);
    }

    @Override
    public int hashCode() {
        return Objects.hash(nomMotClef, idPersonne);
    }

}