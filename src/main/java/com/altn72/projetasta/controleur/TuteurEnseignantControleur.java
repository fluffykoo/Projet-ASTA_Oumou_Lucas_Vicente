package com.altn72.projetasta.controleur;

import com.altn72.projetasta.modele.TuteurEnseignant;
import com.altn72.projetasta.controleur.service.TuteurEnseignantService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/tuteurs")
public class TuteurEnseignantControleur {

    private final TuteurEnseignantService tuteurEnseignantService;

    public TuteurEnseignantControleur(TuteurEnseignantService tuteurEnseignantService) {
        this.tuteurEnseignantService = tuteurEnseignantService;
    }

    // Liste des tuteurs
    @GetMapping
    public String afficherTousLesTuteurs(Model model) {
        List<TuteurEnseignant> tuteurs = tuteurEnseignantService.getTousLesTuteurs();
        model.addAttribute("tuteurs", tuteurs);
        return "listeTuteurs"; // correspond au fichier listeTuteurs.html
    }

    //Supprimer un tuteur
    @DeleteMapping("/supprimer/{id}")
    public String supprimerUnTuteur(@PathVariable Integer id) {
        tuteurEnseignantService.supprimerUnTuteur(id);
        return "redirect:/tuteurs";
    }

    //Préparer formulaire d'ajout
    @GetMapping("/preparerAjout")
    public String preparerAjoutTuteur(Model model) {
        model.addAttribute("nouveauTuteur", new TuteurEnseignant());
        return "nouveauTuteur"; // correspond au formulaire d'ajout
    }

    //Ajouter un tuteur
    @PostMapping("/ajouter")
    public String ajouterUnTuteur(@ModelAttribute TuteurEnseignant tuteur) {
        tuteurEnseignantService.ajouterUnTuteur(tuteur);
        return "redirect:/tuteurs";
    }

    //Préparer formulaire de modification
    @GetMapping("/preparerModif/{id}")
    public String preparerModifTuteur(@PathVariable Integer id, Model model) {
        Optional<TuteurEnseignant> tuteur = tuteurEnseignantService.getUnTuteur(id);
        model.addAttribute("tuteurToUpdate", tuteur.orElseThrow());
        return "detailsTuteur"; // correspond au fichier détails de modification
    }

    //Modifier un tuteur
    @PutMapping("/modifier/{id}")
    public String modifierUnTuteur(@PathVariable Integer id, @ModelAttribute TuteurEnseignant tuteurModifie) {
        tuteurEnseignantService.modifierUnTuteur(id, tuteurModifie);
        return "redirect:/tuteurs";
    }
}