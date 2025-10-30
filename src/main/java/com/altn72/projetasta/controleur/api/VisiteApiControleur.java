package com.altn72.projetasta.controleur.api;

import com.altn72.projetasta.dto.VisiteDTO;
import com.altn72.projetasta.dto.VisiteRequestDTO;
import com.altn72.projetasta.modele.Apprenti;
import com.altn72.projetasta.modele.Entreprise;
import com.altn72.projetasta.modele.TuteurEnseignant;
import com.altn72.projetasta.modele.Visite;
import com.altn72.projetasta.repository.ApprentiRepository;
import com.altn72.projetasta.repository.EntrepriseRepository;
import com.altn72.projetasta.repository.TuteurEnseignantRepository;
import com.altn72.projetasta.service.VisiteService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/visites")
public class VisiteApiControleur {

    private final VisiteService visiteService;
    private final ApprentiRepository apprentiRepo;
    private final EntrepriseRepository entrepriseRepo;
    private final TuteurEnseignantRepository tuteurRepo;

    public VisiteApiControleur(VisiteService visiteService,
                               ApprentiRepository apprentiRepo,
                               EntrepriseRepository entrepriseRepo,
                               TuteurEnseignantRepository tuteurRepo) {
        this.visiteService = visiteService;
        this.apprentiRepo = apprentiRepo;
        this.entrepriseRepo = entrepriseRepo;
        this.tuteurRepo = tuteurRepo;
    }

    // 🔹 GET — Toutes les visites
    @GetMapping
    public List<VisiteDTO> getAllVisites() {
        return visiteService.getVisites().stream()
                .map(VisiteDTO::new)
                .toList();
    }

    // 🔹 GET — Une visite
    @GetMapping("/{id}")
    public ResponseEntity<VisiteDTO> getVisiteById(@PathVariable Integer id) {
        return visiteService.getUneVisite(id)
                .map(v -> ResponseEntity.ok(new VisiteDTO(v)))
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    // 🔹 POST — Créer une visite
    @PostMapping
    public ResponseEntity<VisiteDTO> createVisite(@RequestBody VisiteRequestDTO dto) {
        Visite visite = dto.toEntity();

        Apprenti apprenti = apprentiRepo.findById(dto.getIdApprenti())
                .orElseThrow(() -> new IllegalStateException("Apprenti introuvable"));
        Entreprise entreprise = entrepriseRepo.findById(dto.getIdEntreprise())
                .orElseThrow(() -> new IllegalStateException("Entreprise introuvable"));
        TuteurEnseignant tuteur = tuteurRepo.findById(dto.getIdTuteurEnseignant())
                .orElseThrow(() -> new IllegalStateException("Tuteur enseignant introuvable"));

        visite.setApprenti(apprenti);
        visite.setEntreprise(entreprise);
        visite.setTuteurEnseignant(tuteur);

        visiteService.ajouterVisite(visite);
        return ResponseEntity.status(HttpStatus.CREATED).body(new VisiteDTO(visite));
    }

    // 🔹 PUT — Modifier une visite
    @PutMapping("/{id}")
    public ResponseEntity<VisiteDTO> updateVisite(@PathVariable Integer id, @RequestBody VisiteRequestDTO dto) {
        return visiteService.getUneVisite(id)
                .map(existing -> {
                    Visite updated = dto.toEntity();
                    updated.setId(id);
                    updated.setApprenti(existing.getApprenti());
                    updated.setEntreprise(existing.getEntreprise());
                    updated.setTuteurEnseignant(existing.getTuteurEnseignant());
                    visiteService.modifierVisite(id, updated);
                    return ResponseEntity.ok(new VisiteDTO(updated));
                })
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    // 🔹 DELETE — Supprimer
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