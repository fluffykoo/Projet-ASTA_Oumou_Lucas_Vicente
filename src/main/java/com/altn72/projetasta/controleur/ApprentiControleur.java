//package com.altn72.projetasta.controleur;
//
//import com.altn72.projetasta.modele.*;
//import com.altn72.projetasta.repository.EntrepriseRepository;
//import com.altn72.projetasta.repository.PersonneRepository;
//import com.altn72.projetasta.service.*;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Controller;
//import org.springframework.ui.Model;
//import org.springframework.web.bind.annotation.*;
//import org.springframework.web.servlet.mvc.support.RedirectAttributes;
//
//import java.util.Optional;
//
//@Controller
//@RequestMapping("/apprentis")
//public class ApprentiControleur {
//
//    @Autowired
//    private ApprentiService apprentiService;
//
//    @Autowired
//    private PersonneRepository personneRepository;
//
//    @Autowired
//    private EntrepriseRepository entrepriseRepository;
//
//    @Autowired
//    private VisiteService visiteService;
//
//    @Autowired
//    private EvaluationRapportService evaluationRapportService;
//
//    @Autowired
//    private SoutenanceService soutenanceService;
//
//    // Liste de tous les apprentis
//    @GetMapping
//    public String afficherTousLesApprentis(Model model) {
//        model.addAttribute("lesApprentis", apprentiService.getTousLesApprentis());
//        return "listeApprentis";
//    }
//
//    // Détails d’un apprenti (lecture seule)
//    @GetMapping("/{id}")
//    public String detailsApprenti(@PathVariable Integer id, Model model, RedirectAttributes redirectAttributes) {
//        Optional<Apprenti> apprentiOpt = apprentiService.getUnApprenti(id);
//
//        if (apprentiOpt.isEmpty()) {
//            redirectAttributes.addFlashAttribute("errorMessage", "Apprenti introuvable !");
//            return "redirect:/dashboard";
//        }
//
//        Apprenti apprenti = apprentiOpt.get();
//        model.addAttribute("apprenti", apprenti);
//
//        // données associées
//        model.addAttribute("visites", apprenti.getVisites());
//        model.addAttribute("nouvelleVisite", new Visite());
//        model.addAttribute("newEvaluation", new EvaluationRapport());
//        model.addAttribute("newSoutenance", new Soutenance());
//        model.addAttribute("entreprises", entrepriseRepository.findAll());
//
//        return "apprenti-details";
//    }
//
//    // Préparer le formulaire d’ajout
//    @GetMapping("/preparerAjout")
//    public String preparerAjout(Model model) {
//        model.addAttribute("nouvelApprenti", new Apprenti());
//        model.addAttribute("entreprises", entrepriseRepository.findAll());
//        model.addAttribute("anneeEnCours", "2025-2026");
//        return "ajouter-apprenti";
//    }
//
//    // Ajouter un apprenti
//    @PostMapping
//    public String ajouterApprenti(@ModelAttribute("nouvelApprenti") Apprenti apprenti,
//                                  RedirectAttributes redirectAttributes) {
//        try {
//            Personne p = apprenti.getPersonne();
//            personneRepository.save(p);
//            apprenti.setPersonne(p);
//            apprentiService.ajouterApprenti(apprenti);
//            redirectAttributes.addFlashAttribute("successMessage", "Apprenti ajouté avec succès !");
//            return "redirect:/dashboard";
//        } catch (Exception e) {
//            redirectAttributes.addFlashAttribute("errorMessage", "Erreur : " + e.getMessage());
//            return "redirect:/apprentis/preparerAjout";
//        }
//    }
//
//    // Modifier un apprenti (PUT)
//    @PutMapping("/{id}")
//    public String modifierApprenti(@PathVariable Integer id,
//                                   @ModelAttribute Apprenti apprentiModifie,
//                                   RedirectAttributes redirectAttributes) {
//        try {
//            apprentiService.modifierApprenti(id, apprentiModifie);
//            redirectAttributes.addFlashAttribute("successMessage", "Apprenti modifié avec succès !");
//        } catch (Exception e) {
//            redirectAttributes.addFlashAttribute("errorMessage", "Erreur de modification : " + e.getMessage());
//        }
//        return "redirect:/apprentis/" + id;
//    }
//
//    // Supprimer un apprenti
//    @DeleteMapping("/{id}")
//    public String supprimerApprenti(@PathVariable Integer id, RedirectAttributes redirectAttributes) {
//        try {
//            apprentiService.supprimerApprenti(id);
//            redirectAttributes.addFlashAttribute("successMessage", "Apprenti supprimé avec succès !");
//        } catch (Exception e) {
//            redirectAttributes.addFlashAttribute("errorMessage", "Erreur lors de la suppression : " + e.getMessage());
//        }
//        return "redirect:/dashboard";
//    }
//
//    //  Ajouter une visite depuis la fiche apprenti
//    @PostMapping("/{id}/visites")
//    public String ajouterVisiteApprenti(@PathVariable Integer id,
//                                        @ModelAttribute("nouvelleVisite") Visite visite,
//                                        RedirectAttributes redirectAttributes) {
//        try {
//            visiteService.ajouterVisitePourApprenti(id, visite);
//            redirectAttributes.addFlashAttribute("successMessage", "Visite ajoutée avec succès !");
//        } catch (Exception e) {
//            redirectAttributes.addFlashAttribute("errorMessage", "Erreur ajout visite : " + e.getMessage());
//        }
//        return "redirect:/apprentis/" + id;
//    }
//
//    // Ajouter une évaluation depuis la fiche apprenti
//    @PostMapping("/{id}/evaluations")
//    public String ajouterEvaluationApprenti(@PathVariable Integer id,
//                                            @ModelAttribute("newEvaluation") EvaluationRapport evaluation,
//                                            RedirectAttributes redirectAttributes) {
//        try {
//            // Récupérer l’apprenti
//            Apprenti apprenti = apprentiService.getUnApprenti(id)
//                    .orElseThrow(() -> new IllegalStateException("Apprenti introuvable"));
//
//            // Récupérer le rapport associé (ici on prend le premier rapport pour simplifier)
//            if (apprenti.getRapports().isEmpty()) {
//                throw new IllegalStateException("Aucun rapport trouvé pour cet apprenti");
//            }
//            Rapport rapport = apprenti.getRapports().get(0);
//
//            //  Associer le rapport et un tuteur évaluateur fictif (id 1 ici à remplacer plus tard)
//            evaluation.setRapport(rapport);
//            evaluation.setTuteurEnseignant(rapport.getApprenti().getVisites().get(0).getTuteurEnseignant());
//
//            // Sauvegarder
//            evaluationRapportService.ajouterEvaluation(evaluation);
//
//            redirectAttributes.addFlashAttribute("successMessage", "Évaluation ajoutée avec succès !");
//        } catch (Exception e) {
//            redirectAttributes.addFlashAttribute("errorMessage", "Erreur ajout évaluation : " + e.getMessage());
//        }
//
//        return "redirect:/apprentis/" + id;
//    }
//
//    // Ajouter une soutenance depuis la fiche apprenti
//    @PostMapping("/{id}/soutenances")
//    public String ajouterSoutenanceApprenti(@PathVariable Integer id,
//                                            @ModelAttribute("newSoutenance") Soutenance soutenance,
//                                            RedirectAttributes redirectAttributes) {
//        try {
//            soutenanceService.ajouterSoutenance(soutenance);
//            redirectAttributes.addFlashAttribute("successMessage", "Soutenance ajoutée avec succès !");
//        } catch (Exception e) {
//            redirectAttributes.addFlashAttribute("errorMessage", "Erreur ajout soutenance : " + e.getMessage());
//        }
//        return "redirect:/apprentis/" + id;
//    }
//}
package com.altn72.projetasta.controleur;

