/*
 * Via cette interface nous pouvons requêter la BDD
 * en profitant des requêtes prédéfinies utilisables directement
 *
 */

package com.altn72.projetasta.repository;
import com.altn72.projetasta.modele.Rapport;
import com.altn72.projetasta.modele.tmp.RapportId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface RapportRepository extends JpaRepository<Rapport,Integer> {
    @Query("SELECT r FROM Rapport r WHERE r.apprenti.id = :idApprenti AND r.id NOT IN (SELECT e.rapport.id FROM EvaluationRapport e)")
    List<Rapport> findRapportsNonEvalues(@Param("idApprenti") Integer idApprenti);
}