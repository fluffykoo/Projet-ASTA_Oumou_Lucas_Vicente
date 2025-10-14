package com.altn72.projetasta.controleur;

import com.altn72.projetasta.modele.Entreprise;
import com.altn72.projetasta.controleur.service.EntrepriseService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/entreprises")
public class EntrepriseControleur {

    private final EntrepriseService entrepriseService;

    public EntrepriseControleur(EntrepriseService entrepriseService) {
        this.entrepriseService = entrepriseService;
    }

    // GET : afficher la liste des entreprises
    @GetMapping
    public String afficherToutesLesEntreprises(Model model) {
        List<Entreprise> entreprises = entrepriseService.getToutesLesEntreprises();
        model.addAttribute("lesEntreprises", entreprises);
        return "listeEntreprises"; // pour le template Thymeleaf
    }
    // GET pour les tests API (retourne du JSON)
    @GetMapping("/api")
    @ResponseBody
    public List<Entreprise> getToutesLesEntreprisesAPI() {
        return entrepriseService.getToutesLesEntreprises();
    }

    // GET : récupérer une entreprise par son id
    @GetMapping("/{id}")
    @ResponseBody
    public Optional<Entreprise> afficherUneEntreprise(@PathVariable Integer id) {
        return entrepriseService.getUneEntreprise(id);
    }

    // POST : ajouter une entreprise
    @PostMapping
    @ResponseBody
    public Entreprise ajouterUneEntreprise(@RequestBody Entreprise entreprise) {
        return entrepriseService.ajouterUneEntreprise(entreprise);
    }

    //DELETE : supprimer une entreprise
    @DeleteMapping("/{id}")
    @ResponseBody
    public Optional<Entreprise> supprimerUneEntreprise(@PathVariable Integer id) {
        return entrepriseService.supprimerUneEntreprise(id);
    }

    //PUT : modifier une entreprise en sa totalité
    @PutMapping("/{id}")
    @ResponseBody
    public void modifierUneEntreprise(@PathVariable Integer id, @RequestBody Entreprise entreprise) {
        entrepriseService.modifierUneEntreprise(id, entreprise);
    }
}