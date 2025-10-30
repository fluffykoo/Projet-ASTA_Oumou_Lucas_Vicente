package com.altn72.projetasta.security;

import com.altn72.projetasta.modele.TuteurEnseignant;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.Collections;

public class TuteurDetails implements UserDetails {

    private final TuteurEnseignant tuteur;

    public TuteurDetails(TuteurEnseignant tuteur) {
        this.tuteur = tuteur;
    }

    //  Données d'identité
    public String getPrenom() {
        return tuteur.getPersonne().getPrenom();
    }

    public String getNom() {
        return tuteur.getPersonne().getNom();
    }

    public String getEmail() {
        return tuteur.getPersonne().getAdresseElectronique();
    }


    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return Collections.emptyList(); // Aucun rôle spécifique
    }

    @Override
    public String getPassword() {
        return tuteur.getPassword();
    }

    @Override
    public String getUsername() {
        return tuteur.getIdentifiant(); // identifiant de connexion
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return tuteur.isEnabled();
    }

    // Accès direct à l’objet Tuteur
    public TuteurEnseignant getTuteur() {
        return tuteur;
    }
}