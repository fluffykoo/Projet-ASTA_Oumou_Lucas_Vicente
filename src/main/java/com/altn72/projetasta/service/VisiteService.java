package com.altn72.projetasta.service;

import com.altn72.projetasta.modele.Visite;
import com.altn72.projetasta.repository.VisiteRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class VisiteService {

    private final VisiteRepository visiteRepository;

    public VisiteService(VisiteRepository visiteRepository) {
        this.visiteRepository = visiteRepository;
    }

    // Récupérer toutes les visites
    public List<Visite> getVisites() {
        return visiteRepository.findAll();
    }

    // Récupérer une visite par son ID
    public Optional<Visite> getUneVisite(Integer idVisite) {
        Optional<Visite> visite = visiteRepository.findById(idVisite);
        return Optional.ofNullable(
                visite.orElseThrow(() ->
                        new IllegalStateException("La visite dont l'id est " + idVisite + " n'existe pas"))
        );
    }

    // Supprimer une visite
    @Transactional
    public void supprimerVisite(Integer idVisite) {
        Optional<Visite> visite = visiteRepository.findById(idVisite);

        if (visite.isPresent()) {
            visiteRepository.deleteById(idVisite);
        } else {
            throw new IllegalStateException("La visite dont l'id est " + idVisite + " n'existe pas");
        }
    }

    // Ajouter une visite
    @Transactional
    public void ajouterVisite(Visite visite) {
        visiteRepository.save(visite);
    }

    // Modifier une visite existante
    @Transactional
    public void modifierVisite(Integer idVisite, Visite visiteModifiee) {
        Visite visiteToModify = visiteRepository.findById(idVisite)
                .orElseThrow(() -> new IllegalStateException("La visite dont l'id est " + idVisite + " n'existe pas"));

        BeanUtils.copyProperties(visiteModifiee, visiteToModify, "id");
        visiteRepository.save(visiteToModify);
    }
}