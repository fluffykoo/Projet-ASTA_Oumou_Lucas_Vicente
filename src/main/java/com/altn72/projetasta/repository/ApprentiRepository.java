/*
 * Via cette interface nous pouvons requêter la BDD
 * en profitant des requêtes prédéfinies utilisables directement
 *
 */

package com.altn72.projetasta.repository;

import com.altn72.projetasta.modele.AnneeAcademique;
import com.altn72.projetasta.modele.Apprenti;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ApprentiRepository extends JpaRepository<Apprenti, Integer> {
    List<Apprenti> findByTuteurEnseignant_Identifiant(String identifiant);
    List<Apprenti> findByAnneeAcademiqueAndArchiveFalse(AnneeAcademique annee);
}