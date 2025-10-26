//package com.altn72.projetasta.controleur;
//
//import com.altn72.projetasta.service.MissionService;
//import com.altn72.projetasta.modele.Mission;
//import org.springframework.stereotype.Controller;
//import org.springframework.ui.Model;
//import org.springframework.web.bind.annotation.*;
//
//import java.util.List;
//import java.util.Optional;
//
//@Controller
//@RequestMapping("/missions")
//public class MissionControleur {
//
//    private final MissionService missionService;
//
//    public MissionControleur(MissionService missionService) {
//        this.missionService = missionService;
//    }
//
//    @GetMapping
//    public String afficherToutesLesMissions(Model model) {
//        List<Mission> missions = missionService.getToutesLesMissions();
//        model.addAttribute("lesMissions", missions);
//        return "listeMissions";
//    }
//
//    @GetMapping("/preparerAjout")
//    public String preparerAjoutMission(Model model) {
//        model.addAttribute("nouvelleMission", new Mission());
//        return "nouvelleMission";
//    }
//
//    @GetMapping("/preparerModif/{id}")
//    public String preparerModifMission(@PathVariable Integer id, Model model) {
//        Optional<Mission> missionToUpdate = missionService.getUneMission(id);
//        model.addAttribute("missionToUpdate", missionToUpdate.orElseThrow());
//        return "detailsMission";
//    }
//
//    @PostMapping
//    public String ajouterMission(@ModelAttribute Mission mission) {
//        missionService.ajouterUneMission(mission);
//        return "redirect:/missions";
//    }
//
//    @PutMapping("/modifier/{id}")
//    public String modifierMission(@PathVariable Integer id, @ModelAttribute Mission mission) {
//        missionService.modifierUneMission(id, mission);
//        return "redirect:/missions";
//    }
//
//    @DeleteMapping("/supprimer/{id}")
//    public String supprimerMission(@PathVariable Integer id) {
//        missionService.supprimerUneMission(id);
//        return "redirect:/missions";
//    }
//}