import com.altn72.projetasta.modele.*;
import com.altn72.projetasta.repository.EntrepriseRepository;
import com.altn72.projetasta.repository.PersonneRepository;
import com.altn72.projetasta.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/apprentis")
public class ApprentiControleur {

    @Autowired
    private ApprentiService apprentiService;

    @Autowired
    private PersonneRepository personneRepository;

    @Autowired
    private EntrepriseRepository entrepriseRepository;

    @Autowired
    private VisiteService visiteService;

    @Autowired
    private EvaluationRapportService evaluationRapportService;

    @Autowired
    private SoutenanceService soutenanceService;

    @Autowired
    private RapportService rapportService;

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
        if (apprentiOpt.isEmpty()) {
            redirectAttributes.addFlashAttribute("errorMessage", "Apprenti introuvable !");
            return "redirect:/dashboard";
        }

        Apprenti apprenti = apprentiOpt.get();
        model.addAttribute("apprenti", apprenti);
        model.addAttribute("visites", apprenti.getVisites());
        model.addAttribute("nouvelleVisite", new Visite());
        model.addAttribute("newEvaluation", new EvaluationRapport());
        model.addAttribute("newSoutenance", new Soutenance());
        model.addAttribute("entreprises", entrepriseRepository.findAll());

        // Charger les rapports non évalués
        List<Rapport> rapportsNonNotes = rapportService.getRapportsNonEvaluesPourApprenti(id);
        model.addAttribute("rapportsNonNotes", rapportsNonNotes);

