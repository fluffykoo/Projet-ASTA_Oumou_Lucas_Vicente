//package com.altn72.projetasta.controleur;
//
//import org.springframework.stereotype.Controller;
//import org.springframework.web.bind.annotation.GetMapping;
//
//@Controller
//public class DashboardControleur {
//
//    @GetMapping("/dashboard")
//    public String dashboard() {
//        return "dashboard";
//    }
//}
package com.altn72.projetasta.controleur;

import com.altn72.projetasta.modele.Apprenti;
import com.altn72.projetasta.controleur.service.ApprentiService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
public class DashboardControleur {

    private final ApprentiService apprentiService;

    public DashboardControleur(ApprentiService apprentiService) {
        this.apprentiService = apprentiService;
    }

    @GetMapping("/dashboard")
    public String dashboard(
            @RequestParam(required = false) String nom,
            @RequestParam(required = false) String entreprise,
            @RequestParam(required = false) String motCle,
            @RequestParam(required = false) String anneeAcademique,
            Model model) {

        // Récupérer tous les apprentis (à adapter avec votre service)
        List<Apprenti> apprentis = apprentiService.getTousLesApprentis();

        // Filtrer selon les critères de recherche (à implémenter dans le service)
        if (nom != null && !nom.isEmpty()) {
            apprentis = apprentis.stream()
                    .filter(a -> a.getNom().toLowerCase().contains(nom.toLowerCase()))
                    .toList();
        }

        model.addAttribute("apprentis", apprentis);
        model.addAttribute("anneeEnCours", "2025-26");
        model.addAttribute("anneesAcademiques", List.of("2023-24", "2024-25", "2025-26"));

        return "dashboard";
    }
}