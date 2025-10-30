package com.altn72.projetasta.dto;

import com.altn72.projetasta.modele.Entreprise;

public class EntrepriseDTO {
    private Integer id;
    private String raisonSociale;
    private String adresse;
    private int nombreApprentis;
    private int nombreVisites;

    public EntrepriseDTO(Entreprise entreprise) {
        this.id = entreprise.getId();
        this.raisonSociale = entreprise.getRaisonSociale();
        this.adresse = entreprise.getAdresse();

        this.nombreApprentis = (entreprise.getApprentis() != null)
                ? entreprise.getApprentis().size() : 0;

        this.nombreVisites = (entreprise.getVisites() != null)
                ? entreprise.getVisites().size() : 0;
    }

    // Getters
    public Integer getId() { return id; }
    public String getRaisonSociale() { return raisonSociale; }
    public String getAdresse() { return adresse; }
    public int getNombreApprentis() { return nombreApprentis; }
    public int getNombreVisites() { return nombreVisites; }
}