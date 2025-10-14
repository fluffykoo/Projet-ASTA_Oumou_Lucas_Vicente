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
        return "listeApprentis";  // pour la vue thymeleaf
    }

    @GetMapping("/{id}")
    @ResponseBody
    public Optional<Apprenti> afficherUnApprenti(@PathVariable Integer id) {
        return apprentiService.getUnApprenti(id);
    }

    @PostMapping
    @ResponseBody
    public Apprenti ajouterUnApprenti(@RequestBody Apprenti apprenti) {
        return apprentiService.ajouterUnApprenti(apprenti);
    }

    @DeleteMapping("/{id}")
    @ResponseBody
    public Optional<Apprenti> supprimerUnApprenti(@PathVariable Integer id) {
        return apprentiService.supprimerUnApprenti(id);
    }

    @PutMapping("/{id}")
    @ResponseBody
    public void modifierUnApprenti(@PathVariable Integer id, @RequestBody Apprenti apprenti) {
        apprentiService.modifierUnApprenti(id, apprenti);
    }

    @PatchMapping("/{id}")
    @ResponseBody
    public Optional<Apprenti> patchUnApprenti(@PathVariable Integer id, @RequestBody Apprenti apprenti) {
        return apprentiService.patchUnApprenti(id, apprenti);
    }
}