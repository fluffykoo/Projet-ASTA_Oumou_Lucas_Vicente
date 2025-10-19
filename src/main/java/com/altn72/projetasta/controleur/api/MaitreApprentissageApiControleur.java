package com.altn72.projetasta.controleur.api;

import com.altn72.projetasta.modele.MaitreApprentissage;
import com.altn72.projetasta.controleur.service.MaitreApprentissageService;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/maitres")
public class MaitreApprentissageApiControleur {

    private final MaitreApprentissageService maitreApprentissageService;

    public MaitreApprentissageApiControleur(MaitreApprentissageService maitreApprentissageService) {
        this.maitreApprentissageService = maitreApprentissageService;
    }

    @GetMapping
    public List<MaitreApprentissage> getTousLesMaitres() {
        return maitreApprentissageService.getTousLesMaitres();
    }

    @GetMapping("/{id}")
    public Optional<MaitreApprentissage> getUnMaitre(@PathVariable Integer id) {
        return maitreApprentissageService.getUnMaitre(id);
    }

    @PostMapping
    public MaitreApprentissage ajouterUnMaitre(@RequestBody MaitreApprentissage maitre) {
        return maitreApprentissageService.ajouterUnMaitre(maitre);
    }

    @PutMapping("/{id}")
    public void modifierUnMaitre(@PathVariable Integer id, @RequestBody MaitreApprentissage maitreModifie) {
        maitreApprentissageService.modifierUnMaitre(id, maitreModifie);
    }

    @DeleteMapping("/{id}")
    public Optional<MaitreApprentissage> supprimerUnMaitre(@PathVariable Integer id) {
        return maitreApprentissageService.supprimerUnMaitre(id);
    }
}