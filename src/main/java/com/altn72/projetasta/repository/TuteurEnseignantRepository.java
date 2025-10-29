/*
 * Via cette interface nous pouvons requêter la BDD
 * en profitant des requêtes prédéfinies utilisables directement
 *
 */

package com.altn72.projetasta.repository;
import com.altn72.projetasta.modele.TuteurEnseignant;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface TuteurEnseignantRepository extends JpaRepository<TuteurEnseignant,Integer> {
    Optional<TuteurEnseignant> findByIdentifiant(String identifiant);//pour spring security

}