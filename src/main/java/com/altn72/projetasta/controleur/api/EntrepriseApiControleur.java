package com.altn72.projetasta.controleur.api;

import com.altn72.projetasta.dto.EntrepriseDTO;
import com.altn72.projetasta.modele.Entreprise;
import com.altn72.projetasta.service.EntrepriseService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/entreprises")
public class EntrepriseApiControleur {

    private final EntrepriseService entrepriseService;

    public EntrepriseApiControleur(EntrepriseService entrepriseService) {
        this.entrepriseService = entrepriseService;
    }

    // GET - toutes les entreprises
    @GetMapping
    public List<EntrepriseDTO> getAllEntreprises() {
        return entrepriseService.getEntreprises()
                .stream()
                .map(EntrepriseDTO::new)
                .collect(Collectors.toList());
    }

    // GET - une entreprise par ID
    @GetMapping("/{id}")
    public ResponseEntity<EntrepriseDTO> getEntrepriseById(@PathVariable Integer id) {
        return entrepriseService.getUneEntreprise(id)
                .map(e -> ResponseEntity.ok(new EntrepriseDTO(e)))
                .orElse(ResponseEntity.notFound().build());
    }

    // POST - créer une entreprise
    @PostMapping
    public ResponseEntity<EntrepriseDTO> createEntreprise(@RequestBody Entreprise entreprise) {
        try {
            entrepriseService.ajouterEntreprise(entreprise);
            return ResponseEntity.status(HttpStatus.CREATED).body(new EntrepriseDTO(entreprise));
        } catch (Exception ex) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
    }

    // PUT - modifier une entreprise
    @PutMapping("/{id}")
    public ResponseEntity<EntrepriseDTO> updateEntreprise(@PathVariable Integer id, @RequestBody Entreprise updated) {
        try {
            entrepriseService.modifierEntreprise(id, updated);
            return entrepriseService.getUneEntreprise(id)
                    .map(e -> ResponseEntity.ok(new EntrepriseDTO(e)))
                    .orElse(ResponseEntity.notFound().build());
        } catch (Exception ex) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
    }

    // DELETE - supprimer une entreprise
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteEntreprise(@PathVariable Integer id) {
        try {
            entrepriseService.supprimerEntreprise(id);
            return ResponseEntity.noContent().build();
        } catch (Exception ex) {
            return ResponseEntity.notFound().build();
        }
    }
}