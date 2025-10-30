package com.altn72.projetasta.controleur;

import com.altn72.projetasta.modele.AnneeAcademique;
import com.altn72.projetasta.modele.Apprenti;
import com.altn72.projetasta.service.ApprentiService;
import jakarta.persistence.NamedNativeQuery;
import com.altn72.projetasta.modele.Visite;
import com.altn72.projetasta.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

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

    @Autowired
    private AnneeAcademiqueService anneeAcademiqueService;

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

    // 🔹 Identifiant de l’utilisateur connecté
    String identifiant = authentication.getName();
    var tuteur = tuteurService.getByIdentifiant(identifiant)
            .orElseThrow(() -> new IllegalStateException("Tuteur introuvable : " + identifiant));

    // 🔹 Année académique active
    AnneeAcademique anneeActuelle = anneeAcademiqueService.getAnneeEnCours();
    if (anneeActuelle == null) {
        model.addAttribute("errorMessage", "Aucune année académique active trouvée !");
        return "dashboard";
    }

    // Apprentis actifs de l’année
    List<Apprenti> apprentis = apprentiService.getTousLesApprentis().stream()
            .filter(a -> a.getAnneeAcademique() != null
                    && a.getAnneeAcademique().equals(anneeActuelle)
                    && !a.isArchive())
            .toList();
    long totalApprentisActifs = apprentis.size();

    // Apprentis du tuteur connecté
    List<Apprenti> apprentisTuteur = apprentiService.getApprentisParTuteur(identifiant).stream()
            .filter(a -> a.getAnneeAcademique() != null
                    && a.getAnneeAcademique().equals(anneeActuelle)
                    && !a.isArchive())
            .toList();
    long mesApprentis = apprentisTuteur.size();

    //  Statistiques et alertes
    List<Visite> visites = visiteService.getVisites().stream()
            .filter(v -> v.getApprenti() != null && apprentis.contains(v.getApprenti()))
            .toList();

    Map<String, Long> visitesParMois = visites.stream()
            .collect(Collectors.groupingBy(
                    v -> v.getDateVisite().getMonth().name(),
                    Collectors.counting()
            ));

    List<String> labels = Arrays.stream(Month.values()).map(Month::name).toList();
    List<Long> dataVisites = labels.stream()
            .map(m -> visitesParMois.getOrDefault(m, 0L))
            .toList();

    long totalRapports = rapportService.countTotal();
    long nonEvalues = rapportService.countNonEvalues();
    long evalues = totalRapports - nonEvalues;

    List<String> alertes = new ArrayList<>();
    if (nonEvalues > 0) alertes.add(nonEvalues + " rapport(s) non évalué(s)");
    long visitesNonPlanifiees = apprentis.stream()
            .filter(a -> a.getVisites() == null || a.getVisites().isEmpty())
            .count();
    if (visitesNonPlanifiees > 0) alertes.add(visitesNonPlanifiees + " apprenti(s) sans visite planifiée");

    List<Visite> visitesAVenir = visites.stream()
            .filter(v -> v.getDateVisite() != null && v.getDateVisite().isAfter(LocalDate.now()))
            .sorted(Comparator.comparing(Visite::getDateVisite))
            .limit(5)
            .toList();

    // Injection modèle Thymeleaf
    model.addAttribute("tuteurPrenom", tuteur.getPersonne().getPrenom());
    model.addAttribute("tuteurNom", tuteur.getPersonne().getNom());
    model.addAttribute("totalApprentis", totalApprentisActifs);
    model.addAttribute("mesApprentis", mesApprentis);
    model.addAttribute("visitesAVenir", visitesAVenir.size());
    model.addAttribute("rapportsNonEvalues", nonEvalues);
    model.addAttribute("alertes", alertes);
    model.addAttribute("visitesProchaines", visitesAVenir);
    model.addAttribute("labelsMois", labels);
    model.addAttribute("dataVisites", dataVisites);
    model.addAttribute("dataRapports", List.of(evalues, nonEvalues));

    return "dashboard";
}


    @PostMapping("/archiver-annee")
    public String archiverAnnee(RedirectAttributes redirectAttributes) {
        try {
            AnneeAcademique anneeActuelle = anneeAcademiqueService.getAnneeEnCours();
            AnneeAcademique nouvelle = anneeAcademiqueService.creerNouvelleAnnee();
            apprentiService.basculerVersNouvelleAnnee(anneeActuelle, nouvelle);

            redirectAttributes.addFlashAttribute("successMessage",
                    " Passage réussi de " + anneeActuelle.getCode() +
                            " vers " + nouvelle.getCode() +
                            " — les M2 ont été archivés et les autres apprentis promus !");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("errorMessage",
                    "Erreur lors de l'archivage : " + e.getMessage());
        }

        return "redirect:/dashboard";
    }
}