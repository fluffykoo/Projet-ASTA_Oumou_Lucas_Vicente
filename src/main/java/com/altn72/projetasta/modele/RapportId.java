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
public class RapportId implements Serializable {
    private static final long serialVersionUID = -4551430032673516959L;
    @Column(name = "Id_rapport", nullable = false)
    private Integer idRapport;

    @Column(name = "Id_apprenti", nullable = false)
    private Integer idApprenti;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        RapportId entity = (RapportId) o;
        return Objects.equals(this.idRapport, entity.idRapport) &&
                Objects.equals(this.idApprenti, entity.idApprenti);
    }

    @Override
    public int hashCode() {
        return Objects.hash(idRapport, idApprenti);
    }

}