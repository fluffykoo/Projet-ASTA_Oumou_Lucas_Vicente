package com.altn72.projetasta.service;

import com.altn72.projetasta.modele.EvaluationRapport;
import com.altn72.projetasta.repository.EvaluationRapportRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class EvaluationRapportService {

    private final EvaluationRapportRepository evaluationRapportRepository;

    public EvaluationRapportService(EvaluationRapportRepository evaluationRapportRepository) {
        this.evaluationRapportRepository = evaluationRapportRepository;
    }

    // Récupérer toutes les évaluations
    public List<EvaluationRapport> getEvaluations() {
        return evaluationRapportRepository.findAll();
    }

    // Récupérer une évaluation par son id
    public Optional<EvaluationRapport> getUneEvaluation(Integer id) {
        return Optional.ofNullable(
                evaluationRapportRepository.findById(id)
                        .orElseThrow(() -> new IllegalStateException("L'évaluation dont l'id est " + id + " n'existe pas"))
        );
    }

    // Supprimer une évaluation
    @Transactional
    public void supprimerEvaluation(Integer id) {
        Optional<EvaluationRapport> evaluation = evaluationRapportRepository.findById(id);

        if (evaluation.isPresent()) {
            evaluationRapportRepository.deleteById(id);
        } else {
            throw new IllegalStateException("L'évaluation dont l'id est " + id + " n'existe pas");
        }
    }

    // Ajouter une évaluation
    @Transactional
    public void ajouterEvaluation(EvaluationRapport evaluation) {
        evaluationRapportRepository.save(evaluation);
    }

    // Modifier une évaluation
    @Transactional
    public void modifierEvaluation(Integer id, EvaluationRapport evaluationModifiee) {
        EvaluationRapport evaluationToModify = evaluationRapportRepository.findById(id)
                .orElseThrow(() -> new IllegalStateException("L'évaluation dont l'id est " + id + " n'existe pas"));

        BeanUtils.copyProperties(evaluationModifiee, evaluationToModify, "id");
        evaluationRapportRepository.save(evaluationToModify);
    }
}