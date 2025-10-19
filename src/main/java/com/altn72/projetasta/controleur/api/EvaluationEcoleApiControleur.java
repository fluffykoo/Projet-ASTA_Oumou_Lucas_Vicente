package com.altn72.projetasta.controleur.api;

import com.altn72.projetasta.modele.EvaluationEcole;
import com.altn72.projetasta.controleur.service.EvaluationEcoleService;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/evaluations")
public class EvaluationEcoleApiControleur {

    private final EvaluationEcoleService evaluationEcoleService;

    public EvaluationEcoleApiControleur(EvaluationEcoleService evaluationEcoleService) {
        this.evaluationEcoleService = evaluationEcoleService;
    }

    @GetMapping
    public List<EvaluationEcole> getToutesLesEvaluations() {
        return evaluationEcoleService.getToutesLesEvaluations();
    }

    @GetMapping("/{id}")
    public Optional<EvaluationEcole> getUneEvaluation(@PathVariable Integer id) {
        return evaluationEcoleService.getUneEvaluation(id);
    }

    @PostMapping
    public EvaluationEcole ajouterUneEvaluation(@RequestBody EvaluationEcole evaluationEcole) {
        return evaluationEcoleService.ajouterUneEvaluation(evaluationEcole);
    }

    @PutMapping("/{id}")
    public void modifierUneEvaluation(@PathVariable Integer id, @RequestBody EvaluationEcole evaluationEcole) {
        evaluationEcoleService.modifierUneEvaluation(id, evaluationEcole);
    }

    @DeleteMapping("/{id}")
    public Optional<EvaluationEcole> supprimerUneEvaluation(@PathVariable Integer id) {
        return evaluationEcoleService.supprimerUneEvaluation(id);
    }
}