        return "apprenti-details";
    }
     //Ajouter un apprenti
    @PostMapping
    public String ajouterApprenti(@ModelAttribute("nouvelApprenti") Apprenti apprenti,
                                  RedirectAttributes redirectAttributes) {
        try {
            Personne p = apprenti.getPersonne();
            personneRepository.save(p);
            apprenti.setPersonne(p);
            apprentiService.ajouterApprenti(apprenti);
            redirectAttributes.addFlashAttribute("successMessage", "Apprenti ajouté avec succès !");
            return "redirect:/dashboard";
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("errorMessage", "Erreur : " + e.getMessage());
            return "redirect:/apprentis/preparerAjout";
        }
    }

    // Modifier un apprenti (PUT)
    @PutMapping("/{id}")
    public String modifierApprenti(@PathVariable Integer id,
                                   @ModelAttribute Apprenti apprentiModifie,
                                   RedirectAttributes redirectAttributes) {
        try {
            apprentiService.modifierApprenti(id, apprentiModifie);
            redirectAttributes.addFlashAttribute("successMessage", "Apprenti modifié avec succès !");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("errorMessage", "Erreur de modification : " + e.getMessage());
        }
        return "redirect:/apprentis/" + id;
    }

    // Supprimer un apprenti
    @DeleteMapping("/{id}")
    public String supprimerApprenti(@PathVariable Integer id, RedirectAttributes redirectAttributes) {
        try {
            apprentiService.supprimerApprenti(id);
            redirectAttributes.addFlashAttribute("successMessage", "Apprenti supprimé avec succès !");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("errorMessage", "Erreur lors de la suppression : " + e.getMessage());
        }
        return "redirect:/dashboard";
    }

    //  Ajouter une visite depuis la fiche apprenti
    @PostMapping("/{id}/visites")
    public String ajouterVisiteApprenti(@PathVariable Integer id,
                                        @ModelAttribute("nouvelleVisite") Visite visite,
                                        RedirectAttributes redirectAttributes) {
        try {
            visiteService.ajouterVisitePourApprenti(id, visite);
            redirectAttributes.addFlashAttribute("successMessage", "Visite ajoutée avec succès !");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("errorMessage", "Erreur ajout visite : " + e.getMessage());
        }
        return "redirect:/apprentis/" + id;
    }
    // Ajouter une évaluation pour un rapport sélectionné
    @PostMapping("/{id}/evaluations")
    public String ajouterEvaluationApprenti(@PathVariable Integer id,
                                            @ModelAttribute("newEvaluation") EvaluationRapport evaluation,
                                            RedirectAttributes redirectAttributes) {
        try {
            // Récupère l'apprenti
            Apprenti apprenti = apprentiService.getUnApprenti(id)
                    .orElseThrow(() -> new IllegalStateException("Apprenti introuvable"));

            // Pour éviter le bug Hibernate : forcer l’ID à null INSERT au lieu d’UPDATE
            evaluation.setId(null);

            // Récupère le rapport sélectionné dans le formulaire

            Rapport rapport = apprenti.getRapports().get(0);

            evaluation.setRapport(rapport);
            evaluation.setApprenti(apprenti);


            if (!apprenti.getVisites().isEmpty()) {
                evaluation.setTuteurEnseignant(apprenti.getVisites().get(0).getTuteurEnseignant());
            }

            evaluationRapportService.ajouterEvaluation(evaluation);
            redirectAttributes.addFlashAttribute("successMessage", "Évaluation ajoutée avec succès !");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("errorMessage", "Erreur ajout évaluation : " + e.getMessage());
        }
        return "redirect:/apprentis/" + id;
    }
}