package com.altn72.projetasta.controleur.api;

import com.altn72.projetasta.modele.Entreprise;
import com.altn72.projetasta.controleur.service.EntrepriseService;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/entreprises")
public class EntrepriseApiControleur {

    private final EntrepriseService entrepriseService;

    public EntrepriseApiControleur(EntrepriseService entrepriseService) {
        this.entrepriseService = entrepriseService;
    }

    @GetMapping
    public List<Entreprise> getToutesLesEntreprises() {
        return entrepriseService.getToutesLesEntreprises();
    }

    @GetMapping("/{id}")
    public Optional<Entreprise> getUneEntreprise(@PathVariable Integer id) {
        return entrepriseService.getUneEntreprise(id);
    }

    @PostMapping
    public Entreprise ajouterEntreprise(@RequestBody Entreprise entreprise) {
        return entrepriseService.ajouterUneEntreprise(entreprise);
    }

    @PutMapping("/{id}")
    public void modifierEntreprise(@PathVariable Integer id, @RequestBody Entreprise entreprise) {
        entrepriseService.modifierUneEntreprise(id, entreprise);
    }

    @DeleteMapping("/{id}")
    public Optional<Entreprise> supprimerEntreprise(@PathVariable Integer id) {
        return entrepriseService.supprimerUneEntreprise(id);
    }
}