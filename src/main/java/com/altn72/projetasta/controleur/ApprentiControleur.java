package com.altn72.projetasta.controleur;

import com.altn72.projetasta.modele.Apprenti;
import com.altn72.projetasta.controleur.service.ApprentiService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/apprentis")
public class ApprentiControleur {

    private final ApprentiService apprentiService;

    public ApprentiControleur(ApprentiService apprentiService) {
        this.apprentiService = apprentiService;
    }

    @GetMapping
    public String afficherTousLesApprentis(Model model) {
        List<Apprenti> apprentis = apprentiService.getTousLesApprentis();
        model.addAttribute("lesApprentis", apprentis);
        return "listeApprentis";
    }

    @GetMapping("/preparerAjout")
    public String preparerAjout(Model model) {
        model.addAttribute("nouvelApprenti", new Apprenti());
        return "nouvelApprenti";
    }

    @PostMapping("/ajouter")
    public String ajouterApprenti(@ModelAttribute Apprenti apprenti) {
        apprentiService.ajouterUnApprenti(apprenti);
        return "redirect:/apprentis";
    }

    @GetMapping("/preparerModif/{id}")
    public String preparerModif(@PathVariable Integer id, Model model) {
        Optional<Apprenti> apprenti = apprentiService.getUnApprenti(id);
        model.addAttribute("apprentiToUpdate", apprenti.orElseThrow());
        return "detailsApprenti";
    }

    @PutMapping("/modifier/{id}")
    public String modifierApprenti(@PathVariable Integer id, @ModelAttribute Apprenti apprentiModifie) {
        apprentiService.modifierUnApprenti(id, apprentiModifie);
        return "redirect:/apprentis";
    }

    @DeleteMapping("/supprimer/{id}")
    public String supprimerApprenti(@PathVariable Integer id) {
        apprentiService.supprimerUnApprenti(id);
        return "redirect:/apprentis";
    }
}