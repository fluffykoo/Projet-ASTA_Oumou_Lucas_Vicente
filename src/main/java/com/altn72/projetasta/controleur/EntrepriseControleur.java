package com.altn72.projetasta.controleur;

import com.altn72.projetasta.modele.Entreprise;
import com.altn72.projetasta.service.EntrepriseService;
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

    // Afficher la liste des entreprises
    @GetMapping
    public String afficherEntreprises(Model model) {
        List<Entreprise> entreprises = entrepriseService.getEntreprises();
        model.addAttribute("entreprises", entreprises);
        return "listeEntreprises";
    }

    // Supprimer une entreprise
    @DeleteMapping("/supprimer/{id}")
    public String supprimerEntreprise(@PathVariable Integer id) {
        entrepriseService.supprimerEntreprise(id);
        return "redirect:/entreprises";
    }

    // Préparer le formulaire d’ajout
    @GetMapping("/preparerAjout")
    public String preparerAjout(Model model) {
        model.addAttribute("nouvelleEntreprise", new Entreprise());
        return "nouvelleEntreprise";
    }

    // Ajouter une nouvelle entreprise
    @PostMapping("/ajouter")
    public String ajouterEntreprise(@ModelAttribute Entreprise entreprise) {
        entrepriseService.ajouterEntreprise(entreprise);
        return "redirect:/entreprises";
    }

    // Préparer le formulaire de modification
    @GetMapping("/preparerModif/{id}")
    public String preparerModif(@PathVariable Integer id, Model model) {
        Optional<Entreprise> entreprise = entrepriseService.getUneEntreprise(id);
        model.addAttribute("entrepriseToUpdate", entreprise.orElseThrow());
        return "detailsEntreprise";
    }

    // Modifier une entreprise existante
    @PutMapping("/modifier/{id}")
    public String modifierEntreprise(@PathVariable Integer id, @ModelAttribute Entreprise entrepriseModifiee) {
        entrepriseService.modifierEntreprise(id, entrepriseModifiee);
        return "redirect:/entreprises";
    }
}