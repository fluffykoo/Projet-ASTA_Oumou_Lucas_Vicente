package com.altn72.projetasta.controleur.api;

import com.altn72.projetasta.modele.Apprenti;
import com.altn72.projetasta.controleur.service.ApprentiService;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/apprentis")
public class ApprentiApiControleur {

    private final ApprentiService apprentiService;

    public ApprentiApiControleur(ApprentiService apprentiService) {
        this.apprentiService = apprentiService;
    }

    @GetMapping
    public List<Apprenti> getTousLesApprentis() {
        return apprentiService.getTousLesApprentis();
    }

    @GetMapping("/{id}")
    public Optional<Apprenti> getUnApprenti(@PathVariable Integer id) {
        return apprentiService.getUnApprenti(id);
    }

    @PostMapping
    public Apprenti ajouterUnApprenti(@RequestBody Apprenti apprenti) {
        return apprentiService.ajouterUnApprenti(apprenti);
    }

    @DeleteMapping("/{id}")
    public Optional<Apprenti> supprimerUnApprenti(@PathVariable Integer id) {
        return apprentiService.supprimerUnApprenti(id);
    }

    @PutMapping("/{id}")
    public void modifierUnApprenti(@PathVariable Integer id, @RequestBody Apprenti apprenti) {
        apprentiService.modifierUnApprenti(id, apprenti);
    }

    @PatchMapping("/{id}")
    public Optional<Apprenti> patchUnApprenti(@PathVariable Integer id, @RequestBody Apprenti apprenti) {
        return apprentiService.patchUnApprenti(id, apprenti);
    }
}