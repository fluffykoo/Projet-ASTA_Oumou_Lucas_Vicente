package com.altn72.projetasta.service;

import com.altn72.projetasta.modele.Entreprise;
import com.altn72.projetasta.repository.EntrepriseRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class EntrepriseService {

    private final EntrepriseRepository entrepriseRepository;

    public EntrepriseService(EntrepriseRepository entrepriseRepository) {
        this.entrepriseRepository = entrepriseRepository;
    }

    // Récupérer toutes les entreprises
    public List<Entreprise> getEntreprises() {
        return entrepriseRepository.findAll();
    }

    // Récupérer une entreprise par son ID
    public Optional<Entreprise> getUneEntreprise(Integer idEntreprise) {
        Optional<Entreprise> entreprise = entrepriseRepository.findById(idEntreprise);
        return Optional.ofNullable(
                entreprise.orElseThrow(() ->
                        new IllegalStateException("L'entreprise dont l'id est " + idEntreprise + " n'existe pas"))
        );
    }

    // Supprimer une entreprise
    @Transactional
    public void supprimerEntreprise(Integer idEntreprise) {
        Optional<Entreprise> entreprise = entrepriseRepository.findById(idEntreprise);

        if (entreprise.isPresent()) {
            entrepriseRepository.deleteById(idEntreprise);
        } else {
            throw new IllegalStateException("L'entreprise dont l'id est " + idEntreprise + " n'existe pas");
        }
    }

    // Ajouter une nouvelle entreprise
    @Transactional
    public void ajouterEntreprise(Entreprise entreprise) {
        entrepriseRepository.save(entreprise);
    }

    // Modifier une entreprise existante
    @Transactional
    public void modifierEntreprise(Integer idEntreprise, Entreprise entrepriseModifiee) {
        Entreprise entrepriseToModify = entrepriseRepository.findById(idEntreprise)
                .orElseThrow(() -> new IllegalStateException("L'entreprise dont l'id est " + idEntreprise + " n'existe pas"));

        BeanUtils.copyProperties(entrepriseModifiee, entrepriseToModify, "id");
        entrepriseRepository.save(entrepriseToModify);
    }
}