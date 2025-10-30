package com.altn72.projetasta.dto;

import com.altn72.projetasta.modele.Apprenti;

public class ApprentiDTO {
    private Integer id;
    private String prenom;
    private String nom;
    private String entreprise;

    // Constructeur
    public ApprentiDTO(Apprenti apprenti) {
        this.id = apprenti.getId();
        if (apprenti.getPersonne() != null) {
            this.prenom = apprenti.getPersonne().getPrenom();
            this.nom = apprenti.getPersonne().getNom();
        }
        this.entreprise = (apprenti.getEntreprise() != null)
                ? apprenti.getEntreprise().getRaisonSociale()
                : "Non renseignée";
    }

    // Getters
    public Integer getId() { return id; }
    public String getPrenom() { return prenom; }
    public String getNom() { return nom; }
    public String getEntreprise() { return entreprise; }
}