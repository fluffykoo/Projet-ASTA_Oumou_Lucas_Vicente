package com.altn72.projetasta.controleur;

import com.altn72.projetasta.modele.Apprenti;
import com.altn72.projetasta.modele.Visite;
import com.altn72.projetasta.service.ApprentiService;
import com.altn72.projetasta.service.RapportService;
import com.altn72.projetasta.service.TuteurEnseignantService;
import com.altn72.projetasta.service.VisiteService;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.time.LocalDate;
import java.time.Month;
import java.util.*;
import java.util.stream.Collectors;

@Controller
public class DashboardControleur {

    private final ApprentiService apprentiService;
    private final VisiteService visiteService;
    private final RapportService rapportService;
    private final TuteurEnseignantService tuteurService;

    public DashboardControleur(ApprentiService apprentiService,
                               VisiteService visiteService,
                               RapportService rapportService,
                               TuteurEnseignantService tuteurService) {
        this.apprentiService = apprentiService;
        this.visiteService = visiteService;
        this.rapportService = rapportService;
        this.tuteurService = tuteurService;
    }

    @GetMapping("/dashboard")
    public String afficherDashboard(Model model, Authentication authentication) {

        // Identifiant de l’utilisateur connecté
        String identifiant = authentication.getName();

        // Récupérer le tuteur connecté
        var tuteur = tuteurService.getByIdentifiant(identifiant)
                .orElseThrow(() -> new IllegalStateException("Tuteur introuvable : " + identifiant));

        // Apprentis rattachés à ce tuteur
        List<Apprenti> apprentis = apprentiService.getApprentisParTuteur(identifiant);

        // Visites associées à ces apprentis
        List<Visite> visites = visiteService.getVisites()
                .stream()
                .filter(v -> apprentis.contains(v.getApprenti()))
                .toList();

        // Statistiques des visites par mois
        Map<String, Long> visitesParMois = visites.stream()
                .collect(Collectors.groupingBy(
                        v -> v.getDateVisite().getMonth().name(),
                        Collectors.counting()
                ));

        List<String> labels = Arrays.stream(Month.values())
                .map(Month::name)
                .toList();

        List<Long> dataVisites = labels.stream()
                .map(m -> visitesParMois.getOrDefault(m, 0L))
                .toList();

        // Rapports évalués vs non évalués
        long totalRapports = rapportService.countTotal();
        long nonEvalues = rapportService.countNonEvalues();
        long evalues = totalRapports - nonEvalues;

        // Alertes dynamiques
        List<String> alertes = new ArrayList<>();
        if (nonEvalues > 0)
            alertes.add(nonEvalues + " rapport(s) non évalué(s)");
        long visitesNonPlanifiees = apprentis.stream()
                .filter(a -> a.getVisites().isEmpty())
                .count();
        if (visitesNonPlanifiees > 0)
            alertes.add(visitesNonPlanifiees + " apprenti(s) sans visite planifiée");

        // Prochaines visites (dates futures)
        List<Visite> visitesAVenir = visites.stream()
                .filter(v -> v.getDateVisite().isAfter(LocalDate.now()))
                .sorted(Comparator.comparing(Visite::getDateVisite))
                .limit(5)
                .toList();

        // Injection dans le modèle Thymeleaf
        model.addAttribute("tuteurPrenom", tuteur.getPersonne().getPrenom());
        model.addAttribute("tuteurNom", tuteur.getPersonne().getNom());
        model.addAttribute("totalApprentis", apprentis.size());
        model.addAttribute("visitesAVenir", visitesAVenir.size());
        model.addAttribute("rapportsNonEvalues", nonEvalues);
        model.addAttribute("alertes", alertes);
        model.addAttribute("visitesProchaines", visitesAVenir);

        // Données pour les graphiques
        model.addAttribute("labelsMois", labels);
        model.addAttribute("dataVisites", dataVisites);
        model.addAttribute("dataRapports", List.of(evalues, nonEvalues));

        return "dashboard";
    }
}