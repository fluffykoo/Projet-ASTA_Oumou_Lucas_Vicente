package com.altn72.projetasta.controleur.service;

import com.altn72.projetasta.modele.Apprenti;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class ApprentiService {

    private final ApprentiRepository apprentiRepository;

    public ApprentiService(ApprentiRepository apprentiRepository) {
        this.apprentiRepository = apprentiRepository;
    }

    public List<Apprenti> getTousLesApprentis() {
        return apprentiRepository.findAll();
    }

    public Optional<Apprenti> getUnApprenti(Integer id) {
        return apprentiRepository.findById(id);
    }

    public Apprenti ajouterUnApprenti(Apprenti apprenti) {
        return apprentiRepository.save(apprenti);
    }

    public Optional<Apprenti> supprimerUnApprenti(Integer id) {
        Optional<Apprenti> apprenti = apprentiRepository.findById(id);
        if (apprenti.isPresent()) {
            apprentiRepository.deleteById(id);
            return apprenti;
        }
        throw new IllegalStateException("Cet apprenti n'existe pas");
    }

    @Transactional
    public void modifierUnApprenti(Integer id, Apprenti apprentiModifie) {
        Apprenti apprentiToModify = apprentiRepository.findById(id)
                .orElseThrow(() -> new IllegalStateException("Cet apprenti n'existe pas"));

        BeanUtils.copyProperties(apprentiModifie, apprentiToModify, "id");
        apprentiRepository.save(apprentiToModify);
    }

    public Optional<Apprenti> patchUnApprenti(Integer id, Apprenti apprentiModifie) {
        Optional<Apprenti> apprenti = apprentiRepository.findById(id);

        if (apprenti.isPresent()) {
            Apprenti a = apprenti.get();

            if (apprentiModifie.getNom() != null) a.setNom(apprentiModifie.getNom());
            if (apprentiModifie.getPrenom() != null) a.setPrenom(apprentiModifie.getPrenom());
            if (apprentiModifie.getEmail() != null) a.setEmail(apprentiModifie.getEmail());
            if (apprentiModifie.getTelephone() != null)a.setTelephone(apprentiModifie.getTelephone());

            apprentiRepository.save(a);
            return Optional.of(a);
        } else {
            throw new IllegalStateException("Cet apprenti n'existe pas");
        }
    }
}