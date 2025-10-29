package com.altn72.projetasta.controleur;

import com.altn72.projetasta.modele.Visite;
import com.altn72.projetasta.service.VisiteService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/visites")
public class VisiteControleur {

    private final VisiteService visiteService;

    public VisiteControleur(VisiteService visiteService) {
        this.visiteService = visiteService;
    }

    // Afficher toutes les visites
    @GetMapping
    public String afficherToutesLesVisites(Model model) {
        List<Visite> visites = visiteService.getVisites();
        model.addAttribute("visites", visites);
        return "listeVisites";
    }

    // Afficher le détail d’une visite
    @GetMapping("/{id}")
    public String afficherUneVisite(@PathVariable("id") Integer id, Model model) {
        Optional<Visite> visite = visiteService.getUneVisite(id);
        model.addAttribute("visite", visite.orElseThrow());
        return "detailsVisite";
    }

    // Supprimer une visite
    @DeleteMapping("/supprimer/{id}")
    public String supprimerVisite(@PathVariable("id") Integer id) {
        visiteService.supprimerVisite(id);
        return "redirect:/visites";
    }

    // Préparer le formulaire d’ajout
    @GetMapping("/preparerAjout")
    public String preparerAjoutVisite(Model model) {
        model.addAttribute("nouvelleVisite", new Visite());
        return "nouvelleVisite";
    }

    // Ajouter une visite
    @PostMapping("/ajouter")
    public String ajouterVisite(@ModelAttribute Visite visite) {
        visiteService.ajouterVisite(visite);
        return "redirect:/visites";
    }

    // Préparer le formulaire de modification
    @GetMapping("/preparerModif/{id}")
    public String preparerModifVisite(@PathVariable("id") Integer id, Model model) {
        Optional<Visite> visiteToUpdate = visiteService.getUneVisite(id);
        model.addAttribute("visiteToUpdate", visiteToUpdate.orElseThrow());
        return "detailsVisite";
    }

    // Modifier une visite existante
    @PutMapping("/modifier/{id}")
    public String modifierVisite(@PathVariable("id") Integer id, @ModelAttribute Visite visiteModifiee) {
        visiteService.modifierVisite(id, visiteModifiee);
        return "redirect:/visites";
    }


}