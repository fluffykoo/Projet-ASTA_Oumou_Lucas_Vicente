//package com.altn72.projetasta.service;
//
//import com.altn72.projetasta.modele.EvaluationEcole;
//import com.altn72.projetasta.modele.repository.EvaluationEcoleRepository;
//import org.springframework.stereotype.Service;
//
//import java.util.List;
//import java.util.Optional;
//
//@Service
//public class EvaluationEcoleService {
//
//    private final EvaluationEcoleRepository evaluationEcoleRepository;
//
//    public EvaluationEcoleService(EvaluationEcoleRepository evaluationEcoleRepository) {
//        this.evaluationEcoleRepository = evaluationEcoleRepository;
//    }
//
//    public List<EvaluationEcole> getToutesLesEvaluations() {
//        return evaluationEcoleRepository.findAll();
//    }
//
//    public Optional<EvaluationEcole> getUneEvaluation(Integer id) {
//        return evaluationEcoleRepository.findById(id);
//    }
//
//    public EvaluationEcole ajouterUneEvaluation(EvaluationEcole evaluationEcole) {
//        return evaluationEcoleRepository.save(evaluationEcole);
//    }
//
//    public void modifierUneEvaluation(Integer id, EvaluationEcole evaluationEcole) {
//        evaluationEcole.setId(id);
//        evaluationEcoleRepository.save(evaluationEcole);
//    }
//
//    public Optional<EvaluationEcole> supprimerUneEvaluation(Integer id) {
//        Optional<EvaluationEcole> evaluation = evaluationEcoleRepository.findById(id);
//        evaluation.ifPresent(evaluationEcoleRepository::delete);
//        return evaluation;
//    }
//}