package com.altn72.projetasta.controleur;

import com.altn72.projetasta.modele.Soutenance;
import com.altn72.projetasta.service.SoutenanceService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@Controller
@RequestMapping("/soutenances")
public class SoutenanceControleur {

    private final SoutenanceService soutenanceService;

    public SoutenanceControleur(SoutenanceService soutenanceService) {
        this.soutenanceService = soutenanceService;
    }

    // Afficher la liste des soutenances
    @GetMapping
    public String afficherSoutenances(Model model) {
        model.addAttribute("soutenances", soutenanceService.getSoutenances());
        return "listeSoutenances";
    }

    // Afficher le détail d'une soutenance
    @GetMapping("/{id}")
    public String afficherUneSoutenance(@PathVariable Integer id, Model model) {
        Optional<Soutenance> soutenance = soutenanceService.getUneSoutenance(id);
        model.addAttribute("soutenance", soutenance.orElseThrow());
        return "detailsSoutenance";
    }

    // Supprimer une soutenance
    @DeleteMapping("/supprimer/{id}")
    public String supprimerSoutenance(@PathVariable Integer id) {
        soutenanceService.supprimerSoutenance(id);
        return "redirect:/soutenances";
    }

    // Préparer le formulaire d'ajout
    @GetMapping("/preparerAjout")
    public String preparerAjout(Model model) {
        model.addAttribute("nouvelleSoutenance", new Soutenance());
        return "nouvelleSoutenance";
    }

    // Ajouter une soutenance
    @PostMapping("/ajouter")
    public String ajouterSoutenance(@ModelAttribute Soutenance soutenance) {
        soutenanceService.ajouterSoutenance(soutenance);
        return "redirect:/soutenances";
    }

    // Préparer le formulaire de modification
    @GetMapping("/preparerModif/{id}")
    public String preparerModif(@PathVariable Integer id, Model model) {
        Optional<Soutenance> soutenance = soutenanceService.getUneSoutenance(id);
        model.addAttribute("soutenanceToUpdate", soutenance.orElseThrow());
        return "detailsSoutenance";
    }

    // Modifier une soutenance
    @PutMapping("/modifier/{id}")
    public String modifierSoutenance(@PathVariable Integer id, @ModelAttribute Soutenance soutenanceModifiee) {
        soutenanceService.modifierSoutenance(id, soutenanceModifiee);
        return "redirect:/soutenances";
    }
}