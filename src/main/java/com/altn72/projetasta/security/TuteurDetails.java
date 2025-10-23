package com.altn72.projetasta.security;

import com.altn72.projetasta.modele.TuteurEnseignant;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;

public class TuteurDetails implements UserDetails {

    private final TuteurEnseignant tuteur;

    public TuteurDetails(TuteurEnseignant tuteur) {
        this.tuteur = tuteur;
    }

    public TuteurEnseignant getTuteur() {
        return tuteur;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(() -> "ROLE_TUTEUR");
    }

    @Override
    public String getPassword() {
        return tuteur.getPassword();
    }

    @Override
    public String getUsername() {
        return tuteur.getEmail();
    }
    public String getNom() {
        return tuteur.getNom();
    }

    public String getPrenom() {
        return tuteur.getPrenom();
    }

    @Override
    public boolean isAccountNonExpired() { return true; }

    @Override
    public boolean isAccountNonLocked() { return true; }

    @Override
    public boolean isCredentialsNonExpired() { return true; }

    @Override
    public boolean isEnabled() { return tuteur.isEnabled(); }

}