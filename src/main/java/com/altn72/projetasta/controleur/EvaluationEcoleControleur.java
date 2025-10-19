package com.altn72.projetasta.controleur;

import com.altn72.projetasta.modele.EvaluationEcole;
import com.altn72.projetasta.controleur.service.EvaluationEcoleService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/evaluations")
public class EvaluationEcoleControleur {

    private final EvaluationEcoleService evaluationEcoleService;

    public EvaluationEcoleControleur(EvaluationEcoleService evaluationEcoleService) {
        this.evaluationEcoleService = evaluationEcoleService;
    }

    //Afficher la liste de toutes les évaluations

    @GetMapping
    public String afficherToutesLesEvaluations(Model model) {
        List<EvaluationEcole> evaluations = evaluationEcoleService.getToutesLesEvaluations();
        model.addAttribute("evaluations", evaluations);
        return "listeEvaluations";
    }

    //Afficher une seule évaluation par ID

    @GetMapping("/{id}")
    public String afficherUneEvaluation(@PathVariable Integer id, Model model) {
        Optional<EvaluationEcole> evaluation = evaluationEcoleService.getUneEvaluation(id);
        model.addAttribute("evaluation", evaluation.orElseThrow());
        return "detailsEvaluation";
    }

    //Préparer le formulaire vierge pour ajouter une évaluation

    @GetMapping("/preparerAjout")
    public String preparerAjoutEvaluation(Model model) {
        model.addAttribute("nouvelleEvaluation", new EvaluationEcole());
        return "nouvelleEvaluation";
    }

    //Ajouter une nouvelle évaluation

    @PostMapping("/ajouter")
    public String ajouterEvaluation(@ModelAttribute EvaluationEcole evaluationEcole) {
        evaluationEcoleService.ajouterUneEvaluation(evaluationEcole);
        return "redirect:/evaluations";
    }

    //Préparer le formulaire pour modifier une évaluation existante

    @GetMapping("/preparerModif/{id}")
    public String preparerModifEvaluation(@PathVariable Integer id, Model model) {
        Optional<EvaluationEcole> evaluation = evaluationEcoleService.getUneEvaluation(id);
        model.addAttribute("evaluationToUpdate", evaluation.orElseThrow());
        return "detailsEvaluation";
    }

    //Modifier une évaluation
    @PutMapping("/modifier/{id}")
    public String modifierEvaluation(@PathVariable Integer id, @ModelAttribute EvaluationEcole evaluationEcole) {
        evaluationEcoleService.modifierUneEvaluation(id, evaluationEcole);
        return "redirect:/evaluations";
    }

    //Supprimer une évaluation
    @DeleteMapping("/supprimer/{id}")
    public String supprimerEvaluation(@PathVariable Integer id) {
        evaluationEcoleService.supprimerUneEvaluation(id);
        return "redirect:/evaluations";
    }
}