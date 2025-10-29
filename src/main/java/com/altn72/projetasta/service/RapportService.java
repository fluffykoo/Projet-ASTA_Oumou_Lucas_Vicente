package com.altn72.projetasta.service;

import com.altn72.projetasta.modele.Rapport;
import com.altn72.projetasta.repository.RapportRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class RapportService {

    private final RapportRepository rapportRepository;

    public RapportService(RapportRepository rapportRepository) {
        this.rapportRepository = rapportRepository;
    }

    // Récupérer tous les rapports
    public List<Rapport> getRapports() {
        return rapportRepository.findAll();
    }

    // Récupérer un rapport par son ID
    public Optional<Rapport> getUnRapport(Integer idRapport) {
        Optional<Rapport> rapport = rapportRepository.findById(idRapport);
        return Optional.ofNullable(
                rapport.orElseThrow(() ->
                        new IllegalStateException("Le rapport dont l'id est " + idRapport + " n'existe pas"))
        );
    }

    //  Récupérer les rapports d’un apprenti sans évaluation
    public List<Rapport> getRapportsNonEvaluesPourApprenti(Integer idApprenti) {
        return rapportRepository.findRapportsNonEvalues(idApprenti);
    }

    // Supprimer un rapport
    @Transactional
    public void supprimerRapport(Integer idRapport) {
        Optional<Rapport> rapport = rapportRepository.findById(idRapport);

        if (rapport.isPresent()) {
            rapportRepository.deleteById(idRapport);
        } else {
            throw new IllegalStateException("Le rapport dont l'id est " + idRapport + " n'existe pas");
        }
    }

    // Ajouter un rapport
    @Transactional
    public void ajouterRapport(Rapport rapport) {
        rapportRepository.save(rapport);
    }

    // Modifier un rapport existant
    @Transactional
    public void modifierRapport(Integer idRapport, Rapport rapportModifie) {
        Rapport rapportToModify = rapportRepository.findById(idRapport)
                .orElseThrow(() -> new IllegalStateException("Le rapport dont l'id est " + idRapport + " n'existe pas"));

        BeanUtils.copyProperties(rapportModifie, rapportToModify, "id");
        rapportRepository.save(rapportToModify);
    }
    // Compter le total de rapports
    public long countTotal() {
        return rapportRepository.count();
    }

    // Compter les rapports non évalués
    public long countNonEvalues() {
        return rapportRepository.countRapportsNonEvalues();
    }
}