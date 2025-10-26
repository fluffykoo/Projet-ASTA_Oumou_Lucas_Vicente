package com.altn72.projetasta.controleur.service;

import com.altn72.projetasta.modele.Visite;
import com.altn72.projetasta.repository.VisiteRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class VisiteService {

    private final VisiteRepository visiteRepository;

    public VisiteService(VisiteRepository visiteRepository) {
        this.visiteRepository = visiteRepository;
    }

    public List<Visite> getToutesLesVisites() {
        return visiteRepository.findAll();
    }

    public Optional<Visite> getUneVisite(Integer id) {
        return visiteRepository.findById(id);
    }

    public Visite ajouterUneVisite(Visite visite) {
        return visiteRepository.save(visite);
    }

    public Optional<Visite> supprimerUneVisite(Integer id) {
        Optional<Visite> visite = visiteRepository.findById(id);
        if (visite.isPresent()) {
            visiteRepository.deleteById(id);
            return visite;
        }
        throw new IllegalStateException("Cette visite n'existe pas");
    }

    @Transactional
    public void modifierUneVisite(Integer id, Visite visiteModifiee) {
        Visite visiteToModify = visiteRepository.findById(id)
                .orElseThrow(() -> new IllegalStateException("Cette visite n'existe pas"));
        BeanUtils.copyProperties(visiteModifiee, visiteToModify, "id");
        visiteRepository.save(visiteToModify);
    }
}