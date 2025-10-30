package com.altn72.projetasta.service;

import com.altn72.projetasta.modele.AnneeAcademique;
import com.altn72.projetasta.modele.Apprenti;
import com.altn72.projetasta.modele.Personne;
import com.altn72.projetasta.repository.ApprentiRepository;
import com.altn72.projetasta.repository.PersonneRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ApprentiService {

    private final ApprentiRepository apprentiRepository;
    private final PersonneRepository personneRepository;

    public ApprentiService(ApprentiRepository apprentiRepository,
                           PersonneRepository personneRepository) {
        this.apprentiRepository = apprentiRepository;
        this.personneRepository = personneRepository;
    }
    //Récupérer tous les apprentis
    public List<Apprenti> getTousLesApprentis() {
        return apprentiRepository.findAll();
    }
    public List<Apprenti> getApprentisParAnnee(AnneeAcademique annee) {
        return apprentiRepository.findByAnneeAcademiqueAndArchiveFalse(annee);
    }

    // Récupérer un apprenti par son ID
    public Optional<Apprenti> getUnApprenti(Integer idApprenti) {
        Optional<Apprenti> apprenti = apprentiRepository.findById(idApprenti);
        return Optional.ofNullable(
                apprenti.orElseThrow(() ->
                        new IllegalStateException("L'apprenti dont l'id est " + idApprenti + " n'existe pas"))
        );
    }

    // Supprimer un apprenti
    @Transactional
    public void supprimerApprenti(Integer idApprenti) {
        Optional<Apprenti> apprenti = apprentiRepository.findById(idApprenti);

        if (apprenti.isPresent()) {
            apprentiRepository.deleteById(idApprenti);
        } else {
            throw new IllegalStateException("L'apprenti dont l'id est " + idApprenti + " n'existe pas");
        }
    }

    // Ajouter un apprenti
    @Transactional
    public void ajouterApprenti(Apprenti apprenti) {
        apprentiRepository.save(apprenti);
    }

    // Modifier un apprenti
    @Transactional
    public void modifierApprenti(Integer idApprenti, Apprenti apprentiModifie) {
        Apprenti apprentiExistant = apprentiRepository.findById(idApprenti)
                .orElseThrow(() -> new IllegalStateException("L'apprenti dont l'id est " + idApprenti + " n'existe pas"));
        //  Mise à jour des champs de Apprenti
        BeanUtils.copyProperties(apprentiModifie, apprentiExistant, "id", "personne");

        //  Mise à jour des champs Personne liés
        if (apprentiExistant.getPersonne() != null && apprentiModifie.getPersonne() != null) {
            Personne personneExistante = apprentiExistant.getPersonne();
            Personne personneModifiee = apprentiModifie.getPersonne();

            personneExistante.setNom(personneModifiee.getNom());
            personneExistante.setPrenom(personneModifiee.getPrenom());
            personneExistante.setAdresseElectronique(personneModifiee.getAdresseElectronique());
            personneExistante.setTelephone(personneModifiee.getTelephone());

            personneRepository.save(personneExistante);
        }

        apprentiRepository.save(apprentiExistant);
    }

    public List<Apprenti> getApprentisParTuteur(String identifiant) {
        return apprentiRepository.findByTuteurEnseignant_Identifiant(identifiant);
    }
    @Transactional
    public void basculerVersNouvelleAnnee(AnneeAcademique anneeActuelle, AnneeAcademique nouvelleAnnee) {
        List<Apprenti> apprentis = apprentiRepository.findByAnneeAcademiqueAndArchiveFalse(anneeActuelle);

        for (Apprenti apprenti : apprentis) {
            String programme = apprenti.getProgramme();

            switch (programme) {
                case "L1" -> apprenti.setProgramme("L2");
                case "L2" -> apprenti.setProgramme("L3");
                case "L3" -> apprenti.setProgramme("M1");
                case "M1" -> apprenti.setProgramme("M2");
                case "M2" -> apprenti.setArchive(true); // diplômé
            }

            // Si l'apprenti n'est pas diplômé, on le rattache à la nouvelle année
            if (!apprenti.isArchive()) {
                apprenti.setAnneeAcademique(nouvelleAnnee);
            }
        }

        apprentiRepository.saveAll(apprentis);
    }
    public long countApprentisActifsPourAnnee(AnneeAcademique annee) {
        return apprentiRepository.findByAnneeAcademiqueAndArchiveFalse(annee).size();
    }
}