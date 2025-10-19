package com.altn72.projetasta.controleur.api;

import com.altn72.projetasta.modele.Visite;
import com.altn72.projetasta.controleur.service.VisiteService;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/visites")
public class VisiteApiControleur {

    private final VisiteService visiteService;

    public VisiteApiControleur(VisiteService visiteService) {
        this.visiteService = visiteService;
    }

    @GetMapping
    public List<Visite> getToutesLesVisites() {
        return visiteService.getToutesLesVisites();
    }

    @GetMapping("/{id}")
    public Optional<Visite> getUneVisite(@PathVariable Integer id) {
        return visiteService.getUneVisite(id);
    }

    @PostMapping
    public Visite ajouterUneVisite(@RequestBody Visite visite) {
        return visiteService.ajouterUneVisite(visite);
    }

    @PutMapping("/{id}")
    public void modifierUneVisite(@PathVariable Integer id, @RequestBody Visite visite) {
        visiteService.modifierUneVisite(id, visite);
    }

    @DeleteMapping("/{id}")
    public Optional<Visite> supprimerUneVisite(@PathVariable Integer id) {
        return visiteService.supprimerUneVisite(id);
    }
}