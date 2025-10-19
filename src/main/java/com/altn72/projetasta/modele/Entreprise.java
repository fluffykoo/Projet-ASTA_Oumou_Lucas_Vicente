package com.altn72.projetasta.modele;

import jakarta.persistence.*;
import java.util.List;

@Entity
@Table(name = "entreprise")
public class Entreprise {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "raison_sociale", nullable = false, length = 150)
    private String raisonSociale;

    @Column(name = "adresse", length = 255)
    private String adresse;

    @Column(name = "infos_utiles_acces", length = 255)
    private String infosUtilesAcces;

    @OneToMany(mappedBy = "entreprise", cascade = CascadeType.ALL)
    private List<Apprenti> apprentis;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getRaisonSociale() {
        return raisonSociale;
    }

    public void setRaisonSociale(String raisonSociale) {
        this.raisonSociale = raisonSociale;
    }

    public String getAdresse() {
        return adresse;
    }

    public void setAdresse(String adresse) {
        this.adresse = adresse;
    }

    public String getInfosUtilesAcces() {
        return infosUtilesAcces;
    }

    public void setInfosUtilesAcces(String infosUtilesAcces) {
        this.infosUtilesAcces = infosUtilesAcces;
    }

    public List<Apprenti> getApprentis() {
        return apprentis;
    }

    public void setApprentis(List<Apprenti> apprentis) {
        this.apprentis = apprentis;
    }
}