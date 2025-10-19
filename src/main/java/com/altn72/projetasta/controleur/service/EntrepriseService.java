package com.altn72.projetasta.controleur.service;

import com.altn72.projetasta.modele.Entreprise;
import com.altn72.projetasta.modele.repository.EntrepriseRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class EntrepriseService {

    private final EntrepriseRepository entrepriseRepository;

    public EntrepriseService(EntrepriseRepository entrepriseRepository) {
        this.entrepriseRepository = entrepriseRepository;
    }

    public List<Entreprise> getToutesLesEntreprises() {
        return entrepriseRepository.findAll();
    }

    public Optional<Entreprise> getUneEntreprise(Integer id) {
        return entrepriseRepository.findById(id);
    }

    public Entreprise ajouterUneEntreprise(Entreprise entreprise) {
        return entrepriseRepository.save(entreprise);
    }

    public Optional<Entreprise> supprimerUneEntreprise(Integer id) {
        Optional<Entreprise> entreprise = entrepriseRepository.findById(id);
        if (entreprise.isPresent()) {
            entrepriseRepository.deleteById(id);
            return entreprise;
        }
        throw new IllegalStateException("Cette entreprise n'existe pas");
    }

    @Transactional
    public void modifierUneEntreprise(Integer id, Entreprise entrepriseModifiee) {
        Entreprise entrepriseToModify = entrepriseRepository.findById(id)
                .orElseThrow(() -> new IllegalStateException("Cette entreprise n'existe pas"));

        BeanUtils.copyProperties(entrepriseModifiee, entrepriseToModify, "id");
        entrepriseRepository.save(entrepriseToModify);
    }
}