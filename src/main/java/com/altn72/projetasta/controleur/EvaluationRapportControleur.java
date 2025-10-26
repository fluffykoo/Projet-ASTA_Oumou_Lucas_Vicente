package com.altn72.projetasta.controleur;

import com.altn72.projetasta.modele.EvaluationRapport;
import com.altn72.projetasta.service.EvaluationRapportService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/evaluations")
public class EvaluationRapportControleur {

    private final EvaluationRapportService evaluationRapportService;

    public EvaluationRapportControleur(EvaluationRapportService evaluationRapportService) {
        this.evaluationRapportService = evaluationRapportService;
    }

    // Afficher toutes les évaluations
    @GetMapping
    public String afficherToutesLesEvaluations(Model model) {
        List<EvaluationRapport> evaluations = evaluationRapportService.getEvaluations();
        model.addAttribute("evaluations", evaluations);
        return "listeEvaluations";
    }

    // Afficher une évaluation
    @GetMapping("/{id}")
    public String afficherUneEvaluation(@PathVariable("id") Integer id, Model model) {
        Optional<EvaluationRapport> evaluation = evaluationRapportService.getUneEvaluation(id);
        model.addAttribute("evaluation", evaluation.orElseThrow());
        return "detailsEvaluation";
    }

    // Supprimer une évaluation
    @DeleteMapping("/supprimer/{id}")
    public String supprimerEvaluation(@PathVariable("id") Integer id) {
        evaluationRapportService.supprimerEvaluation(id);
        return "redirect:/evaluations";
    }

    // Préparer l’ajout
    @GetMapping("/preparerAjout")
    public String preparerAjout(Model model) {
        model.addAttribute("nouvelleEvaluation", new EvaluationRapport());
        return "nouvelleEvaluation";
    }

    // Ajouter une évaluation
    @PostMapping("/ajouter")
    public String ajouterEvaluation(@ModelAttribute EvaluationRapport evaluation) {
        evaluationRapportService.ajouterEvaluation(evaluation);
        return "redirect:/evaluations";
    }

    // Préparer la modification
    @GetMapping("/preparerModif/{id}")
    public String preparerModif(@PathVariable Integer id, Model model) {
        Optional<EvaluationRapport> evaluation = evaluationRapportService.getUneEvaluation(id);
        model.addAttribute("evaluationToUpdate", evaluation.orElseThrow());
        return "detailsEvaluation";
    }

    // Modifier une évaluation
    @PutMapping("/modifier/{id}")
    public String modifierEvaluation(@PathVariable Integer id, @ModelAttribute EvaluationRapport evaluationModifiee) {
        evaluationRapportService.modifierEvaluation(id, evaluationModifiee);
        return "redirect:/evaluations";
    }
}