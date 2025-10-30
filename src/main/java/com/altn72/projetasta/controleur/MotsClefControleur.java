package com.altn72.projetasta.controleur;

import com.altn72.projetasta.modele.MotsClef;
import com.altn72.projetasta.service.MotsClefService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/motsclefs")
public class MotsClefControleur {

    private final MotsClefService motsClefService;

    public MotsClefControleur(MotsClefService motsClefService) {
        this.motsClefService = motsClefService;
    }

    // Afficher tous les mots-clés
    @GetMapping
    public String afficherTousLesMotsClefs(Model model) {
        List<MotsClef> motsClefs = motsClefService.getMotsClefs();
        model.addAttribute("motsClefs", motsClefs);
        return "listeMotsClefs";
    }

    // Afficher le détail d’un mot-clé
    @GetMapping("/{nom}")
    public String afficherUnMotClef(@PathVariable("nom") String nom, Model model) {
        Optional<MotsClef> motClef = motsClefService.getUnMotClef(nom);
        model.addAttribute("motClef", motClef.orElseThrow());
        return "detailsMotClef";
    }

    // Supprimer un mot-clé
    @DeleteMapping("/supprimer/{nom}")
    public String supprimerMotClef(@PathVariable("nom") String nom) {
        motsClefService.supprimerMotClef(nom);
        return "redirect:/motsclefs";
    }

    // Préparer le formulaire d’ajout
    @GetMapping("/preparerAjout")
    public String preparerAjout(Model model) {
        model.addAttribute("nouveauMotClef", new MotsClef());
        return "nouveauMotClef";
    }

    // Ajouter un mot-clé
    @PostMapping("/ajouter")
    public String ajouterMotClef(@ModelAttribute MotsClef motClef) {
        motsClefService.ajouterMotClef(motClef);
        return "redirect:/motsclefs";
    }

    // Préparer le formulaire de modification
    @GetMapping("/preparerModif/{nom}")
    public String preparerModif(@PathVariable("nom") String nom, Model model) {
        Optional<MotsClef> motClef = motsClefService.getUnMotClef(nom);
        model.addAttribute("motClefToUpdate", motClef.orElseThrow());
        return "detailsMotClef";
    }

    // Modifier un mot-clé
    @PutMapping("/modifier/{nom}")
    public String modifierMotClef(@PathVariable("nom") String nom, @ModelAttribute MotsClef motClefModifie) {
        motsClefService.modifierMotClef(nom, motClefModifie);
        return "redirect:/motsclefs";
    }
}