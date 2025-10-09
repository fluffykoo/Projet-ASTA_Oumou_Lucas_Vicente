package com.altn72.tpfilrouge.controleur;

import com.altn72.tpfilrouge.modele.Programmeur;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

//@RestController
@Controller
@RequestMapping("/tpfilrouge")
public class ProgrammeurControleur {
    @Autowired
    private final ProgrammeurService programmeurService;

    public ProgrammeurControleur(ProgrammeurService programmeurService) {
        this.programmeurService = programmeurService;
    }

    @GetMapping("/programmeurs")
    public String afficherInfosProgrammers(Model model) {
        List<Programmeur> listeProgrammeurs = programmeurService.afficherInfosProgrammeurs();
        model.addAttribute("lesProgrammeurs", listeProgrammeurs);
        model.addAttribute("programmeur", new Programmeur());
        return "listeProgrammeurs";
    }
    @GetMapping("/programmeur/{idProgrammeur}")
    public Optional<Programmeur> afficherUnProgrammeur(@PathVariable Integer idProgrammeur) {

        return programmeurService.getUnProgrammeur(idProgrammeur);
    }


    @DeleteMapping("/programmeur/supprimer/{idProgrammeur}")
    public String deleteUnProgrammeur(@PathVariable Integer idProgrammeur) {
        programmeurService.deleteUnProgrammeur(idProgrammeur);
        return "redirect:/tpfilrouge/programmeurs";
    }

    @PutMapping("/programmeur/modifier/{idProgrammeur}")
    public ResponseEntity<Void> modifierProgrammeur(@PathVariable Integer idProgrammeur, @RequestBody Programmeur programmeurModifie) {

        programmeurService.modifierProgrammeur(idProgrammeur, programmeurModifie);
        return ResponseEntity.noContent().build(); // 204 No Content : ressource modifiée avec succès
    }

    @PatchMapping("/programmeur/modifierPatch/{idProgrammeur}")
    public Optional<Programmeur> patchUnProgrammeur(@PathVariable Integer idProgrammeur, @RequestBody Programmeur programmeurModifie) {
        return programmeurService.patchUnProgrammeur(idProgrammeur, programmeurModifie);
    }


@PostMapping("/programmeurs")
public String postUnProgrammeur(@ModelAttribute Programmeur nouveauProgrammeur) {
    programmeurService.postUnProgrammeur(nouveauProgrammeur);
    return "redirect:/tpfilrouge/programmeurs";
}
}

//version tp partie 1-2
 /*
@GetMapping("/programmeurs")
public List<Programmeur> afficherInfosProgrammeurs() {
    List<Programmeur> programmeurs = new ArrayList<>();

    //objet programmeur
    Programmeur programmeur1 = new Programmeur(20221426, "Camara", "Oumou","Paris","Java","The Princess Diaries",4307.34);
    Programmeur programmeur2 = new Programmeur(1, "Doe", "John", "123 Main St", "Java", "Clean Code", 60000.0);
    programmeurs.add(programmeur1);
    programmeurs.add(programmeur2);
    System.out.println(programmeurs); // affichage dans la console
        return programmeurs;// affichage sur la page web
   }
}
*/