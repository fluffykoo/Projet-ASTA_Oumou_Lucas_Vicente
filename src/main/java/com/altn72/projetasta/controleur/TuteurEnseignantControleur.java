package com.altn72.projetasta.controleur;

import com.altn72.projetasta.modele.Personne;
import com.altn72.projetasta.modele.TuteurEnseignant;
import com.altn72.projetasta.service.PersonneService;
import com.altn72.projetasta.service.TuteurEnseignantService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/tuteurs")
public class TuteurEnseignantControleur {

    private final TuteurEnseignantService tuteurEnseignantService;
    private final PersonneService personneService;

    public TuteurEnseignantControleur(TuteurEnseignantService tuteurEnseignantService, PersonneService personneService) {
        this.tuteurEnseignantService = tuteurEnseignantService;
        this.personneService = personneService;
    }

    // Liste des tuteurs enseignants
    @GetMapping
    public String listeTuteurs(Model model) {
        model.addAttribute("tuteurs", tuteurEnseignantService.getTuteurs());
        return "tuteurs/liste"; // --> tuteurs/liste.html
    }

    // Préparer formulaire d’ajout
    @GetMapping("/ajouter")
    public String preparerAjout(Model model) {
        TuteurEnseignant tuteur = new TuteurEnseignant();
        tuteur.setPersonne(new Personne()); //
        model.addAttribute("tuteur", tuteur);
        return "tuteurs/formulaire";
    }
    // Soumettre formulaire d’ajout
    @PostMapping("/ajouter")
    public String ajouterTuteur(@ModelAttribute("tuteur") TuteurEnseignant tuteur) {
        tuteurEnseignantService.ajouterTuteur(tuteur); // hash automatique
        return "redirect:/tuteurs";
    }

    // Préparer modification
    @GetMapping("/modifier/{id}")
    public String preparerModification(@PathVariable("id") Integer id, Model model) {
        model.addAttribute("tuteur", tuteurEnseignantService.getUnTuteur(id).orElseThrow());
        return "tuteurs/formulaire";
    }

    // Soumettre modification
    @PostMapping("/modifier/{id}")
    public String modifierTuteur(@PathVariable("id") Integer id,
                                 @ModelAttribute("tuteur") TuteurEnseignant tuteur) {
        tuteurEnseignantService.modifierTuteur(id, tuteur); // re-hash si modifié
        return "redirect:/tuteurs";
    }

    // Supprimer
    @GetMapping("/supprimer/{id}")
    public String supprimerTuteur(@PathVariable("id") Integer id) {
        tuteurEnseignantService.supprimerTuteur(id);
        return "redirect:/tuteurs";
    }

    //Récupérer nom
    @GetMapping("/recuperernompersonne/{id}")
    public String recupererNompersonne(@PathVariable("id") Integer id) {
        return personneService.RecupererNomPersonne(id);
    }
}