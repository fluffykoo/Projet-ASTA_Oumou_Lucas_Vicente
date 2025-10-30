package com.altn72.projetasta.controleur;

import com.altn72.projetasta.modele.Rapport;
import com.altn72.projetasta.service.RapportService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@Controller
@RequestMapping("/rapports")
public class RapportControleur {

    private final RapportService rapportService;

    public RapportControleur(RapportService rapportService) {
        this.rapportService = rapportService;
    }

    // Afficher la liste des rapports
    @GetMapping
    public String afficherRapports(Model model) {
        model.addAttribute("rapports", rapportService.getRapports());
        return "listeRapports";
    }

    // Afficher les détails d’un rapport
    @GetMapping("/{id}")
    public String afficherUnRapport(@PathVariable Integer id, Model model) {
        Optional<Rapport> rapport = rapportService.getUnRapport(id);
        model.addAttribute("rapport", rapport.orElseThrow());
        return "detailsRapport";
    }

    // Supprimer un rapport
    @DeleteMapping("/supprimer/{id}")
    public String supprimerRapport(@PathVariable Integer id) {
        rapportService.supprimerRapport(id);
        return "redirect:/rapports";
    }

    // Préparer le formulaire d’ajout
    @GetMapping("/preparerAjout")
    public String preparerAjout(Model model) {
        model.addAttribute("nouveauRapport", new Rapport());
        return "nouveauRapport";
    }

    // Ajouter un rapport
    @PostMapping("/ajouter")
    public String ajouterRapport(@ModelAttribute Rapport rapport) {
        rapportService.ajouterRapport(rapport);
        return "redirect:/rapports";
    }

    // Préparer le formulaire de modification
    @GetMapping("/preparerModif/{id}")
    public String preparerModif(@PathVariable Integer id, Model model) {
        Optional<Rapport> rapport = rapportService.getUnRapport(id);
        model.addAttribute("rapportToUpdate", rapport.orElseThrow());
        return "detailsRapport";
    }

    // Modifier un rapport
    @PutMapping("/modifier/{id}")
    public String modifierRapport(@PathVariable Integer id, @ModelAttribute Rapport rapportModifie) {
        rapportService.modifierRapport(id, rapportModifie);
        return "redirect:/rapports";
    }
}