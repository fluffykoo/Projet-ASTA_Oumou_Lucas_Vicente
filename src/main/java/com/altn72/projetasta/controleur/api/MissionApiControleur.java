package com.altn72.projetasta.controleur.api;

import com.altn72.projetasta.controleur.service.MissionService;
import com.altn72.projetasta.modele.Mission;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/missions")
public class MissionApiControleur {

    private final MissionService missionService;

    public MissionApiControleur(MissionService missionService) {
        this.missionService = missionService;
    }

    @GetMapping
    public List<Mission> getToutesLesMissions() {
        return missionService.getToutesLesMissions();
    }

    @GetMapping("/{id}")
    public Optional<Mission> getMissionParId(@PathVariable Integer id) {
        return missionService.getUneMission(id);
    }

    @PostMapping
    public Mission ajouterMission(@RequestBody Mission mission) {
        return missionService.ajouterUneMission(mission);
    }

    @PutMapping("/{id}")
    public void modifierMission(@PathVariable Integer id, @RequestBody Mission mission) {
        missionService.modifierUneMission(id, mission);
    }

    @DeleteMapping("/{id}")
    public Optional<Mission> supprimerMission(@PathVariable Integer id) {
        return missionService.supprimerUneMission(id);
    }
}