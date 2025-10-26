package com.altn72.projetasta.controleur;

import com.altn72.projetasta.modele.MaitreApprentissage;
import com.altn72.projetasta.service.MaitreApprentissageService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@Controller
@RequestMapping("/maitres")
public class MaitreApprentissageControleur {

    private final MaitreApprentissageService maitreApprentissageService;

    public MaitreApprentissageControleur(MaitreApprentissageService maitreApprentissageService) {
        this.maitreApprentissageService = maitreApprentissageService;
    }

    // Afficher la liste des maîtres d’apprentissage
    @GetMapping
    public String afficherMaitres(Model model) {
        model.addAttribute("maitres", maitreApprentissageService.getMaitres());
        return "listeMaitres";
    }

    // Afficher les détails d’un maître d’apprentissage
    @GetMapping("/unMaitre/{idMaitre}")
    public Optional<MaitreApprentissage> afficherUnMaitre(@PathVariable("idMaitre") Integer id) {
        return maitreApprentissageService.getUnMaitre(id);
    }

    // Supprimer un maître d’apprentissage
    @DeleteMapping("/supprimerMaitre/{idMaitre}")
    public String supprimerMaitre(@PathVariable("idMaitre") Integer id) {
        maitreApprentissageService.supprimerMaitre(id);
        return "redirect:/maitres";
    }

    // Ajouter un nouveau maître d’apprentissage
    @PostMapping("/ajouterMaitre")
    public String ajouterMaitre(@ModelAttribute MaitreApprentissage maitre) {
        maitreApprentissageService.ajouterMaitre(maitre);
        return "redirect:/maitres";
    }

    // Préparer le formulaire d’ajout d’un maître d’apprentissage
    @GetMapping("/preparerAjoutMaitre")
    public String preparerAjoutMaitre(Model model) {
        model.addAttribute("nouveauMaitre", new MaitreApprentissage());
        return "nouveauMaitre";
    }

    // Préparer le formulaire de modification d’un maître d’apprentissage
    @GetMapping("/preparerModifMaitre/{idMaitre}")
    public String preparerModifMaitre(@PathVariable Integer idMaitre, Model model) {
        Optional<MaitreApprentissage> maitreToUpdate = maitreApprentissageService.getUnMaitre(idMaitre);
        model.addAttribute("maitreToUpdate", maitreToUpdate.orElseThrow());
        return "detailsMaitre";
    }

    // Modifier un maître d’apprentissage existant
    @PutMapping("/modifier/{idMaitre}")
    public String modifierMaitre(@PathVariable Integer idMaitre, @ModelAttribute MaitreApprentissage maitreModifie) {
        maitreApprentissageService.modifierMaitre(idMaitre, maitreModifie);
        return "redirect:/maitres";
    }
}