//package com.altn72.projetasta.service;
//
//import com.altn72.projetasta.modele.Mission;
//import com.altn72.projetasta.modele.repository.MissionRepository;
//import org.springframework.beans.BeanUtils;
//import org.springframework.stereotype.Service;
//import org.springframework.transaction.annotation.Transactional;
//
//import java.util.List;
//import java.util.Optional;
//
//@Service
//public class MissionService {
//
//    private final MissionRepository missionRepository;
//
//    public MissionService(MissionRepository missionRepository) {
//        this.missionRepository = missionRepository;
//    }
//
//    public List<Mission> getToutesLesMissions() {
//        return missionRepository.findAll();
//    }
//
//    public Optional<Mission> getUneMission(Integer id) {
//        return missionRepository.findById(id);
//    }
//
//    public Mission ajouterUneMission(Mission mission) {
//        return missionRepository.save(mission);
//    }
//
//    public Optional<Mission> supprimerUneMission(Integer id) {
//        Optional<Mission> mission = missionRepository.findById(id);
//        if (mission.isPresent()) {
//            missionRepository.deleteById(id);
//            return mission;
//        }
//        throw new IllegalStateException("Cette mission n'existe pas");
//    }
//
//    @Transactional
//    public void modifierUneMission(Integer id, Mission missionModifiee) {
//        Mission missionToModify = missionRepository.findById(id)
//                .orElseThrow(() -> new IllegalStateException("Cette mission n'existe pas"));
//        BeanUtils.copyProperties(missionModifiee, missionToModify, "id");
//        missionRepository.save(missionToModify);
//    }
//}