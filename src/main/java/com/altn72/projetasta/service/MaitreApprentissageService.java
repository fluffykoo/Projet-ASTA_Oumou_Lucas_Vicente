package com.altn72.projetasta.service;

import com.altn72.projetasta.modele.MaitreApprentissage;
import com.altn72.projetasta.repository.MaitreApprentissageRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class MaitreApprentissageService {

    private final MaitreApprentissageRepository maitreApprentissageRepository;

    public MaitreApprentissageService(MaitreApprentissageRepository maitreApprentissageRepository) {
        this.maitreApprentissageRepository = maitreApprentissageRepository;
    }

    // Récupérer tous les maîtres d’apprentissage
    public List<MaitreApprentissage> getMaitres() {
        return maitreApprentissageRepository.findAll();
    }

    // Récupérer un maître d’apprentissage par son ID
    public Optional<MaitreApprentissage> getUnMaitre(Integer idMaitre) {
        Optional<MaitreApprentissage> maitre = maitreApprentissageRepository.findById(idMaitre);
        return Optional.ofNullable(
                maitre.orElseThrow(() ->
                        new IllegalStateException("Le maître d'apprentissage dont l'id est " + idMaitre + " n'existe pas"))
        );
    }

    // Supprimer un maître d’apprentissage
    @Transactional
    public void supprimerMaitre(Integer idMaitre) {
        Optional<MaitreApprentissage> maitre = maitreApprentissageRepository.findById(idMaitre);

        if (maitre.isPresent()) {
            maitreApprentissageRepository.deleteById(idMaitre);
        } else {
            throw new IllegalStateException("Le maître d'apprentissage dont l'id est " + idMaitre + " n'existe pas");
        }
    }

    // Ajouter un maître d’apprentissage
    @Transactional
    public void ajouterMaitre(MaitreApprentissage maitreApprentissage) {
        maitreApprentissageRepository.save(maitreApprentissage);
    }

    // Modifier un maître d’apprentissage existant
    @Transactional
    public void modifierMaitre(Integer idMaitre, MaitreApprentissage maitreModifie) {
        MaitreApprentissage maitreToModify = maitreApprentissageRepository.findById(idMaitre)
                .orElseThrow(() -> new IllegalStateException("Le maître d'apprentissage dont l'id est " + idMaitre + " n'existe pas"));

        BeanUtils.copyProperties(maitreModifie, maitreToModify, "id", "personne");
        maitreApprentissageRepository.save(maitreToModify);
    }
}