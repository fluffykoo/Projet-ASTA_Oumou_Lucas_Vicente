package com.altn72.projetasta.service;

import com.altn72.projetasta.modele.AnneeAcademique;
import com.altn72.projetasta.modele.Apprenti;
import com.altn72.projetasta.modele.Entreprise;
import com.altn72.projetasta.modele.Personne;
import com.altn72.projetasta.repository.ApprentiRepository;
import com.altn72.projetasta.repository.EntrepriseRepository;
import com.altn72.projetasta.repository.PersonneRepository;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ApprentiService {

    private final ApprentiRepository apprentiRepository;
    private final PersonneRepository personneRepository;
    private final EntrepriseRepository entrepriseRepository;

    public ApprentiService(ApprentiRepository apprentiRepository,
                           PersonneRepository personneRepository, EntrepriseRepository entrepriseRepository)
    {
        this.apprentiRepository = apprentiRepository;
        this.personneRepository = personneRepository;
        this.entrepriseRepository = entrepriseRepository;
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

        // Copier les propriétés sauf les collections gérées par Hibernate
        BeanUtils.copyProperties(apprentiModifie, apprentiExistant,
                "id", "personne", "motsClefs", "soutenances", "rapports", "entreprise", "visites");

        // ✅ On sauvegarde l'entité existante, PAS la modifiée
        apprentiRepository.save(apprentiExistant);
    }

    @PersistenceContext
    private EntityManager entityManager;
    public List<Apprenti> searchApprentis(String nom, String entreprise, String motCle, String anneeAcademique) {
        if(nom == null || nom == ""){
            nom="%";
        }
        if(motCle == null ||  motCle == ""){
            motCle="%";
        }

        if(anneeAcademique == null || anneeAcademique == ""){
            anneeAcademique="%";
        }

        String sql = """
            SELECT a.*
            FROM personne p
            LEFT JOIN posseder_mots_clefs pmc ON p.id = pmc.id_personne
            LEFT JOIN apprenti a ON p.id = a.id_personne
            LEFT JOIN entreprise e ON a.id_entreprise = e.id
            WHERE (?1 IS NULL OR p.nom LIKE ?1)
              AND (?2 IS NULL OR e.raison_sociale LIKE ?2)
              AND (?3 IS NULL OR pmc.nom_mot_clef LIKE ?3)
              AND (?4 IS NULL OR a.annee_academique LIKE ?4)
        """;

        var query = entityManager.createNativeQuery(sql, Apprenti.class);

        query.setParameter(1, nom != null && !nom.isEmpty() ? "%" + nom + "%" : null);
        query.setParameter(2, entreprise != null && !entreprise.isEmpty() ? "%" + entreprise + "%" : null);
        query.setParameter(3, motCle != null && !motCle.isEmpty() ? "%" + motCle + "%" : null);
        query.setParameter(4, anneeAcademique != null && !anneeAcademique.isEmpty() ? "%" + anneeAcademique + "%" : null);
        return query.getResultList();
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