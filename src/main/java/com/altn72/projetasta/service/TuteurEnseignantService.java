package com.altn72.projetasta.controleur.service;

import com.altn72.projetasta.modele.TuteurEnseignant;
import com.altn72.projetasta.repository.TuteurEnseignantRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class TuteurEnseignantService {

    private final TuteurEnseignantRepository tuteurEnseignantRepository;
    private final PasswordEncoder passwordEncoder;

    public TuteurEnseignantService(TuteurEnseignantRepository tuteurEnseignantRepository,
                                   PasswordEncoder passwordEncoder) {
        this.tuteurEnseignantRepository = tuteurEnseignantRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public List<TuteurEnseignant> getTousLesTuteurs() {
        return tuteurEnseignantRepository.findAll();
    }

    public Optional<TuteurEnseignant> getUnTuteur(Integer id) {
        return tuteurEnseignantRepository.findById(id);
    }

    public TuteurEnseignant ajouterUnTuteur(TuteurEnseignant tuteur) {
        // encodage du mot de passe avant sauvegarde
        String hashedPassword = passwordEncoder.encode(tuteur.getPassword());
        tuteur.setPassword(hashedPassword);
        tuteur.setEnabled(true);  // sécurité : compte actif par défaut
        return tuteurEnseignantRepository.save(tuteur);
    }

    public void modifierUnTuteur(Integer id, TuteurEnseignant tuteur) {
        tuteur.setId(id);
        // si le mot de passe a changé, le réencoder
        if (tuteur.getPassword() != null && !tuteur.getPassword().isEmpty()) {
            tuteur.setPassword(passwordEncoder.encode(tuteur.getPassword()));
        }
        tuteurEnseignantRepository.save(tuteur);
    }

    public Optional<TuteurEnseignant> supprimerUnTuteur(Integer id) {
        Optional<TuteurEnseignant> tuteur = tuteurEnseignantRepository.findById(id);
        tuteur.ifPresent(tuteurEnseignantRepository::delete);
        return tuteur;
    }
}