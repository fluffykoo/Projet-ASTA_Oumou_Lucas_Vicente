package com.altn72.projetasta.service;

import com.altn72.projetasta.modele.Apprenti;
import com.altn72.projetasta.modele.EvaluationRapport;
import com.altn72.projetasta.modele.Rapport;
import com.altn72.projetasta.repository.ApprentiRepository;
import com.altn72.projetasta.repository.EvaluationRapportRepository;
import com.altn72.projetasta.repository.RapportRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class EvaluationRapportService {

    @Autowired
    private EvaluationRapportRepository evaluationRapportRepository;

    @Autowired
    private RapportRepository rapportRepository;

    @Autowired
    private ApprentiRepository apprentiRepository;

    //Récupérer toutes les évaluations
    public List<EvaluationRapport> getEvaluations() {
        return evaluationRapportRepository.findAll();
    }

    //Récupérer une évaluation par son ID
    public Optional<EvaluationRapport> getUneEvaluation(Integer idEvaluation) {
        return evaluationRapportRepository.findById(idEvaluation);
    }

    //  Ajouter une évaluation
    @Transactional
    public void ajouterEvaluation(EvaluationRapport evaluation) {
        Rapport rapport = rapportRepository.findById(evaluation.getRapport().getId())
                .orElseThrow(() -> new IllegalStateException("Rapport introuvable"));

        Apprenti apprenti = apprentiRepository.findById(rapport.getApprenti().getId())
                .orElseThrow(() -> new IllegalStateException("Apprenti introuvable"));

        evaluation.setRapport(rapport);
        evaluation.setApprenti(apprenti);

        evaluationRapportRepository.save(evaluation);
    }
    @Transactional
    public void modifierEvaluation(Integer idEvaluation, EvaluationRapport evaluationModifiee) {
        EvaluationRapport evaluationExistante = evaluationRapportRepository.findById(idEvaluation)
                .orElseThrow(() -> new IllegalStateException("Évaluation introuvable avec l’ID : " + idEvaluation));

        // Copie des champs modifiables
        evaluationExistante.setNoteFinale(evaluationModifiee.getNoteFinale());
        evaluationExistante.setCommentaire(evaluationModifiee.getCommentaire());

        // On ne touche pas au rapport / apprenti / tuteur pour éviter des erreurs de mapping
        evaluationRapportRepository.save(evaluationExistante);
    }
    // Supprimer une évaluation
    @Transactional
    public void supprimerEvaluation(Integer idEvaluation) {
        evaluationRapportRepository.deleteById(idEvaluation);
    }
}