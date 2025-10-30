package com.altn72.projetasta.controleur.api;

import com.altn72.projetasta.dto.ApprentiDTO;
import com.altn72.projetasta.modele.Apprenti;
import com.altn72.projetasta.service.ApprentiService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/apprentis")
public class ApprentiApiControleur {

    private final ApprentiService apprentiService;

    public ApprentiApiControleur(ApprentiService apprentiService) {
        this.apprentiService = apprentiService;
    }


    // Liste des apprentis en version DTO
    @GetMapping
    public List<ApprentiDTO> getAllApprentis() {
        return apprentiService.getTousLesApprentis()
                .stream()
                .map(ApprentiDTO::new)
                .collect(Collectors.toList());
    }

    // Détail d’un apprenti
    @GetMapping("/{id}")
    public ResponseEntity<ApprentiDTO> getApprentiById(@PathVariable Integer id) {
        Optional<Apprenti> apprentiOpt = apprentiService.getUnApprenti(id);
        return apprentiOpt
                .map(apprenti -> ResponseEntity.ok(new ApprentiDTO(apprenti)))
                .orElse(ResponseEntity.notFound().build());
    }

    // Créer un nouvel apprenti
    @PostMapping
    public ResponseEntity<Apprenti> createApprenti(@RequestBody Apprenti apprenti) {
        apprentiService.ajouterApprenti(apprenti);
        return ResponseEntity.status(HttpStatus.CREATED).body(apprenti);
    }

    // Modifier un apprenti existant
    @PutMapping("/{id}")
    public ResponseEntity<Apprenti> updateApprenti(@PathVariable Integer id, @RequestBody Apprenti apprentiUpdated) {
        try {
            apprentiUpdated.setId(id);
            apprentiService.modifierApprenti(id, apprentiUpdated);
            return apprentiService.getUnApprenti(id)
                    .map(ResponseEntity::ok)
                    .orElseGet(() -> ResponseEntity.notFound().build());
        } catch (IllegalStateException ex) {
            return ResponseEntity.notFound().build();
        }
    }

    // Supprimer un apprenti
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteApprenti(@PathVariable Integer id) {
        try {
            apprentiService.supprimerApprenti(id);
            return ResponseEntity.noContent().build();
        } catch (IllegalStateException ex) {
            return ResponseEntity.notFound().build();
        }
    }
}