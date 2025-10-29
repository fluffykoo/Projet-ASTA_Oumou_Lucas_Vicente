package com.altn72.projetasta.controleur;

import com.altn72.projetasta.modele.Apprenti;
import com.altn72.projetasta.service.ApprentiService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
public class ApprentiListeControleur {

    private final ApprentiService apprentiService;

    public ApprentiListeControleur(ApprentiService apprentiService) {
        this.apprentiService = apprentiService;
    }

    @GetMapping("/apprentis")
    public String listerApprentis(
            @RequestParam(required = false) String nom,
            @RequestParam(required = false) String entreprise,
            @RequestParam(required = false) String motCle,
            @RequestParam(required = false) String anneeAcademique,
            Model model) {

        List<Apprenti> apprentis = apprentiService.getTousLesApprentis();

        if (nom != null && !nom.isEmpty()) {
            apprentis = apprentis.stream()
                    .filter(a -> a.getPersonne().getNom().toLowerCase().contains(nom.toLowerCase()))
                    .toList();
        }

        model.addAttribute("apprentis", apprentis);
        model.addAttribute("anneeEnCours", "2025-26");
        model.addAttribute("anneesAcademiques", List.of("2023-24", "2024-25", "2025-26"));

        return "apprentis/liste";
    }
}