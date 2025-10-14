package com.altn72.projetasta.controleur;
import com.altn72.projetasta.modele.Entreprise;
import com.altn72.projetasta.modele.Formation;
import com.altn72.projetasta.controleur.service.FormationService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/formations")
public class FormationControleur {

    private final FormationService formationService;

    public FormationControleur(FormationService formationService) {
        this.formationService = formationService;
    }


    @GetMapping
    public String afficherToutesLesFormations(Model model) {
        List<Formation> formations = formationService.getToutesLesFormations();
        model.addAttribute("lesFormations", formations);
        return "listeFormations";
    }

    // GET pour les tests API (retourne du JSON)
    @GetMapping("/api")
    @ResponseBody
    public List<Formation> getToutesLesFormations() {
        return formationService.getToutesLesFormations();
    }

    @GetMapping("/{id}")
    @ResponseBody
    public Optional<Formation> afficherUneFormation(@PathVariable Integer id) {
        return formationService.getUneFormation(id);
    }


    @PostMapping
    @ResponseBody
    public Formation ajouterUneFormation(@RequestBody Formation formation) {
        return formationService.ajouterUneFormation(formation);
    }


    @DeleteMapping("/{id}")
    @ResponseBody
    public Optional<Formation> supprimerUneFormation(@PathVariable Integer id) {
        return formationService.supprimerUneFormation(id);
    }


    @PutMapping("/{id}")
    @ResponseBody
    public void modifierUneFormation(@PathVariable Integer id, @RequestBody Formation formation) {
        formationService.modifierUneFormation(id, formation);
    }

    @PatchMapping("/{id}")
    @ResponseBody
    public Optional<Formation> patchUneFormation(@PathVariable Integer id, @RequestBody Formation formation) {
        return formationService.patchUneFormation(id, formation);
    }

}