package com.altn72.tpfilrouge.modele.tmp;

import lombok.*;

//version avec Lombok
//@Data : contient @Getter , @Setter et  @ToString mais aussi @EqualsAndHashCode et @RequiredArgsConstructor
@Getter
@Setter
@AllArgsConstructor
@ToString
public class Programmeur {
    private Integer idProgrammeur;
    private String nom;
    private String prenom;
    private String adresse;
    private String langageExpertise;
    private String livrePrefere;
    private Double salaire;
}
//Version sans Lombok
////classe Programmeur avec ses attirbuts
//public class Programmeur {
//    private Integer idProgrammeur;
//    private String nom;
//    private String prenom;
//    private String adresse;
//    private String langageExpertise;
//    private String livrePrefere;
//    private Double salaire;
//
//    //getters et setters
//    public Integer getIdProgrammeur() {
//        return idProgrammeur;
//    }
//
//    public void setIdProgrammeur(Integer idProgrammeur) {
//        this.idProgrammeur = idProgrammeur;
//    }
//
//    public String getNom() {
//        return nom;
//    }
//
//    public void setNom(String nom) {
//        this.nom = nom;
//    }
//
//    public String getPrenom() {
//        return prenom;
//    }
//
//    public void setPrenom(String prenom) {
//        this.prenom = prenom;
//    }
//
//    public String getAdresse() {
//        return adresse;
//    }
//
//    public void setAdresse(String adresse) {
//        this.adresse = adresse;
//    }
//
//    public String getLangageExpertise() {
//        return langageExpertise;
//    }
//
//    public void setLangageExpertise(String langageExpertise) {
//        this.langageExpertise = langageExpertise;
//    }
//
//    public String getLivrePrefere() {
//        return livrePrefere;
//    }
//
//    public void setLivrePrefere(String livrePrefere) {
//        this.livrePrefere = livrePrefere;
//    }
//
//    public double getSalaire() {
//        return salaire;
//    }
//
//    public void setSalaire(Double salaire) {
//        this.salaire = salaire;
//    }
//
//    //constructeur
//    public Programmeur(Integer idProgrammeur, String nom, String prenom, String adresse, String langageExpertise, String livrePrefere, Double salaire) {
//        this.idProgrammeur = idProgrammeur;
//        this.nom = nom;
//        this.prenom = prenom;
//        this.adresse = adresse;
//        this.langageExpertise = langageExpertise;
//        this.livrePrefere = livrePrefere;
//        this.salaire = salaire;
//    }
//
//    //override de toString()
//    @Override
//    public String toString() {
//        return "Le programmeur s'appelle " + nom +  prenom + ". Elle/Il vit à " + adresse + ". Son langage  d'expertise est " + langageExpertise + ", son livre préféré est " + livrePrefere + " et son salaire est de " + salaire + " euros .";
//    }
//}
