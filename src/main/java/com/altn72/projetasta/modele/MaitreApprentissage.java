/**
 * Classe Entity qui est "bindée" avec la table "maitre_apprentissage"
 * chaque ligne de la table maitre_apprentissage correspond à une instance de cette classe Entity
 *
 */

package com.altn72.projetasta.modele;

import jakarta.persistence.*;

@Entity
@Table(name = "maitre_apprentissage")
public class MaitreApprentissage {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Integer id;

    @Column(name = "nom", nullable = false, length = 100)
    private String nom;

    @Column(name = "prenom", nullable = false, length = 100)
    private String prenom;

    @Column(name = "poste", length = 100)
    private String poste;

    @Column(name = "email", length = 150)
    private String email;

    @Column(name = "telephone", length = 50)
    private String telephone;

    @Column(name = "remarques", length = 255)
    private String remarques;

    @ManyToOne
    @JoinColumn(name = "entreprise_id")
    private Entreprise entreprise;

    // Getters & Setters
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getPrenom() {
        return prenom;
    }

    public void setPrenom(String prenom) {
        this.prenom = prenom;
    }

    public String getPoste() {
        return poste;
    }

    public void setPoste(String poste) {
        this.poste = poste;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getTelephone() {
        return telephone;
    }

    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }

    public String getRemarques() {
        return remarques;
    }

    public void setRemarques(String remarques) {
        this.remarques = remarques;
    }

    public Entreprise getEntreprise() {
        return entreprise;
    }

    public void setEntreprise(Entreprise entreprise) {
        this.entreprise = entreprise;
    }
}