package com.altn72.projetasta.repository;

import com.altn72.projetasta.modele.Rapport;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import java.util.List;

public interface RapportRepository extends JpaRepository<Rapport, Integer> {

    @Query("SELECT r FROM Rapport r WHERE r.apprenti.id = :idApprenti AND r.id NOT IN (SELECT e.rapport.id FROM EvaluationRapport e)")
    List<Rapport> findRapportsNonEvalues(@Param("idApprenti") Integer idApprenti);

    // Compte les rapports sans évaluation
    @Query("SELECT COUNT(r) FROM Rapport r WHERE r.evaluationsRapport IS EMPTY")
    long countRapportsNonEvalues();
}