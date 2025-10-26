package com.altn72.projetasta.service;

import com.altn72.projetasta.modele.Soutenance;
import com.altn72.projetasta.repository.SoutenanceRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class SoutenanceService {

    private final SoutenanceRepository soutenanceRepository;

    public SoutenanceService(SoutenanceRepository soutenanceRepository) {
        this.soutenanceRepository = soutenanceRepository;
    }

    // Récupérer toutes les soutenances
    public List<Soutenance> getSoutenances() {
        return soutenanceRepository.findAll();
    }

    // Récupérer une soutenance par son ID
    public Optional<Soutenance> getUneSoutenance(Integer idSoutenance) {
        Optional<Soutenance> soutenance = soutenanceRepository.findById(idSoutenance);
        return Optional.ofNullable(
                soutenance.orElseThrow(() ->
                        new IllegalStateException("La soutenance dont l'id est " + idSoutenance + " n'existe pas"))
        );
    }

    // Supprimer une soutenance
    @Transactional
    public void supprimerSoutenance(Integer idSoutenance) {
        Optional<Soutenance> soutenance = soutenanceRepository.findById(idSoutenance);

        if (soutenance.isPresent()) {
            soutenanceRepository.deleteById(idSoutenance);
        } else {
            throw new IllegalStateException("La soutenance dont l'id est " + idSoutenance + " n'existe pas");
        }
    }

    // Ajouter une soutenance
    @Transactional
    public void ajouterSoutenance(Soutenance soutenance) {
        soutenanceRepository.save(soutenance);
    }

    // Modifier une soutenance existante
    @Transactional
    public void modifierSoutenance(Integer idSoutenance, Soutenance soutenanceModifiee) {
        Soutenance soutenanceToModify = soutenanceRepository.findById(idSoutenance)
                .orElseThrow(() -> new IllegalStateException("La soutenance dont l'id est " + idSoutenance + " n'existe pas"));

        BeanUtils.copyProperties(soutenanceModifiee, soutenanceToModify, "id");
        soutenanceRepository.save(soutenanceToModify);
    }
}