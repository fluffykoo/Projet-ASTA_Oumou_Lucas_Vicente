package com.altn72.projetasta.controleur.service;

import com.altn72.projetasta.modele.TuteurEnseignant;
import com.altn72.projetasta.modele.repository.TuteurEnseignantRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class TuteurEnseignantService {

    private final TuteurEnseignantRepository tuteurEnseignantRepository;

    public TuteurEnseignantService(TuteurEnseignantRepository tuteurEnseignantRepository) {
        this.tuteurEnseignantRepository = tuteurEnseignantRepository;
    }

    public List<TuteurEnseignant> getTousLesTuteurs() {
        return tuteurEnseignantRepository.findAll();
    }

    public Optional<TuteurEnseignant> getUnTuteur(Integer id) {
        return tuteurEnseignantRepository.findById(id);
    }

    public TuteurEnseignant ajouterUnTuteur(TuteurEnseignant tuteur) {
        return tuteurEnseignantRepository.save(tuteur);
    }

    public void modifierUnTuteur(Integer id, TuteurEnseignant tuteur) {
        tuteur.setId(id);
        tuteurEnseignantRepository.save(tuteur);
    }

    public Optional<TuteurEnseignant> supprimerUnTuteur(Integer id) {
        Optional<TuteurEnseignant> tuteur = tuteurEnseignantRepository.findById(id);
        tuteur.ifPresent(tuteurEnseignantRepository::delete);
        return tuteur;
    }
}