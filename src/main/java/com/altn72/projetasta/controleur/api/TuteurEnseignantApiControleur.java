package com.altn72.projetasta.controleur.api;

import com.altn72.projetasta.modele.TuteurEnseignant;
import com.altn72.projetasta.controleur.service.TuteurEnseignantService;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/tuteurs")
public class TuteurEnseignantApiControleur {

    private final TuteurEnseignantService tuteurEnseignantService;

    public TuteurEnseignantApiControleur(TuteurEnseignantService tuteurEnseignantService) {
        this.tuteurEnseignantService = tuteurEnseignantService;
    }

    @GetMapping
    public List<TuteurEnseignant> getTousLesTuteurs() {
        return tuteurEnseignantService.getTousLesTuteurs();
    }

    @GetMapping("/{id}")
    public Optional<TuteurEnseignant> getUnTuteur(@PathVariable Integer id) {
        return tuteurEnseignantService.getUnTuteur(id);
    }

    @PostMapping
    public TuteurEnseignant ajouterUnTuteur(@RequestBody TuteurEnseignant tuteur) {
        return tuteurEnseignantService.ajouterUnTuteur(tuteur);
    }

    @PutMapping("/{id}")
    public void modifierUnTuteur(@PathVariable Integer id, @RequestBody TuteurEnseignant tuteur) {
        tuteurEnseignantService.modifierUnTuteur(id, tuteur);
    }

    @DeleteMapping("/{id}")
    public Optional<TuteurEnseignant> supprimerUnTuteur(@PathVariable Integer id) {
        return tuteurEnseignantService.supprimerUnTuteur(id);
    }
}