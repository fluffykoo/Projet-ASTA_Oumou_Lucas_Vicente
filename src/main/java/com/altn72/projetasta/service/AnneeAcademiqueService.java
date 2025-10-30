package com.altn72.projetasta.service;

import com.altn72.projetasta.modele.AnneeAcademique;
import com.altn72.projetasta.repository.AnneeAcademiqueRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class AnneeAcademiqueService {

    private final AnneeAcademiqueRepository repo;

    public AnneeAcademiqueService(AnneeAcademiqueRepository repo) {
        this.repo = repo;
    }

    // Récupère l’année active
    public AnneeAcademique getAnneeEnCours() {
        return repo.findByEnCoursTrue()
                .orElseThrow(() -> new IllegalStateException("Aucune année académique active trouvée."));
    }

    // Crée automatiquement la nouvelle année et archive l’ancienne
    @Transactional
    public AnneeAcademique creerNouvelleAnnee() {
        AnneeAcademique ancienne = getAnneeEnCours();
        ancienne.setEnCours(false);
        repo.save(ancienne);

        String[] parts = ancienne.getCode().split("-");
        int start = Integer.parseInt(parts[0]) + 1;
        int end = Integer.parseInt(parts[1]) + 1;

        AnneeAcademique nouvelle = new AnneeAcademique();
        nouvelle.setCode(start + "-" + end);
        nouvelle.setLibelle("Année universitaire " + nouvelle.getCode());
        nouvelle.setEnCours(true);

        return repo.save(nouvelle);
    }
}