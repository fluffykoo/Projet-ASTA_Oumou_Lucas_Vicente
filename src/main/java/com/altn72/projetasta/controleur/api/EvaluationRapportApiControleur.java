package com.altn72.projetasta.controleur.api;

import com.altn72.projetasta.dto.EvaluationRapportDTO;
import com.altn72.projetasta.modele.EvaluationRapport;
import com.altn72.projetasta.service.EvaluationRapportService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/evaluations-rapport")
public class EvaluationRapportApiControleur {

    private final EvaluationRapportService evaluationRapportService;

    public EvaluationRapportApiControleur(EvaluationRapportService evaluationRapportService) {
        this.evaluationRapportService = evaluationRapportService;
    }

    // GET - Liste de toutes les évaluations (renvoie un DTO)
    @GetMapping
    public List<EvaluationRapportDTO> getAllEvaluations() {
        return evaluationRapportService.getEvaluations().stream()
                .map(EvaluationRapportDTO::new)
                .collect(Collectors.toList());
    }

    // GET - Une seule évaluation
    @GetMapping("/{id}")
    public ResponseEntity<EvaluationRapportDTO> getEvaluationById(@PathVariable Integer id) {
        return evaluationRapportService.getUneEvaluation(id)
                .map(e -> ResponseEntity.ok(new EvaluationRapportDTO(e)))
                .orElse(ResponseEntity.notFound().build());
    }

    // POST - Créer une évaluation
    @PostMapping
    public ResponseEntity<EvaluationRapportDTO> createEvaluation(@RequestBody EvaluationRapport evaluation) {
        try {
            evaluationRapportService.ajouterEvaluation(evaluation);
            return ResponseEntity.status(HttpStatus.CREATED).body(new EvaluationRapportDTO(evaluation));
        } catch (Exception ex) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
    }

    // PUT - Modifier une évaluation existante
    @PutMapping("/{id}")
    public ResponseEntity<EvaluationRapportDTO> updateEvaluation(
            @PathVariable Integer id,
            @RequestBody EvaluationRapport evaluationModifiee) {

        try {
            evaluationRapportService.modifierEvaluation(id, evaluationModifiee);
            return evaluationRapportService.getUneEvaluation(id)
                    .map(e -> ResponseEntity.ok(new EvaluationRapportDTO(e)))
                    .orElse(ResponseEntity.notFound().build());
        } catch (Exception ex) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
    }

    // DELETE - Supprimer une évaluation
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteEvaluation(@PathVariable Integer id) {
        try {
            evaluationRapportService.supprimerEvaluation(id);
            return ResponseEntity.noContent().build();
        } catch (Exception ex) {
            return ResponseEntity.notFound().build();
        }
    }
}