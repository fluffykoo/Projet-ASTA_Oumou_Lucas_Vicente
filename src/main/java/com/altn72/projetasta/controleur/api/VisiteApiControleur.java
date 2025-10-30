package com.altn72.projetasta.controleur.api;

import com.altn72.projetasta.modele.Visite;
import com.altn72.projetasta.service.VisiteService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/visites")
public class VisiteApiControleur {

    private final VisiteService visiteService;

    public VisiteApiControleur(VisiteService visiteService) {
        this.visiteService = visiteService;
    }

    // Récupérer toutes les visites
    @GetMapping
    public List<Visite> getAllVisites() {
        return visiteService.getVisites();
    }

    // Récupérer une visite par ID
    @GetMapping("/{id}")
    public ResponseEntity<Visite> getVisiteById(@PathVariable Integer id) {
        return visiteService.getUneVisite(id)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    // Créer une nouvelle visite
    @PostMapping
    public ResponseEntity<Visite> createVisite(@RequestBody Visite visite) {
        visite.setId(null);
        visiteService.ajouterVisite(visite);
        return ResponseEntity.status(HttpStatus.CREATED).body(visite);
    }

    // Modifier une visite existante
    @PutMapping("/{id}")
    public ResponseEntity<Visite> updateVisite(@PathVariable Integer id, @RequestBody Visite visiteUpdated) {
        try {
            visiteUpdated.setId(id);
            visiteService.modifierVisite(id, visiteUpdated);
            return visiteService.getUneVisite(id)
                    .map(ResponseEntity::ok)
                    .orElseGet(() -> ResponseEntity.notFound().build());
        } catch (IllegalStateException ex) {
            return ResponseEntity.notFound().build();
        }
    }

    // Supprimer une visite
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteVisite(@PathVariable Integer id) {
        try {
            visiteService.supprimerVisite(id);
            return ResponseEntity.noContent().build();
        } catch (IllegalStateException ex) {
            return ResponseEntity.notFound().build();
        }
    }
}