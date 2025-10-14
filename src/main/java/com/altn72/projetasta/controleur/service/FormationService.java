package com.altn72.projetasta.controleur.service;

import com.altn72.projetasta.modele.Formation;
import com.altn72.projetasta.modele.repository.FormationRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class FormationService {

    private final FormationRepository formationRepository;

    public FormationService(FormationRepository formationRepository) {
        this.formationRepository = formationRepository;
    }

    public List<Formation> getToutesLesFormations() {
        return formationRepository.findAll();
    }

    public Optional<Formation> getUneFormation(Integer id) {
        return formationRepository.findById(id);
    }

    public Formation ajouterUneFormation(Formation formation) {
        return formationRepository.save(formation);
    }

    public Optional<Formation> supprimerUneFormation(Integer id) {
        Optional<Formation> formation = formationRepository.findById(id);
        if (formation.isPresent()) {
            formationRepository.deleteById(id);
            return formation;
        }
        throw new IllegalStateException("Cette formation n'existe pas");
    }

    @Transactional
    public void modifierUneFormation(Integer id, Formation formationModifiee) {
        Formation formationToModify = formationRepository.findById(id)
                .orElseThrow(() -> new IllegalStateException("Cette formation n'existe pas"));

        BeanUtils.copyProperties(formationModifiee, formationToModify, "id");
        formationRepository.save(formationToModify);
    }

    @Transactional
    public Optional<Formation> patchUneFormation(Integer id, Formation formationModifiee) {
        Optional<Formation> formation = formationRepository.findById(id);

        if (formation.isPresent()) {
            Formation f = formation.get();
            if (formationModifiee.getIntitule() != null) f.setIntitule(formationModifiee.getIntitule());
            if (formationModifiee.getDescription() != null) f.setDescription(formationModifiee.getDescription());
            if (formationModifiee.getDuree() != null) f.setDuree(formationModifiee.getDuree());

            formationRepository.save(f);
            return Optional.of(f);
        } else {
            throw new IllegalStateException("Cette formation n'existe pas");
        }
    }
}