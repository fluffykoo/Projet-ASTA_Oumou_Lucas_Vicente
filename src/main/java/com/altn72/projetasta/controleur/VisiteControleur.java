package com.altn72.projetasta.controleur;

import com.altn72.projetasta.modele.Visite;
import com.altn72.projetasta.controleur.service.VisiteService;
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

    @GetMapping
    public String afficherToutesLesVisites(Model model) {
        List<Visite> visites = visiteService.getToutesLesVisites();
        model.addAttribute("visites", visites);
        return "listeVisites"; // nom du template Thymeleaf
    }

    @GetMapping("/unVisite/{idVisite}")
    public Optional<Visite> afficherUneVisite(@PathVariable("idVisite") Integer id) {
        return visiteService.getUneVisite(id);
    }

    @DeleteMapping("/supprimer/{idVisite}")
    public String supprimerVisite(@PathVariable("idVisite") Integer id) {
        visiteService.supprimerUneVisite(id);
        return "redirect:/visites";
    }

    @PostMapping("/ajouter")
    public String ajouterVisite(@ModelAttribute Visite visite) {
        visiteService.ajouterUneVisite(visite);
        return "redirect:/visites";
    }

    @GetMapping("/preparerAjout")
    public String preparerAjoutVisite(Model model) {
        model.addAttribute("nouvelleVisite", new Visite());
        return "nouvelleVisite";
    }

    @GetMapping("/preparerModif/{idVisite}")
    public String preparerModifVisite(@PathVariable Integer idVisite, Model model) {
        Optional<Visite> visiteToUpdate = visiteService.getUneVisite(idVisite);
        model.addAttribute("visiteToUpdate", visiteToUpdate.orElseThrow());
        return "detailsVisite";
    }

    @PutMapping("/modifier/{idVisite}")
    public String modifierVisite(@PathVariable Integer idVisite, @ModelAttribute Visite visiteModifiee) {
        visiteService.modifierUneVisite(idVisite, visiteModifiee);
        return "redirect:/visites";
    }
}