package com.altn72.projetasta.service;

import com.altn72.projetasta.modele.TuteurEnseignant;
import com.altn72.projetasta.repository.TuteurEnseignantRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
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

    // Récupérer tous les tuteurs enseignants
    public List<TuteurEnseignant> getTuteurs() {
        return tuteurEnseignantRepository.findAll();
    }

    // Récupérer un tuteur enseignant par son ID
    public Optional<TuteurEnseignant> getUnTuteur(Integer idTuteur) {
        Optional<TuteurEnseignant> tuteur = tuteurEnseignantRepository.findById(idTuteur);
        return Optional.ofNullable(
                tuteur.orElseThrow(() ->
                        new IllegalStateException("Le tuteur dont l'id est " + idTuteur + " n'existe pas"))
        );
    }
    public Optional<TuteurEnseignant> getByIdentifiant(String identifiant) {
        return tuteurEnseignantRepository.findByIdentifiant(identifiant);
    }

    // Supprimer un tuteur enseignant
    @Transactional
    public void supprimerTuteur(Integer idTuteur) {
        if (tuteurEnseignantRepository.existsById(idTuteur)) {
            tuteurEnseignantRepository.deleteById(idTuteur);
        } else {
            throw new IllegalStateException("Le tuteur dont l'id est " + idTuteur + " n'existe pas");
        }
    }

    // Ajouter un nouveau tuteur enseignant
    @Transactional
    public void ajouterTuteur(TuteurEnseignant tuteur) {
        if (tuteur.getPersonne() != null) {
            String identifiant = genererIdentifiant(tuteur.getPersonne().getPrenom(), tuteur.getPersonne().getNom());
            tuteur.setIdentifiant(identifiant);
        }
        tuteur.setPassword(passwordEncoder.encode(tuteur.getPassword()));
        tuteurEnseignantRepository.save(tuteur);
    }

    // Modifier un tuteur enseignant existant
    @Transactional
    public void modifierTuteur(Integer idTuteur, TuteurEnseignant tuteurModifie) {
        TuteurEnseignant tuteurExistant = tuteurEnseignantRepository.findById(idTuteur)
                .orElseThrow(() -> new IllegalStateException("Le tuteur dont l'id est " + idTuteur + " n'existe pas"));

        BeanUtils.copyProperties(tuteurModifie, tuteurExistant, "id", "personne", "password");

        // On ne re-hash le mot de passe que s’il a été modifié
        if (tuteurModifie.getPassword() != null && !tuteurModifie.getPassword().isBlank()) {
            tuteurExistant.setPassword(passwordEncoder.encode(tuteurModifie.getPassword()));
        }

        tuteurEnseignantRepository.save(tuteurExistant);
    }

    private String genererIdentifiant(String prenom, String nom) {
        if (prenom == null || nom == null) return "tuteur";
        String id = (prenom.charAt(0) + nom).toLowerCase()
                .replaceAll("[^a-z]", ""); // nettoie accents/symboles
        return id.length() > 20 ? id.substring(0, 20) : id;
    }

}