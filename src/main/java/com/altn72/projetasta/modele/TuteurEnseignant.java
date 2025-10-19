package com.altn72.projetasta.modele;

import jakarta.persistence.*;

@Entity
@Table(name = "tuteur_enseignant")
public class TuteurEnseignant {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Integer id;

    @Column(name = "nom", nullable = false, length = 100)
    private String nom;

    @Column(name = "prenom", nullable = false, length = 100)
    private String prenom;

    @Column(name = "email", nullable = false, length = 150, unique = true)
    private String email;

    @Column(name = "telephone", length = 20)
    private String telephone;

    @Column(name = "remarques", columnDefinition = "TEXT")
    private String remarques;

    // Getters & Setters
    public Integer getId() { return id; }
    public void setId(Integer id) { this.id = id; }

    public String getNom() { return nom; }
    public void setNom(String nom) { this.nom = nom; }

    public String getPrenom() { return prenom; }
    public void setPrenom(String prenom) { this.prenom = prenom; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public String getTelephone() { return telephone; }
    public void setTelephone(String telephone) { this.telephone = telephone; }

    public String getRemarques() { return remarques; }
    public void setRemarques(String remarques) { this.remarques = remarques; }
}