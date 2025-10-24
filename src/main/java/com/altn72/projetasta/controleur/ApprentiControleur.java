//package com.altn72.projetasta.controleur;
//
//import com.altn72.projetasta.modele.Apprenti;
//import com.altn72.projetasta.controleur.service.ApprentiService;
//import org.springframework.stereotype.Controller;
//import org.springframework.ui.Model;
//import org.springframework.web.bind.annotation.*;
//
//import java.util.List;
//import java.util.Optional;
//
//@Controller
//@RequestMapping("/apprentis")
//public class ApprentiControleur {
//
//    private final ApprentiService apprentiService;
//
//    public ApprentiControleur(ApprentiService apprentiService) {
//        this.apprentiService = apprentiService;
//    }
//
//    @GetMapping
//    public String afficherTousLesApprentis(Model model) {
//        List<Apprenti> apprentis = apprentiService.getTousLesApprentis();
//        model.addAttribute("lesApprentis", apprentis);
//        return "listeApprentis";
//    }
//
//    @GetMapping("/preparerAjout")
//    public String preparerAjout(Model model) {
//        model.addAttribute("nouvelApprenti", new Apprenti());
//        return "nouvelApprenti";
//    }
//
//    @PostMapping("/ajouter")
//    public String ajouterApprenti(@ModelAttribute Apprenti apprenti) {
//        apprentiService.ajouterUnApprenti(apprenti);
//        return "redirect:/apprentis";
//    }
//
//    @GetMapping("/preparerModif/{id}")
//    public String preparerModif(@PathVariable Integer id, Model model) {
//        Optional<Apprenti> apprenti = apprentiService.getUnApprenti(id);
//        model.addAttribute("apprentiToUpdate", apprenti.orElseThrow());
//        return "detailsApprenti";
//    }
//
//    @PutMapping("/modifier/{id}")
//    public String modifierApprenti(@PathVariable Integer id, @ModelAttribute Apprenti apprentiModifie) {
//        apprentiService.modifierUnApprenti(id, apprentiModifie);
//        return "redirect:/apprentis";
//    }
//
//    @DeleteMapping("/supprimer/{id}")
//    public String supprimerApprenti(@PathVariable Integer id) {
//        apprentiService.supprimerUnApprenti(id);
//        return "redirect:/apprentis";
//    }
//}
package com.altn72.projetasta.controleur;

import com.altn72.projetasta.modele.Apprenti;
import com.altn72.projetasta.controleur.service.ApprentiService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.Optional;

@Controller
@RequestMapping("/apprentis")
public class ApprentiControleur {

    private final ApprentiService apprentiService;

    public ApprentiControleur(ApprentiService apprentiService) {
        this.apprentiService = apprentiService;
    }

    // Route pour afficher tous les apprentis (conservée)
    @GetMapping
    public String afficherTousLesApprentis(Model model) {
        var apprentis = apprentiService.getTousLesApprentis();
        model.addAttribute("lesApprentis", apprentis);
        return "listeApprentis";
    }

    // Route pour les détails d'un apprenti (NOUVELLE)
    @GetMapping("/{id}")
    public String detailsApprenti(@PathVariable Integer id, Model model) {
        Optional<Apprenti> apprenti = apprentiService.getUnApprenti(id);
        if (apprenti.isPresent()) {
            model.addAttribute("apprenti", apprenti.get());
            return "apprenti-details";
        } else {
            return "redirect:/dashboard";
        }
    }

    // Route pour préparer l'ajout (conservée)
    @GetMapping("/preparerAjout")
    public String preparerAjout(Model model) {
        model.addAttribute("nouvelApprenti", new Apprenti());
        model.addAttribute("anneeEnCours", "2025-26");
        return "ajouter-apprenti";
    }

    // Route pour ajouter un apprenti (conservée avec amélioration)
    @PostMapping("/ajouter")
    public String ajouterApprenti(@ModelAttribute Apprenti apprenti,
                                  RedirectAttributes redirectAttributes) {
        try {
            apprentiService.ajouterUnApprenti(apprenti);
            redirectAttributes.addFlashAttribute("successMessage", "Apprenti ajouté avec succès !");
            return "redirect:/dashboard";
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("errorMessage", "Erreur lors de l'ajout : " + e.getMessage());
            return "redirect:/apprentis/preparerAjout";
        }
    }

    // Route pour préparer la modification (conservée)
    @GetMapping("/preparerModif/{id}")
    public String preparerModif(@PathVariable Integer id, Model model) {
        Optional<Apprenti> apprenti = apprentiService.getUnApprenti(id);
        model.addAttribute("apprentiToUpdate", apprenti.orElseThrow());
        return "detailsApprenti";
    }

    // Route pour modifier un apprenti (conservée avec amélioration)
    @PostMapping("/modifier/{id}")
    public String modifierApprenti(@PathVariable Integer id,
                                   @ModelAttribute Apprenti apprentiModifie,
                                   RedirectAttributes redirectAttributes) {
        try {
            apprentiService.modifierUnApprenti(id, apprentiModifie);
            redirectAttributes.addFlashAttribute("successMessage", "Modification réussie !");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("errorMessage", "Échec de la modification : " + e.getMessage());
        }
        return "redirect:/apprentis/" + id;
    }

    // Route pour supprimer un apprenti (conservée)
    @DeleteMapping("/supprimer/{id}")
    public String supprimerApprenti(@PathVariable Integer id) {
        apprentiService.supprimerUnApprenti(id);
        return "redirect:/apprentis";
    }
}