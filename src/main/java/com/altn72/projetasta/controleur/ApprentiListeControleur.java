package com.altn72.projetasta.controleur;

import com.altn72.projetasta.modele.AnneeAcademique;
import com.altn72.projetasta.modele.Apprenti;
import com.altn72.projetasta.service.AnneeAcademiqueService;
import com.altn72.projetasta.service.ApprentiService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
public class ApprentiListeControleur {

    private final ApprentiService apprentiService;
    private final AnneeAcademiqueService anneeAcademiqueService;

    public ApprentiListeControleur(ApprentiService apprentiService,
                                   AnneeAcademiqueService anneeAcademiqueService) {
        this.apprentiService = apprentiService;
        this.anneeAcademiqueService = anneeAcademiqueService;
    }

    @GetMapping("/apprentis")
    public String listerApprentis(
            @RequestParam(required = false) String nom,
            @RequestParam(required = false) String entreprise,
            @RequestParam(required = false) String motCle,
            @RequestParam(required = false) String anneeAcademique,
            Model model) {

        // 🔹 Récupération de l’année académique en cours
        AnneeAcademique anneeActuelle = anneeAcademiqueService.getAnneeEnCours();

        // 🔹 Apprentis actifs uniquement pour l’année en cours
        List<Apprenti> apprentis = apprentiService.getApprentisParAnnee(anneeActuelle);

        // 🔹 (optionnel) filtrage par nom si champ renseigné
        if (nom != null && !nom.isEmpty()) {
            apprentis = apprentis.stream()
                    .filter(a -> a.getPersonne() != null &&
                            a.getPersonne().getNom().toLowerCase().contains(nom.toLowerCase()))
                    .toList();
        }

        // 🔹 Injection dans le modèle
        model.addAttribute("apprentis", apprentis);
        model.addAttribute("totalApprentis", apprentis.size());  // ✅ même chiffre que sur le dashboard
        model.addAttribute("anneeEnCours", anneeActuelle.getCode());
        model.addAttribute("anneesAcademiques", List.of("2023-24", "2024-25", "2025-26"));

        return "apprentis/liste";
    }
}