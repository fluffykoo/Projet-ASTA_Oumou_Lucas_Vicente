package com.altn72.projetasta.controleur;

import com.altn72.projetasta.modele.Apprenti;
import com.altn72.projetasta.modele.Personne;
import com.altn72.projetasta.modele.Entreprise;
import com.altn72.projetasta.service.EntrepriseService;
import com.altn72.projetasta.service.PersonneService;
import com.altn72.projetasta.service.ApprentiService;
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

    private final ApprentiService apprentiService;
    private final PersonneService personneService;
    private final EntrepriseService entrepriseService;
    private final VisiteService visiteService;
    private final EvaluationRapportService evaluationRapportService;

    public ApprentiControleur(ApprentiService apprentiService,
                              PersonneService personneService,
                              EntrepriseService entrepriseService,
                              VisiteService visiteService,
                              EvaluationRapportService evaluationRapportService) {
        this.apprentiService = apprentiService;
        this.personneService = personneService;
        this.entrepriseService = entrepriseService;
        this.visiteService = visiteService;
        this.evaluationRapportService = evaluationRapportService;
    }

    @Autowired
    private RapportService rapportService;

//    // Liste de tous les apprentis
//    @GetMapping
//    public String afficherTousLesApprentis(Model model) {
//        model.addAttribute("lesApprentis", apprentiService.getTousLesApprentis());
//        return "listeApprentis";
//    }


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
        model.addAttribute("entreprises", entrepriseService.getEntreprises());

        // Charger les rapports non évalués
        List<Rapport> rapportsNonNotes = rapportService.getRapportsNonEvaluesPourApprenti(id);
        model.addAttribute("rapportsNonNotes", rapportsNonNotes);

        return "apprenti-details";
    }

    // Préparer le formulaire d’ajout
    @GetMapping("/preparerAjout")
    public String preparerAjout(Model model) {
        model.addAttribute("nouvelApprenti", new Apprenti());
        model.addAttribute("entreprises", entrepriseService.getEntreprises());
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
            personneService.ajouterPersonne(p);

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
            model.addAttribute("entreprises", entrepriseService.getEntreprises());
            return "apprenti-details";
        }
        return "redirect:/dashboard";
    }

    // Modifier un apprenti
    @PutMapping("/modifier/{id}")
    public String modifierApprentiSeul(@PathVariable Integer id,
                                   @ModelAttribute Apprenti apprentiModifie,
                                   RedirectAttributes redirectAttributes) {
        try {
            apprentiService.modifierApprenti(id, apprentiModifie);
            redirectAttributes.addFlashAttribute("successMessage", "Apprenti modifié avec succès !");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("errorMessage", "Échec de la modification : " + e.getMessage());
        }
        return "redirect:/apprentis/" + id;
    }

    @PostMapping("/modifierTotalApprenti/{id}")
    public String modifierTotalApprenti(
            @PathVariable Integer id,
            @ModelAttribute("apprenti") Apprenti apprentiModifie,
            RedirectAttributes redirectAttributes) {
        try {
            // Appelle les services avec les sous-objets déjà présents dans apprenti
            entrepriseService.modifierEntreprise(
                    apprentiModifie.getEntreprise().getId(),
                    apprentiModifie.getEntreprise()
            );
            personneService.modifierPersonne(
                    apprentiModifie.getPersonne().getId(),
                    apprentiModifie.getPersonne()
            );
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
    public String ajouterEvaluationApprenti(
            @PathVariable Integer id,
            @ModelAttribute("newEvaluation") EvaluationRapport evaluation,
            RedirectAttributes redirectAttributes) {

        try {
            // Vérifier que l'apprenti existe
            Apprenti apprenti = apprentiService.getUnApprenti(id)
                    .orElseThrow(() -> new IllegalStateException("Apprenti introuvable"));

            // Vérifier que le rapport a été choisi dans le formulaire
            if (evaluation.getRapport() == null || evaluation.getRapport().getId() == null) {
                throw new IllegalStateException("Aucun rapport sélectionné !");
            }

            //  Charger le rapport complet depuis la BDD
            Rapport rapport = rapportService.getUnRapport(evaluation.getRapport().getId())
                    .orElseThrow(() -> new IllegalStateException("Rapport introuvable !"));

            // Associer l'apprenti et le rapport
            evaluation.setId(null); // force un INSERT (sinon Hibernate fait un merge)
            evaluation.setApprenti(apprenti);
            evaluation.setRapport(rapport);

            //Récupérer le tuteur enseignant à partir de la dernière visite, s’il existe
            if (!apprenti.getVisites().isEmpty()) {
                evaluation.setTuteurEnseignant(
                        apprenti.getVisites().get(0).getTuteurEnseignant()
                );
            } else {
                throw new IllegalStateException("Aucune visite enregistrée pour déterminer le tuteur enseignant !");
            }

            //Enregistrer l’évaluation
            evaluationRapportService.ajouterEvaluation(evaluation);
            redirectAttributes.addFlashAttribute("successMessage", "Évaluation ajoutée avec succès !");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("errorMessage", "Erreur ajout évaluation : " + e.getMessage());
        }

        // Recharge la page de l'apprenti
        return "redirect:/apprentis/" + id;
    }
}