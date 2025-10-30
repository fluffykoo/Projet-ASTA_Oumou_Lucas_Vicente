/*
 * Via cette interface nous pouvons requêter la BDD
 * en profitant des requêtes prédéfinies utilisables directement
 *
 */

package com.altn72.projetasta.repository;
import com.altn72.projetasta.modele.TuteurEnseignant;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface TuteurEnseignantRepository extends JpaRepository<TuteurEnseignant,Integer> {
    Optional<TuteurEnseignant> findByIdentifiant(String identifiant);//pour spring security
    @Query("SELECT t FROM TuteurEnseignant t JOIN FETCH t.personne WHERE t.identifiant = :identifiant")
    Optional<TuteurEnseignant> findByIdentifiantWithPersonne(@Param("identifiant") String identifiant);

}