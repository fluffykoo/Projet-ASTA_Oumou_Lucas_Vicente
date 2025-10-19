package com.altn72.projetasta.controleur.service;

import com.altn72.projetasta.modele.MaitreApprentissage;
import com.altn72.projetasta.modele.repository.MaitreApprentissageRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class MaitreApprentissageService {

    private final MaitreApprentissageRepository maitreApprentissageRepository;

    public MaitreApprentissageService(MaitreApprentissageRepository maitreApprentissageRepository) {
        this.maitreApprentissageRepository = maitreApprentissageRepository;
    }

    //Récupérer tous les maîtres d'apprentissage
    public List<MaitreApprentissage> getTousLesMaitres() {
        return maitreApprentissageRepository.findAll();
    }

    //Récupérer un maître par son ID
    public Optional<MaitreApprentissage> getUnMaitre(Integer id) {
        return maitreApprentissageRepository.findById(id);
    }

    //Ajouter un maître
    public MaitreApprentissage ajouterUnMaitre(MaitreApprentissage maitre) {
        return maitreApprentissageRepository.save(maitre);
    }

    //Supprimer un maître
    public Optional<MaitreApprentissage> supprimerUnMaitre(Integer id) {
        Optional<MaitreApprentissage> maitre = maitreApprentissageRepository.findById(id);
        if (maitre.isPresent()) {
            maitreApprentissageRepository.deleteById(id);
            return maitre;
        }
        throw new IllegalStateException("Ce maître d'apprentissage n'existe pas");
    }

    //Modifier un maître
    @Transactional //pour assurer une mise à jour propre en base de donnée
    public void modifierUnMaitre(Integer id, MaitreApprentissage maitreModifie) {
        MaitreApprentissage maitreToModify = maitreApprentissageRepository.findById(id)
                .orElseThrow(() -> new IllegalStateException("Ce maître d'apprentissage n'existe pas"));

        BeanUtils.copyProperties(maitreModifie, maitreToModify, "id");
        maitreApprentissageRepository.save(maitreToModify);
    }
}