package com.altn72.projetasta.controleur;

import com.altn72.projetasta.modele.Entreprise;
import com.altn72.projetasta.controleur.service.EntrepriseService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/entreprises")
public class EntrepriseControleur {

    private final EntrepriseService entrepriseService;

    public EntrepriseControleur(EntrepriseService entrepriseService) {
        this.entrepriseService = entrepriseService;
    }

    @GetMapping
    public String afficherEntreprises(Model model) {
        List<Entreprise> entreprises = entrepriseService.getToutesLesEntreprises();
        model.addAttribute("entreprises", entreprises);
        return "listeEntreprises";
    }

    @DeleteMapping("/supprimer/{id}")
    public String supprimerEntreprise(@PathVariable Integer id) {
        entrepriseService.supprimerUneEntreprise(id);
        return "redirect:/entreprises";
    }

    @GetMapping("/preparerAjout")
    public String preparerAjout(Model model) {
        model.addAttribute("nouvelleEntreprise", new Entreprise());
        return "nouvelleEntreprise";
    }

    @PostMapping("/ajouter")
    public String ajouterEntreprise(@ModelAttribute Entreprise entreprise) {
        entrepriseService.ajouterUneEntreprise(entreprise);
        return "redirect:/entreprises";
    }

    @GetMapping("/preparerModif/{id}")
    public String preparerModif(@PathVariable Integer id, Model model) {
        Optional<Entreprise> entreprise = entrepriseService.getUneEntreprise(id);
        model.addAttribute("entrepriseToUpdate", entreprise.orElseThrow());
        return "detailsEntreprise";
    }

    @PutMapping("/modifier/{id}")
    public String modifierEntreprise(@PathVariable Integer id, @ModelAttribute Entreprise entrepriseModifiee) {
        entrepriseService.modifierUneEntreprise(id, entrepriseModifiee);
        return "redirect:/entreprises";
    }
}