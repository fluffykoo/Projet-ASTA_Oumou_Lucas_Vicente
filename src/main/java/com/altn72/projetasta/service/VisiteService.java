package com.altn72.projetasta.service;

import com.altn72.projetasta.modele.Apprenti;
import com.altn72.projetasta.modele.TuteurEnseignant;
import com.altn72.projetasta.modele.Visite;
import com.altn72.projetasta.repository.ApprentiRepository;
import com.altn72.projetasta.repository.TuteurEnseignantRepository;
import com.altn72.projetasta.repository.VisiteRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.BeanUtils;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class VisiteService {

    private final VisiteRepository visiteRepository;
    private final ApprentiRepository apprentiRepository;
    private final TuteurEnseignantRepository tuteurEnseignantRepository;

    public VisiteService(VisiteRepository visiteRepository,
                         ApprentiRepository apprentiRepository,
                         TuteurEnseignantRepository tuteurEnseignantRepository) {
        this.visiteRepository = visiteRepository;
        this.apprentiRepository = apprentiRepository;
        this.tuteurEnseignantRepository = tuteurEnseignantRepository;
    }

    // Récupérer toutes les visites
    public List<Visite> getVisites() {
        return visiteRepository.findAll();
    }

    // Récupérer une visite par ID
    public Optional<Visite> getUneVisite(Integer idVisite) {
        return visiteRepository.findById(idVisite);
    }

    // Supprimer une visite
    @Transactional
    public void supprimerVisite(Integer idVisite) {
        if (!visiteRepository.existsById(idVisite)) {
            throw new IllegalStateException("La visite dont l'id est " + idVisite + " n'existe pas");
        }
        visiteRepository.deleteById(idVisite);
    }

    // Ajouter une visite (classique)
    @Transactional
    public void ajouterVisite(Visite visite) {
        visite.setId(null);
        visiteRepository.save(visite);
    }

    // Modifier une visite existante
    @Transactional
    public void modifierVisite(Integer idVisite, Visite visiteModifiee) {
        Visite visiteToModify = visiteRepository.findById(idVisite)
                .orElseThrow(() -> new IllegalStateException("La visite dont l'id est " + idVisite + " n'existe pas"));

        BeanUtils.copyProperties(visiteModifiee, visiteToModify, "id");
        visiteRepository.save(visiteToModify);
    }

    // Ajouter une visite liée à un apprenti spécifique et au tuteur connecté
    @Transactional
    public void ajouterVisitePourApprenti(Integer idApprenti, Visite visite) {
        Apprenti apprenti = apprentiRepository.findById(idApprenti)
                .orElseThrow(() -> new IllegalStateException("Apprenti introuvable"));

        // Associer l'apprenti et son entreprise
        visite.setApprenti(apprenti);
        visite.setEntreprise(apprenti.getEntreprise());

        // Récupère l'identifiant du compte connecté
        String identifiantConnecte = SecurityContextHolder.getContext().getAuthentication().getName();

        // Recherche du tuteur via l'identifiant
        TuteurEnseignant tuteur = tuteurEnseignantRepository.findByIdentifiant(identifiantConnecte)
                .orElseThrow(() -> new IllegalStateException("Tuteur enseignant non trouvé pour l'identifiant : " + identifiantConnecte));

        visite.setTuteurEnseignant(tuteur);

        // Forcer Hibernate à créer une nouvelle ligne
        visite.setId(null);

        //  Sauvegarde
        visiteRepository.save(visite);
    }
}