package com.altn72.projetasta.service;

import com.altn72.projetasta.modele.MotsClef;
import com.altn72.projetasta.repository.MotsClefRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class MotsClefService {

    private final MotsClefRepository motsClefRepository;

    public MotsClefService(MotsClefRepository motsClefRepository) {
        this.motsClefRepository = motsClefRepository;
    }

    // Récupérer tous les mots-clés
    public List<MotsClef> getMotsClefs() {
        return motsClefRepository.findAll();
    }

    // Récupérer un mot-clé par son nom
    public Optional<MotsClef> getUnMotClef(String nom) {
        Optional<MotsClef> mot = motsClefRepository.findById(nom);
        return Optional.ofNullable(
                mot.orElseThrow(() ->
                        new IllegalStateException("Le mot-clé '" + nom + "' n'existe pas"))
        );
    }

    // Supprimer un mot-clé
    @Transactional
    public void supprimerMotClef(String nom) {
        Optional<MotsClef> mot = motsClefRepository.findById(nom);

        if (mot.isPresent()) {
            motsClefRepository.deleteById(nom);
        } else {
            throw new IllegalStateException("Le mot-clé '" + nom + "' n'existe pas");
        }
    }

    // Ajouter un mot-clé
    @Transactional
    public void ajouterMotClef(MotsClef mot) {
        motsClefRepository.save(mot);
    }

    // Modifier un mot-clé existant
    @Transactional
    public void modifierMotClef(String nom, MotsClef motModifie) {
        MotsClef motToModify = motsClefRepository.findById(nom)
                .orElseThrow(() -> new IllegalStateException("Le mot-clé '" + nom + "' n'existe pas"));

        BeanUtils.copyProperties(motModifie, motToModify, "nom");
        motsClefRepository.save(motToModify);
    }
}