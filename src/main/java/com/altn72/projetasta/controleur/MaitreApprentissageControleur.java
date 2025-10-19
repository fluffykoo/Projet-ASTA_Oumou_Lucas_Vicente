package com.altn72.projetasta.controleur;

import com.altn72.projetasta.modele.MaitreApprentissage;
import com.altn72.projetasta.controleur.service.MaitreApprentissageService;
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

    @GetMapping
    public String afficherMaitres(Model model) {
        model.addAttribute("maitres", maitreApprentissageService.getTousLesMaitres());
        return "listeMaitres";
    }

    @GetMapping("/unMaitre/{idMaitre}")
    public Optional<MaitreApprentissage> afficherUnMaitre(@PathVariable("idMaitre") Integer id) {
        return maitreApprentissageService.getUnMaitre(id);
    }

    @DeleteMapping("/supprimerMaitre/{idMaitre}")
    public String supprimerMaitre(@PathVariable("idMaitre") Integer id) {
        maitreApprentissageService.supprimerUnMaitre(id);
        return "redirect:/maitres";
    }

    @PostMapping("/ajouterMaitre")
    public String ajouterMaitre(@ModelAttribute MaitreApprentissage maitre) {
        maitreApprentissageService.ajouterUnMaitre(maitre);
        return "redirect:/maitres";
    }

    @GetMapping("/preparerAjoutMaitre")
    public String preparerAjoutMaitre(Model model) {
        model.addAttribute("nouveauMaitre", new MaitreApprentissage());
        return "nouveauMaitre";
    }

    @GetMapping("/preparerModifMaitre/{idMaitre}")
    public String preparerModifMaitre(@PathVariable Integer idMaitre, Model model) {
        Optional<MaitreApprentissage> maitreToUpdate = maitreApprentissageService.getUnMaitre(idMaitre);
        model.addAttribute("maitreToUpdate", maitreToUpdate.orElseThrow());
        return "detailsMaitre";
    }

    @PutMapping("/modifier/{idMaitre}")
    public String modifierMaitre(@PathVariable Integer idMaitre, @ModelAttribute MaitreApprentissage maitreModifie) {
        maitreApprentissageService.modifierUnMaitre(idMaitre, maitreModifie);
        return "redirect:/maitres";
    }
}