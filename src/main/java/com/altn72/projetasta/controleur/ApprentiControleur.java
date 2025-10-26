package com.altn72.projetasta.controleur;

import com.altn72.projetasta.modele.Apprenti;
import com.altn72.projetasta.modele.Personne;
import com.altn72.projetasta.repository.EntrepriseRepository;
import com.altn72.projetasta.repository.PersonneRepository;
import com.altn72.projetasta.service.ApprentiService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.Optional;

@Controller
@RequestMapping("/apprentis")
public class ApprentiControleur {

    private final ApprentiService apprentiService;
    private final PersonneRepository personneRepository;
    private final EntrepriseRepository entrepriseRepository;

    public ApprentiControleur(ApprentiService apprentiService,
                              PersonneRepository personneRepository,
                              EntrepriseRepository entrepriseRepository) {
        this.apprentiService = apprentiService;
        this.personneRepository = personneRepository;
        this.entrepriseRepository = entrepriseRepository;
    }

    // Liste de tous les apprentis
    @GetMapping
    public String afficherTousLesApprentis(Model model) {
        model.addAttribute("lesApprentis", apprentiService.getTousLesApprentis());
        return "listeApprentis";
    }

    // Détails d’un apprenti
    @GetMapping("/{id}")
    public String detailsApprenti(@PathVariable Integer id, Model model, RedirectAttributes redirectAttributes) {
        Optional<Apprenti> apprentiOpt = apprentiService.getUnApprenti(id);
        if (apprentiOpt.isPresent()) {
            model.addAttribute("apprenti", apprentiOpt.get());
            return "apprenti-details";
        } else {
            redirectAttributes.addFlashAttribute("errorMessage", "Apprenti introuvable !");
            return "redirect:/dashboard";
        }
    }

    // Préparer le formulaire d’ajout
    @GetMapping("/preparerAjout")
    public String preparerAjout(Model model) {
        model.addAttribute("nouvelApprenti", new Apprenti());
        model.addAttribute("entreprises", entrepriseRepository.findAll());
        model.addAttribute("anneeEnCours", "2025-2026");
        return "ajouter-apprenti";
    }

    // Ajouter un nouvel apprenti
    @PostMapping("/ajouter")
    public String ajouterApprenti(@ModelAttribute("nouvelApprenti") Apprenti apprenti,
                                  RedirectAttributes redirectAttributes) {
        try {
            // Créer la personne associée avant de sauvegarder l’apprenti
            Personne p = apprenti.getPersonne();
            personneRepository.save(p);

            // Associer la personne et sauvegarder l’apprenti
            apprenti.setPersonne(p);
            apprentiService.ajouterApprenti(apprenti);

            redirectAttributes.addFlashAttribute("successMessage",
                    "Apprenti ajouté avec succès !");
            return "redirect:/dashboard";

        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("errorMessage",
                    "Erreur lors de l'ajout : " + e.getMessage());
            return "redirect:/apprentis/preparerAjout";
        }
    }

    // Préparer la modification
    @GetMapping("/preparerModif/{id}")
    public String preparerModif(@PathVariable Integer id, Model model) {
        Optional<Apprenti> apprentiOpt = apprentiService.getUnApprenti(id);
        if (apprentiOpt.isPresent()) {
            model.addAttribute("apprenti", apprentiOpt.get());
            model.addAttribute("entreprises", entrepriseRepository.findAll());
            return "apprenti-details";
        }
        return "redirect:/dashboard";
    }

    // Modifier un apprenti
    @PostMapping("/modifier/{id}")
    public String modifierApprenti(@PathVariable Integer id,
                                   @ModelAttribute Apprenti apprentiModifie,
                                   RedirectAttributes redirectAttributes) {
        try {
            apprentiService.modifierApprenti(id, apprentiModifie);
            redirectAttributes.addFlashAttribute("successMessage", "Modification réussie !");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("errorMessage", "Échec de la modification : " + e.getMessage());
        }
        return "redirect:/apprentis/" + id;
    }

    //Supprimer un apprenti
    @GetMapping("/supprimer/{id}")
    public String supprimerApprenti(@PathVariable Integer id, RedirectAttributes redirectAttributes) {
        try {
            apprentiService.supprimerApprenti(id);
            redirectAttributes.addFlashAttribute("successMessage", "Apprenti supprimé avec succès !");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("errorMessage", "Erreur lors de la suppression : " + e.getMessage());
        }
        return "redirect:/dashboard";
    }
}