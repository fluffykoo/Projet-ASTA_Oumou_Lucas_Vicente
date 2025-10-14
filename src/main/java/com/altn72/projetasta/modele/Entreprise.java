package com.altn72.projetasta.modele;

import jakarta.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "entreprise")
public class Entreprise {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(nullable = false, length = 150)
    private String nom;

    @Column(length = 255)
    private String adresse;

    @Column(length = 100)
    private String ville;

    @Column(length = 50)
    private String telephone;

    @Column(length = 100)
    private String email;

    @OneToMany(mappedBy = "entreprise")
    @com.fasterxml.jackson.annotation.JsonIgnoreProperties("entreprise")
    private List<Apprenti> apprentis;
    // Constructeurs
    public Entreprise() {}

    public Entreprise(String nom, String adresse, String ville, String telephone, String email) {
        this.nom = nom;
        this.adresse = adresse;
        this.ville = ville;
        this.telephone = telephone;
        this.email = email;
    }

    // Getters / Setters
    public Integer getId() { return id; }
    public void setId(Integer id) { this.id = id; }

    public String getNom() { return nom; }
    public void setNom(String nom) { this.nom = nom; }

    public String getAdresse() { return adresse; }
    public void setAdresse(String adresse) { this.adresse = adresse; }

    public String getVille() { return ville; }
    public void setVille(String ville) { this.ville = ville; }

    public String getTelephone() { return telephone; }
    public void setTelephone(String telephone) { this.telephone = telephone; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public List<Apprenti> getApprentis() { return apprentis; }
    public void setApprentis(List<Apprenti> apprentis) { this.apprentis = apprentis; }


}